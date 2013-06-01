package ruyees.otp.action.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ruyees.otp.action.view.model.VExam;
import ruyees.otp.model.flow.BamExamCategory;
import ruyees.otp.service.flow.BamExamService;

/**
 * 新教师备课 考试模块
 * @author Zaric
 * @date  2013-6-1 上午12:32:19
 */
@Controller
@RequestMapping(value = "/ajax/exam")
@Scope("request")
public class ExamAjaxController {

	@Autowired
	private BamExamService bamExamService;

	@RequestMapping(value = "pushExam",method=RequestMethod.POST)
	@ResponseBody
	public VExam pushExam(BamExamCategory bamExamCategory) {
		return bamExamService.addExam(bamExamCategory);
	}

}
