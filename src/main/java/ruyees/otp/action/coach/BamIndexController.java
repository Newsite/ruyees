package ruyees.otp.action.coach;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.model.flow.BamIndex;
import ruyees.otp.model.flow.BamOperation;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;
import ruyees.otp.service.flow.BamIndexService;
import ruyees.otp.service.flow.BamOperationService;
import ruyees.otp.service.notify.NotifyService;

/**
 * 指导教师审批指标对应的作业
 * @author Zaric
 * @date  2013-6-1 上午12:37:26
 */
@Controller
@RequestMapping(value = "/coach/index")
@Scope("request")
public class BamIndexController {

	@Autowired
	private BamIndexService bamIndexService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BamOperationService bamOperationService;
	@Autowired
	private NotifyService notifyService;

	@RequestMapping(value = "list")
	public String list(Model model, String pageNo, HttpServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		Finder finder = Finder.create("select b from BamIndex b left join fetch b.bamOperation o left join fetch o.bamPackage pack left join fetch pack.phase phase  where phase.guidId=:guidId and b.status in (:status)");
		
		finder.setParam("guidId", uId);
		finder.setParamList("status", new Integer[]{2,3,4}); // 2:已提交,3:驳回,4:通过
		finder.append("order by b.status,b.fdId");
		Pagination page = bamIndexService.getPage(finder, NumberUtils.createInteger(pageNo));
		
		for(Object obj : page.getList()){
			BamIndex index = (BamIndex)obj;
			SysOrgPerson person = accountService.findById(index.getBamOperation().getBamPackage().getPhase().getNewteachId());
			index.getBamOperation().getBamPackage().getPhase().setNewTeach(person);
		}
		model.addAttribute("page", page);

		return "/base/coach/list";
	}

	@RequestMapping(value = "edit/{id}")
	public String edit(Model model, @PathVariable("id") String indexId) {
		BamIndex bamIndex=bamIndexService.load(indexId);
		SysOrgPerson person = accountService.findById(bamIndex.getBamOperation().getBamPackage().getPhase().getNewteachId());
		model.addAttribute("person", person);
		model.addAttribute("bean", bamIndex);
		return "/base/coach/edit";
	}

	@RequestMapping(value = "view/{id}")
	public String view(Model model, @PathVariable("id") String indexId) {
		model.addAttribute("bean", bamIndexService.load(indexId));
		return "/base/coach/view";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(BamIndex bamIndex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		bamIndexService.saveBamIndex(bamIndex);
		BamIndex bIndex = bamIndexService.load(bamIndex.getFdId());
		// 如果作业驳回，则给新教师发消息
		if(bamIndex.getStatus() == 3){
			notifyService.guidToDoNewTeach(bIndex, 2);
		}
		// 如果作业包下的所有作业都通过，则将相应的作业包步骤通过
		if (bamIndex.getStatus() == 4) {// 如果状态为通过
			BamOperation bamOperation = bIndex.getBamOperation();
			String operationId = bamOperation.getFdId();

			Finder finder = Finder.create("select b from BamIndex b left join fetch b.bamOperation o where o.fdId=:operationId and b.status <> :status");
			finder.setParam("operationId", operationId);
			finder.setParam("status", 4);
			List list = bamIndexService.find(finder);
			if (list == null || list.size() <= 0) {// 如果没有未通过的课件，设置为通过
				BamOperation operation = new BamOperation();
				operation.setFdId(operationId);
				operation.setThrough(true);
				bamOperationService.setThrough(operation);
			}
		}
		String packageId = bIndex.getBamOperation().getBamPackage().getFdId();
		
		return String.format("redirect:/coach/package/edit/%s", packageId);
	}

}