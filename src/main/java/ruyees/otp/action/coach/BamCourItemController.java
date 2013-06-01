package ruyees.otp.action.coach;import javax.servlet.http.HttpServletRequest;import org.apache.commons.lang3.math.NumberUtils;import org.apache.shiro.SecurityUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Scope;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.servlet.mvc.support.RedirectAttributes;import ruyees.otp.common.hibernate4.Finder;import ruyees.otp.common.page.Pagination;import ruyees.otp.model.flow.BamCoursewareItem;import ruyees.otp.model.sys.SysOrgPerson;import ruyees.otp.service.AccountService;import ruyees.otp.service.ShiroDbRealm.ShiroUser;import ruyees.otp.service.flow.BamCoursewareItemService;/** * 指导教师审批课件分项 *  * @author Zaric * @date 2013-6-1 上午12:36:54 */@Controller@RequestMapping(value = "/coach/courItem")@Scope("request")public class BamCourItemController {	@Autowired	private BamCoursewareItemService bamCoursewareItemService;	@Autowired	private AccountService accountService;	@RequestMapping(value = "list/{index}")	public String list(Model model, String pageNo, HttpServletRequest request,			@PathVariable("index") String index) {		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();		String uId = user.id;		Finder finder = Finder				.create("select b from BamCoursewareItem b left join fetch b.bamCourseware c left join fetch c.phase phase left join fetch phase.stage stage where phase.guidId=:guidId and stage.fdIndex = :fdIndex and b.status in(:status)");		finder.setParam("fdIndex", Integer.parseInt(index));		finder.setParam("guidId", uId);		finder.setParamList("status", new Integer[] { 2, 3, 4 });// 2：已提交,3:驳回,4审核通过		finder.append("order by b.name");// order by b.through,b.fdId		// finder.append("order by b.fdId");//order by b.through,b.fdId		Pagination page = bamCoursewareItemService.getPage(finder,				NumberUtils.createInteger(pageNo));		for (Object obj : page.getList()) {			BamCoursewareItem bamItem = (BamCoursewareItem) obj;			SysOrgPerson person = accountService.findById(bamItem					.getBamCourseware().getPhase().getNewteachId());			bamItem.getBamCourseware().getPhase().setNewTeach(person);		}		model.addAttribute("page", page);		return String.format("/base/courItem/%s/list", index);	}	@RequestMapping(value = "edit/{index}/{id}")	public String edit(Model model, @PathVariable("id") String itemId,			@PathVariable("index") String index) {		BamCoursewareItem bamItem = bamCoursewareItemService.load(itemId);		SysOrgPerson person = accountService.findById(bamItem				.getBamCourseware().getPhase().getNewteachId());		model.addAttribute("bean", bamItem);		model.addAttribute("person", person);		return String.format("/base/courItem/%s/edit", index);	}	@RequestMapping(value = "view/{index}/{id}")	public String view(Model model, @PathVariable("id") String itemId,			@PathVariable("index") String index) {		BamCoursewareItem bamItem = bamCoursewareItemService.load(itemId);		SysOrgPerson person = accountService.findById(bamItem				.getBamCourseware().getPhase().getNewteachId());		model.addAttribute("bean", bamItem);		model.addAttribute("person", person);		return String.format("/base/courItem/%s/view", index);	}	@RequestMapping(value = "save/{index}", method = RequestMethod.POST)	public String save(BamCoursewareItem bamCoursewareItem,			RedirectAttributes redirectAttributes, HttpServletRequest request,			@PathVariable("index") String index) {		bamCoursewareItemService				.saveBamCoursewareItem(bamCoursewareItem, index);		BamCoursewareItem bci = bamCoursewareItemService.load(bamCoursewareItem				.getFdId());		String coursewareId = bci.getBamCourseware().getFdId();		return String.format("redirect:/coach/courseware/edit/%s/%s", index,				coursewareId);	}}