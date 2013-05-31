package cn.xdf.me.otp.service.flow;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import cn.xdf.me.otp.model.flow.BamCourseware;
import cn.xdf.me.otp.model.flow.BamCoursewareItem;
import cn.xdf.me.otp.model.flow.BamStage;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;
import cn.xdf.me.otp.service.notify.NotifyService;

/**
 * 
 * 课件分项业务数据
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:29:11
 */
@Service
@Transactional(readOnly = true)
public class BamCoursewareItemService extends BamBaseService {
	@Autowired
	private BamCoursewareService bamCoursewareService;

	@Autowired
	private NotifyService notifyService;

	/**
	 * 上传课件分项对应的课件
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
	public VOperation uploadCourseware(String key, String fdFileName,
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

		vOperation.setFileName(fdFileName);
		vOperation.setFdPath(fileModel.getFilePath());

		Boolean isExists = false;

		// 存储到课件分项里
		BamCoursewareItem bamItem = get(key);
		Updater<BamCoursewareItem> updater = new Updater<BamCoursewareItem>(
				bamItem);
		bamItem.setStatus(status);// 1：已上传
		bamItem.setPath(fileModel.getFilePath());
		bamItem.setTime(ComUtils.now());
		BamCoursewareItem coursewareItem = updateByUpdater(updater);

		isExists = coursewareItem != null;

		vOperation.setAttId(coursewareItem.getFdId());
		vOperation.setIsCuss(isExists);
		return vOperation;
	}

	/**
	 * 提交课件
	 * 
	 * @param key
	 * @param fdFileName
	 * @param fdFullName
	 * @param status
	 * @return -1：提交失败；0：提交成功；1提交成功并且没有未提交的作业
	 */
	@Transactional(readOnly = false)
	public String submitCourseware(String key, String fdFileName,
			String fdFullName, int status) {
		// 提交作业
		Boolean isSucc = false;
		BamCoursewareItem bamItem = get(key);
		Updater<BamCoursewareItem> updater = new Updater<BamCoursewareItem>(
				bamItem);
		bamItem.setStatus(status);// 2：已提交
		bamItem.setPath(fdFullName);
		bamItem.setTime(ComUtils.now());
		bamItem.setRemark(null);
		BamCoursewareItem coursewareItem = updateByUpdater(updater);

		isSucc = coursewareItem != null;

		if (!isSucc) {
			return "-1";
		}

		// 判断是否所有的作业都已经提交
		boolean allUploaded = true;
		List<BamCoursewareItem> bamCoursewareItems = coursewareItem
				.getBamCourseware().getItems();
		if (bamCoursewareItems != null && bamCoursewareItems.size() > 0) {
			for (BamCoursewareItem bi : bamCoursewareItems) {
				if (bi.getStatus() == 0 || bi.getStatus() == 1
						|| bi.getStatus() == 3) {// 未上传，未提交,驳回
					allUploaded = false;
					break;
				}
			}
		}

		BamStage bamStage = coursewareItem.getBamCourseware().getPhase()
				.getStage();
		BamCourseware bamCourseware = coursewareItem.getBamCourseware();
		// 判断是否已经全部提交，并且不需要审核
		if (allUploaded && !bamStage.getAssessment()) {
			autoThrough(bamCourseware.getFdId());// 自动通过
		}

		return allUploaded ? "1" : "0";
	}

	/**
	 * 自动审核通过
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void autoThrough(String bamCoursewareId) {
		BamCourseware bc = get(BamCourseware.class, bamCoursewareId);
		BamStage bamStage = bc.getPhase().getStage();

		List<BamCoursewareItem> bamCoursewareItemList = bc.getItems();
		if (bamCoursewareItemList != null && bamCoursewareItemList.size() > 0) {
			for (BamCoursewareItem bci : bamCoursewareItemList) {
				BamCoursewareItem bamCoursewareItem = new BamCoursewareItem();
				bamCoursewareItem.setFdId(bci.getFdId());
				bamCoursewareItem.setStatus(4);
				bamCoursewareItem.setRemark(bamStage.getDefaultComment());
				bamCoursewareItem.setApproweTime(new Timestamp(new Date()
						.getTime()));
				saveBamCoursewareItem(bamCoursewareItem, "");// 课件分项通过,此处空串无意
			}
		}

		BamCourseware bamCourseware = new BamCourseware();
		bamCourseware.setFdId(bc.getFdId());
		bamCourseware.setThrough(true);
		bamCourseware.setFdComment(bamStage.getDefaultComment());
		bamCoursewareService.setThrough(bamCourseware, bc.getPhase()
				.getNewteachId());// 课件通过
	}

	/**
	 * 批阅课件
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveBamCoursewareItem(BamCoursewareItem bamCoursewareItem,
			String index) {
		// 先批阅某一个分项
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		Integer status = bamCoursewareItem.getStatus();
		Boolean through = status == 3 ? false : true;
		Updater<BamCoursewareItem> updater = new Updater<BamCoursewareItem>(
				bamCoursewareItem);
		bamCoursewareItem.setApproweId(uId);
		bamCoursewareItem.setApproweTime(ComUtils.now());
		bamCoursewareItem.setThrough(through);
		updater.setUpdateMode(UpdateMode.MIN);

		updater.include("remark");
		updater.include("status");
		updater.include("approweTime");
		updater.include("approweId");
		updater.include("through");
		bamCoursewareItem = updateByUpdater(updater);
		// 若驳回则发消息
		if (status == 3) {
			notifyService.guidToDoNewTeach(bamCoursewareItem,
					Integer.parseInt(index));
		}
	}

	private FileRepository fileRepository;

	@Autowired
	public void setFileRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<BamCoursewareItem> getEntityClass() {
		return BamCoursewareItem.class;
	}

}
