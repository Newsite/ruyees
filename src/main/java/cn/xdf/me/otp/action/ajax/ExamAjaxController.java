package cn.xdf.me.otp.action.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xdf.me.otp.action.view.model.VExam;
import cn.xdf.me.otp.model.flow.BamExamCategory;
import cn.xdf.me.otp.service.flow.BamExamService;

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
