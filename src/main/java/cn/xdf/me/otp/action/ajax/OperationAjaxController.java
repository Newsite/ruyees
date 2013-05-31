package cn.xdf.me.otp.action.ajax;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xdf.me.otp.common.hibernate4.Value;
import cn.xdf.me.otp.model.template.TemplateContent;
import cn.xdf.me.otp.service.template.TemplateContentService;

@Controller
@RequestMapping(value = "/ajax/operation")
@Scope("request")
public class OperationAjaxController {

	@Autowired
	private TemplateContentService templateContentService;

	@RequestMapping(value = "checkHasContent", method = RequestMethod.POST)
	@ResponseBody
	public String checkHasContent(HttpServletRequest request) {
		String key = request.getParameter("key");
		String id = request.getParameter("id");
		List<TemplateContent> contents = null;
		if (StringUtils.isNotBlank(id)) {
			contents = templateContentService.findByCriteria(
					TemplateContent.class, Value.eq("operPackage.fdId", key),
					Value.ne("fdId", id));
		} else {
			contents = templateContentService.findByCriteria(
					TemplateContent.class, Value.eq("operPackage.fdId", key));
		}

		if (contents.isEmpty()) {
			return "false";
		}
		return "true";
	}
}
