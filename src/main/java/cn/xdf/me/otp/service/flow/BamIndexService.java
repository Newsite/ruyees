package cn.xdf.me.otp.service.flow;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.xdf.me.otp.action.view.model.VOperation;
import cn.xdf.me.otp.common.hibernate4.Updater;
import cn.xdf.me.otp.common.hibernate4.Updater.UpdateMode;
import cn.xdf.me.otp.common.upload.FileModel;
import cn.xdf.me.otp.common.upload.FileRepository;
import cn.xdf.me.otp.common.utils.ComUtils;
import cn.xdf.me.otp.model.base.AttMain;
import cn.xdf.me.otp.model.flow.BamIndex;
import cn.xdf.me.otp.model.flow.BamOperation;
import cn.xdf.me.otp.model.flow.BamPackage;
import cn.xdf.me.otp.model.flow.BamStage;

/**
 * 
 * 作业包 指标业务数据
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:29:51
 */
@Service
@Transactional(readOnly = true)
public class BamIndexService extends BamBaseService {

	@Autowired
	private BamOperationService bamOperationService;

	@Autowired
	private BamPackageService bamPackageService;

	/**
	 * 上传作业包指标对应的作业
	 * 
	 * @param key
	 * @param fdFileName
	 * @param fdFullName
	 * @param file
	 * @param request
	 * @param status
	 * @return
	 */
	@Transactional(readOnly = false)
	public VOperation uploadOperation(String key, String fdFileName,
			String fdFullName, MultipartFile file, HttpServletRequest request,
			int status) {
		VOperation vOperation = new VOperation();
		// 文件存储类型
		String folder = request.getParameter("folder");
		// 文件存储
		FileModel fileModel = fileRepository.storeByExt(file, folder);

		if (StringUtils.isBlank(fdFullName)) {
			fdFullName = fileModel.getFilePath();
		}

		vOperation.setFileName(fileModel.getFileName());
		vOperation.setFdPath(fileModel.getFilePath());

		Boolean isExists = false;
		if (StringUtils.isBlank(fdFileName)) {
			fdFileName = fileModel.getFileName();
		}

		// 存储到指标里
		BamIndex bamIndex = load(key);

		bamIndex.setFdFileName(fdFileName);
		bamIndex.setUploadTime(ComUtils.now());
		bamIndex.setStatus(status); // 2：已上传

		if (bamIndex.getBamAttMain() == null) {
			AttMain bamAttMain = new AttMain();
			bamAttMain.setFileName(fdFileName);
			bamAttMain.setFilePath(fdFullName);
			bamAttMain.setStoreType(fileModel.getStoreType().getValue());
			bamAttMain.setFileType(1); // 文件类型 1：文档
			bamIndex.setBamAttMain(bamAttMain);
		} else {
			bamIndex.getBamAttMain().setFileName(fdFileName);
			bamIndex.getBamAttMain().setFilePath(fdFullName);
			bamIndex.getBamAttMain().setStoreType(
					fileModel.getStoreType().getValue());
			bamIndex.getBamAttMain().setFileType(1);
		}
		BamIndex index = save(bamIndex);
		isExists = index != null;

		vOperation.setAttId(index.getBamAttMain().getFdId());
		vOperation.setIsCuss(isExists);
		return vOperation;
	}

	/**
	 * 提交作业
	 * 
	 * @param key
	 * @param fdFileName
	 * @param fdFullName
	 * @param status
	 * @return -1：提交失败；0：提交成功；1提交成功并且没有未提交的作业
	 */
	@Transactional(readOnly = false)
	public String submitOperation(String key, String fdFileName,
			String fdFullName, int status) {
		// 提交作业
		Boolean isSucc = false;
		BamIndex bamIndex = load(key);

		bamIndex.setFdFileName(fdFileName);
		bamIndex.setUploadTime(ComUtils.now());
		bamIndex.setStatus(status); // 2：已提交
		bamIndex.setRemark(null); // 驳回后提交清空

		bamIndex.getBamAttMain().setFileName(fdFileName);
		bamIndex.getBamAttMain().setFilePath(fdFullName);
		bamIndex.getBamAttMain().setStoreType(2);
		bamIndex.getBamAttMain().setFileType(1);
		BamIndex index = save(bamIndex);
		isSucc = index != null;

		if (!isSucc) {
			return "-1";
		}

		// 判断是否所有的作业都已经提交
		boolean allUploaded = true;
		List<BamOperation> bamOperations = bamIndex.getBamOperation()
				.getBamPackage().getBamOperation();
		if (bamOperations != null && bamOperations.size() > 0) {
			for (BamOperation bo : bamOperations) {
				List<BamIndex> bamIndexs = bo.getBamIndexs();
				if (bamIndexs != null && bamIndexs.size() > 0) {
					for (BamIndex bi : bamIndexs) {
						if (bi.getStatus() == 0 || bi.getStatus() == 1
								|| bi.getStatus() == 3) {// 未上传，未提交,驳回
							allUploaded = false;
							break;
						}
					}
				}
				if (!allUploaded) {
					break;
				}
			}
		}

		BamPackage bamPackage = bamIndex.getBamOperation().getBamPackage();
		BamStage bamStage = bamPackage.getPhase().getStage();

		// 判断是否已经全部提交，并且不需要审核
		if (allUploaded && !bamStage.getAssessment()) {
			autoThrough(bamPackage.getFdId());// 自动通过
		}

		return allUploaded ? "1" : "0";
	}

	/**
	 * 自动审核通过
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void autoThrough(String packageId) {
		BamPackage bp = get(BamPackage.class, packageId);
		BamStage bamStage = bp.getPhase().getStage();
		List<BamOperation> bamOperationList = bp.getBamOperation();

		if (bamOperationList != null && bamOperationList.size() > 0) {
			for (BamOperation bo : bamOperationList) {
				List<BamIndex> bamIndexList = bo.getBamIndexs();
				if (bamIndexList != null && bamIndexList.size() > 0) {
					for (BamIndex bi : bamIndexList) {
						BamIndex bamIndex = new BamIndex();
						bamIndex.setFdId(bi.getFdId());
						bamIndex.setStatus(4);
						bamIndex.setRemark(bamStage.getDefaultComment());
						bamIndex.setFdToValue(bi.getFdValue().floatValue());
						bamIndex.setAppoveTime(new Timestamp(new Date()
								.getTime()));
						saveBamIndex(bamIndex);// 作业通过
					}
				}

				BamOperation bamOperation = new BamOperation();
				bamOperation.setFdId(bo.getFdId());
				bamOperation.setThrough(true);
				bamOperationService.setThrough(bamOperation);// 作业步骤通过
			}
		}

		BamPackage bamPackage = new BamPackage();
		bamPackage.setFdId(bp.getFdId());
		bamPackage.setThrough(true);
		bamPackage.setFdComment(bamStage.getDefaultComment());
		bamPackageService.setThrough(bamPackage);// 作业包通过
	}

	/**
	 * 批阅作业
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveBamIndex(BamIndex bamIndex) {
		// 保存作业
		Updater<BamIndex> updater = new Updater<BamIndex>(bamIndex);
		Float fdToValue = bamIndex.getFdToValue();
		if (bamIndex.getStatus() == 3) {
			fdToValue = null;
			bamIndex.setFdToValue(null);
		}
		if (fdToValue != null) {
			DecimalFormat df = new DecimalFormat("#.#");
			bamIndex.setFdToValue(Float.parseFloat(df.format(fdToValue)));
		}
		bamIndex.setAppoveTime(ComUtils.now());
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("remark");
		updater.include("status");
		updater.include("appoveTime");
		updater.include("fdToValue");
		updateByUpdater(updater);
	}

	private FileRepository fileRepository;

	@Autowired
	public void setFileRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<BamIndex> getEntityClass() {
		return BamIndex.class;
	}

}
