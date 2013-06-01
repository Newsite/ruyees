package ruyees.otp.action.ajax;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ruyees.otp.action.view.model.VPagination;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.service.CommentService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;

/**
 * 
 * @author Zaric
 * @date  2013-6-1 上午12:31:51
 */
@Controller
@RequestMapping(value = "/ajax/comment")
@Scope("request")
public class CommentAjaxController {

	@RequestMapping(value = "findCommentByCwId")
	@ResponseBody
	public VPagination getCommentByCwId(String cwId, String page) {
		int pageNo = NumberUtils.toInt(page, 0);
		Pagination pagination = commentService.getList(cwId, pageNo);
		VPagination vp = new VPagination(pagination);
		return vp;
	}

	@RequestMapping(value = "pushComment")
	@ResponseBody
	public String pushComment(String cwId, String fdConent) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		commentService.saveComment(user, cwId, fdConent);
		return "true";
	}

	@RequestMapping(value = "deleteCommentByCwId")
	@ResponseBody
	public String deleteCommentByCwId(String fdid) {
		commentService.delete(fdid);
		return "true";
	}

	@Autowired
	private CommentService commentService;

}
