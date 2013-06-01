package ruyees.otp.action.ajax;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ruyees.otp.service.base.ContentMovieService;
import ruyees.otp.service.base.DictionService;
import ruyees.otp.service.base.ExamCategoryService;
import ruyees.otp.service.base.GradeService;
import ruyees.otp.service.base.OperPackageService;
import ruyees.otp.service.template.ProTemplateService;

/**
 * 基础数据校验
 * 
 * 包含: 1:数据字典 2:考试 3:作业包 4:模板 5:按排批课
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:32:45
 */
@Controller
@RequestMapping(value = "/ajax/foundation")
@Scope("request")
public class FoundationAjaxController {

	@Autowired
	private DictionService dictionService;

	@Autowired
	private ExamCategoryService examCategoryService;

	@Autowired
	private OperPackageService operPackageService;

	@Autowired
	private ProTemplateService proTemplateService;

	@Autowired
	private GradeService gradeService;

	@Autowired
	private ContentMovieService contentMovieService;

	/**
	 * 字典数据校验 1:项目2:课程3:分项 4:阶段
	 * 
	 * @param name
	 * @param fdType
	 * @return
	 */
	@RequestMapping(value = "checkDiction", method = RequestMethod.POST)
	@ResponseBody
	public String checkDiction(String fdType, String name) {
		return BooleanUtils.toStringTrueFalse(dictionService
				.hasValueByNameAndType(fdType, name));
	}

	/**
	 * 文档视频增加数据校验
	 * 
	 * @param name
	 * @param fdType
	 * @return
	 */
	@RequestMapping(value = "checkCM", method = RequestMethod.POST)
	@ResponseBody
	public String checkCM(String fileType, String fdName) {
		return BooleanUtils.toStringTrueFalse(contentMovieService
				.hasValueByNameAndType(fileType, fdName));
	}

	/**
	 * 文档视频增加数据校验
	 * 
	 * @param name
	 * @param fdType
	 * @return
	 */
	@RequestMapping(value = "checkEditCM", method = RequestMethod.POST)
	@ResponseBody
	public String checkEditCM(String fdId, String fileType, String fdName) {
		return BooleanUtils.toStringTrueFalse(contentMovieService
				.hasValueByIdAndNameAndType(fdId, fileType, fdName));
	}

	/**
	 * 字典数据编辑校验 1:项目2:课程3:分项 4:阶段
	 * 
	 * @param fdId
	 * @param name
	 * @param fdType
	 * @return
	 */
	@RequestMapping(value = "checkEditDiction", method = RequestMethod.POST)
	@ResponseBody
	public String checkEditDiction(String fdId, String fdType, String name) {
		return BooleanUtils.toStringTrueFalse(dictionService
				.hasValueByIdAndNameAndType(fdId, fdType, name));
	}

	/**
	 * 考试的唯一性校验
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "checkExam", method = RequestMethod.POST)
	@ResponseBody
	public String checkExam(String name) {
		return BooleanUtils.toStringTrueFalse(examCategoryService
				.hasValueByName(name));
	}

	/**
	 * 作业包的唯一性校验
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "checkOperPackage", method = RequestMethod.POST)
	@ResponseBody
	public String checkOperPackage(String name) {
		return BooleanUtils.toStringTrueFalse(operPackageService
				.hasPackageByName(name));
	}

	/**
	 * 备课模板的唯一性校验
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "checkTemplate", method = RequestMethod.POST)
	@ResponseBody
	public String checkTemplate(String name) {
		return BooleanUtils.toStringTrueFalse(proTemplateService
				.hasTemplateByName(name));
	}

	/**
	 * 按排批课的唯一性校验
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "checkGrade", method = RequestMethod.POST)
	@ResponseBody
	public String checkGrade(String name) {
		return BooleanUtils
				.toStringTrueFalse(gradeService.hasGradeByName(name));
	}
}
