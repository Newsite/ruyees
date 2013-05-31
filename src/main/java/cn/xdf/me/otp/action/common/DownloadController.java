package cn.xdf.me.otp.action.common;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xdf.me.otp.common.download.DownloadHelper;
import cn.xdf.me.otp.common.utils.ComUtils;
import cn.xdf.me.otp.common.utils.ResourceBundleReader;
import cn.xdf.me.otp.model.base.AttMain;
import cn.xdf.me.otp.model.flow.BamCoursewareItem;
import cn.xdf.me.otp.service.base.AttMainService;
import cn.xdf.me.otp.service.flow.BamCoursewareItemService;
import cn.xdf.me.otp.utils.HtmlToPdf;
import cn.xdf.me.otp.utils.ShiroUtils;

import com.lowagie.text.DocumentException;

/**
 * 文件附件
 * @author Zaric
 * @date  2013-6-1 上午12:37:56
 */
@Controller
public class DownloadController {

	@Autowired
	private AttMainService attMainService;

	@Autowired
	private BamCoursewareItemService bamCoursewareItemService;

	/**
	 * 
	 * @param id
	 *            (对应AttMain的主键)
	 * @return
	 */
	@RequestMapping("/common/download/{id}")
	@ResponseBody
	public DownloadHelper download(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		AttMain attMain = attMainService.get(AttMain.class, id);
		if (attMain != null) {
			ResourceBundle bundle = ResourceBundleReader.getBundle();

			String destPath = bundle.getString("upload_path");

			String osName = System.getProperty("os.name");
			String filePath = attMain.getFilePath();
			if (StringUtils.isBlank(filePath)) {
				return dh;
			}
			if (osName.contains("Windows")) {
				filePath = filePath.replace("/", "\\\\");
			}
			String webPath = filePath;
			if (!filePath.contains(destPath)) {
				webPath = destPath + filePath;
			}

			File file = new File(webPath);
			if (!file.exists()) {
				webPath = request.getSession().getServletContext()
						.getRealPath("/")
						+ System.getProperty("file.separator") + filePath;
			}
			dh.setFile(new File(webPath));
			dh.setFileName(attMain.getFileName());
		}
		return dh;
	}

	/**
	 * 
	 * @param id
	 *            (对应BamCoursewareItem的主键)
	 * @return
	 */
	@RequestMapping("/common/courseware/{id}")
	@ResponseBody
	public DownloadHelper downloadCour(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		BamCoursewareItem bamItem = bamCoursewareItemService.get(
				BamCoursewareItem.class, id);
		if (bamItem != null) {
			String osName = System.getProperty("os.name"); // Windows 7
			String filePath = bamItem.getPath();
			if (StringUtils.isBlank(filePath)) {
				return dh;
			}
			if (osName.contains("Windows")) {
				filePath = filePath.replace("/", "\\\\");
			}

			dh.setFile(new File(filePath));
			int index = filePath.lastIndexOf(".");
			String suffix = filePath.substring(index);
			dh.setFileName(bamItem.getName() + suffix);
		}
		return dh;
	}

	@RequestMapping("/common/download/pdf/{id}")
	@ResponseBody
	public DownloadHelper downloadPdf(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {

		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		ResourceBundle bundle = ResourceBundleReader.getBundle();
		String destPath = bundle.getString("upload_path");
		String fileName = ShiroUtils.getUser().getName() + ".pdf";

		String destFile = destPath + fileName;
		
		/*Url2Pdf.parseURL2PDFElement(destFile, ComUtils.getWebPath(request)
				+ "trainee/success/self/" + id);*/
		HtmlToPdf.saveHtmlToPdf(ComUtils.getWebPath(request)
				+ "trainee/success/self/" + id, destFile);
		
	
		dh.setFile(new File(destFile));
		dh.setFileName(fileName);
		return dh;
	}

}
