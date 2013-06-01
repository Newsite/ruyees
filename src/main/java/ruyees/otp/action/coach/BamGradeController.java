package ruyees.otp.action.coach;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import ruyees.otp.action.view.model.VPlan;
import ruyees.otp.action.view.model.VRecord;
import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.common.utils.ComUtils;
import ruyees.otp.common.utils.excel.AbsExportExcel;
import ruyees.otp.model.base.NewItemConf;
import ruyees.otp.model.flow.BamProject;
import ruyees.otp.model.flow.BamProjectMember;
import ruyees.otp.model.flow.BamProjectPhase;
import ruyees.otp.model.flow.BamStage;
import ruyees.otp.model.flow.Grade;
import ruyees.otp.model.flow.GradeItem;
import ruyees.otp.model.sys.SysOrgElement;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;
import ruyees.otp.service.SysOrgElementService;
import ruyees.otp.service.base.GradeItemService;
import ruyees.otp.service.base.GradeService;
import ruyees.otp.service.flow.BamProjectService;

/**
 * 指导教师对新教师进行打分
 * @author Zaric
 * @date  2013-6-1 上午12:37:18
 */
@Controller
@RequestMapping(value = "/coach/grade")
@Scope("request")
public class BamGradeController {
	
	@Autowired
	private GradeService gradeService;

	@Autowired
	private GradeItemService gradeItemService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private BamProjectService bamProjectService;

	@Autowired
	private SysOrgElementService sysOrgElementService;

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "grade");
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		// 取模板
		List<BamProject> projects = bamProjectService.findByCriteria(BamProject.class, Value.eq("status", 2));
		Set<BamProject> projetSet = new HashSet<BamProject>();
		for(BamProject project:projects){
			if(project!=null){
				List<BamProjectMember> members=project.getBamProjectMembers();
				for(BamProjectMember member:members){
					if(member!=null){
						if(uId.equals(member.getGuidId())){
							projetSet.add(project);
						}
					}
				}
			}
		}
		// 列表
		Finder finder = Finder.create("from Grade g");
		finder.append("where g.guidId=:guidId").setParam("guidId", uId);
		Pagination page = gradeService.getPage(finder, NumberUtils.createInteger(pageNo));

		List<Grade> lists = (List<Grade>) page.getList();
		for (Grade grade : lists) {
			grade.setNewTeach(accountService.findById(grade.getUid()));
		}
		model.addAttribute("page", page);
		model.addAttribute("projects", projetSet);

		return "/base/grade/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add/{projectId}", method = RequestMethod.GET)
	public String add(Model model, @PathVariable("projectId") String projectId) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;

		Finder finder = Finder.create("from BamProjectMember b where b.guidId=:guidId and b.project.fdId=:projectId");
		finder.setParam("guidId", uId);
		finder.setParam("projectId", projectId);

		List<BamProjectMember> members = gradeService.find(finder);
		for (BamProjectMember member : members) {
			member.setNewTeach(accountService.findById(member.getNewteachId()));
		}

		// 取学校
		BamProject bamProject = bamProjectService.get(projectId);
		SysOrgElement sys = sysOrgElementService.get(bamProject.getSchId());
		model.addAttribute("deptName", sys.getFdName());
		model.addAttribute("bamProject", bamProject);
		model.addAttribute("projectId", projectId);
		model.addAttribute("members", members);

		return "/base/grade/add";
	}

	@RequestMapping(value = "/twoAdd/{gradeId}", method = RequestMethod.GET)
	public String twoAdd(Model model, @PathVariable("gradeId") String gradeId, HttpServletRequest request) {
		Grade grade = gradeService.get(Grade.class, gradeId);
		grade.setNewTeach(accountService.findById(grade.getUid()));
		List<GradeItem> items = grade.getGradeItems();
		for (GradeItem item : items) {
			item.setAdvier(accountService.findById(item.getAdvierId()));
		}
		model.addAttribute("bean", grade);
		
		// 取学校
		BamProject bamProject = grade.getPhase().getStage().getProject();
		SysOrgElement sys = sysOrgElementService.get(bamProject.getSchId());
		model.addAttribute("deptName", sys.getFdName());
		model.addAttribute("bamProject", bamProject);
		return "/base/grade/twoAdd";
	}
	
	@RequestMapping(value = "/itemEdit/{gradeId}", method = RequestMethod.GET)
	public String itemEdit(Model model, @PathVariable("gradeId") String gradeId, HttpServletRequest request) {
		Grade grade = gradeService.get(Grade.class, gradeId);
		grade.setNewTeach(accountService.findById(grade.getUid()));
		List<GradeItem> items = grade.getGradeItems();
		for (GradeItem item : items) {
			item.setAdvier(accountService.findById(item.getAdvierId()));
		}
		model.addAttribute("bean", grade);
		
		// 取学校
		BamProject bamProject = grade.getPhase().getStage().getProject();
		SysOrgElement sys = sysOrgElementService.get(bamProject.getSchId());
		model.addAttribute("deptName", sys.getFdName());
		model.addAttribute("bamProject", bamProject);
		return "/base/grade/itemEdit";
	}

	@RequestMapping(value = "/edit/{gradeId}", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("gradeId") String gradeId, HttpServletRequest request) {
		Grade grade = gradeService.get(Grade.class, gradeId);
		grade.setNewTeach(accountService.findById(grade.getUid()));
		List<GradeItem> items = grade.getGradeItems();
		for (GradeItem item : items) {
			item.setAdvier(accountService.findById(item.getAdvierId()));
		}
		model.addAttribute("bean", grade);

		return "/base/grade/edit";
	}

	@RequestMapping(value = "/view/{gradeId}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable("gradeId") String gradeId, HttpServletRequest request) {
		Grade grade = gradeService.get(Grade.class, gradeId);
		grade.setNewTeach(accountService.findById(grade.getUid()));
		List<GradeItem> items = grade.getGradeItems();
		for (GradeItem item : items) {
			item.setAdvier(accountService.findById(item.getAdvierId()));
		}
		model.addAttribute("bean", grade);
		model.addAttribute("method", "edit");

		return "/base/grade/view";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Grade grade, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String guidId = user.id;
		String projectId = request.getParameter("projectId");
		String uid = request.getParameter("uid");

		gradeService.saveGradeAndTickling(grade, guidId, projectId, uid);

		return "redirect:/coach/grade/list";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Grade grade, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		gradeService.updateGradeValue(grade);
		return "redirect:/coach/grade/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/item/{gradeId}/{itemId}", method = RequestMethod.GET)
	public String item(Model model, @PathVariable("gradeId") String gradeId, @PathVariable("itemId") String itemId, HttpServletRequest request) {
		Grade grade = gradeService.get(Grade.class, gradeId);
		BamProjectPhase phase = grade.getPhase();
		Finder finder = Finder.create("from NewItemConf n where n.templateContent.fdId=:fdId");
		finder.setParam("fdId", phase.getContent().getFdId());
		List<NewItemConf> news = gradeService.find(finder);
		// 批课分项
		GradeItem item = gradeItemService.get(itemId);
		item.setNewTeach(accountService.findById(item.getUid()));
		// 学校
		BamStage stage = phase.getStage();
		BamProject bamProject = stage.getProject();
		SysOrgElement sys = sysOrgElementService.get(bamProject.getSchId());
		// 导师
		SysOrgPerson guid = accountService.findById(grade.getGuidId());

		model.addAttribute("sys", sys);
		model.addAttribute("guid", guid);
		model.addAttribute("news", news);
		model.addAttribute("item", item);
		model.addAttribute("grade", grade);
		model.addAttribute("count", news.size());

		return "/base/grade/item";
	}

	@RequestMapping(value = "upItem", method = RequestMethod.POST)
	public String upItem(GradeItem gradeItem, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String gradeId = request.getParameter("gradeId");
		gradeItemService.updateGradeItem(gradeItem);
		return String.format("redirect:/coach/grade/edit/%s", gradeId);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/itemView/{gradeId}/{itemId}", method = RequestMethod.GET)
	public String itemView(Model model, @PathVariable("gradeId") String gradeId, @PathVariable("itemId") String itemId) {
		Grade grade = gradeService.get(Grade.class, gradeId);
		BamProjectPhase phase = grade.getPhase();
		Finder finder = Finder.create("from NewItemConf n where n.templateContent.fdId=:fdId");
		finder.setParam("fdId", phase.getContent().getFdId());
		List<NewItemConf> news = gradeService.find(finder);
		// 批课分项
		GradeItem item = gradeItemService.get(itemId);
		item.setNewTeach(accountService.findById(item.getUid()));
		// 学校
		BamStage stage = phase.getStage();
		BamProject bamProject = stage.getProject();
		SysOrgElement sys = sysOrgElementService.get(bamProject.getSchId());
		// 导师
		SysOrgPerson guid = accountService.findById(grade.getGuidId());

		model.addAttribute("sys", sys);
		model.addAttribute("guid", guid);
		model.addAttribute("news", news);
		model.addAttribute("item", item);
		model.addAttribute("grade", grade);

		return "/base/grade/itemView";
	}
	
	@RequestMapping(value = "/expRecord/{gradeId}/{itemId}", method = RequestMethod.GET)
	public void expRecord(Model model, @PathVariable("gradeId") String gradeId, @PathVariable("itemId") String itemId,HttpServletResponse response) {
		List<VRecord>  recordList=new ArrayList<VRecord>();
		VRecord record=new VRecord();
		Grade grade = gradeService.get(Grade.class, gradeId);
		record.setTitle(grade.getTitle());
		// 批课分项
		GradeItem item = gradeItemService.get(itemId);
		item.setNewTeach(accountService.findById(item.getUid()));
		// 导师
		SysOrgPerson guid = accountService.findById(grade.getGuidId());
		record.setNewRealName(item.getNewTeach().getRealName());
		record.setGuidRealName(guid.getRealName());
		record.setBeginTime(item.getStartTime().toString());
		record.setEndTime(item.getEndTime().toString());
		record.setRecord(item.getRecord());
		record.setMerit(item.getMerit());
		record.setValue(item.getValue().toString());
		record.setValue1(item.getValue1().toString());
		record.setValue2(item.getValue2().toString());
		record.setValue3(item.getValue3().toString());
		record.setValue4(item.getValue4().toString());
		record.setValue5(item.getValue5().toString());
		record.setValue6(item.getValue6().toString());
		record.setValue7(item.getValue7().toString());
		record.setValue8(item.getValue8().toString());
		
		record.setRemark1(item.getRemark1());
		record.setRemark2(item.getRemark2());
		record.setRemark3(item.getRemark3());
		record.setRemark4(item.getRemark4());
		record.setRemark5(item.getRemark5());
		record.setRemark6(item.getRemark6());
		record.setRemark7(item.getRemark7());
		record.setRemark8(item.getRemark8());
		
		record.setProblem(item.getProblem());
		record.setAdvise(item.getAdvise());
		record.setOverall(item.getOverall());
		// 学校
		BamProjectPhase phase = grade.getPhase();
		BamStage stage = phase.getStage();
		BamProject bamProject = stage.getProject();
		SysOrgElement sys = sysOrgElementService.get(bamProject.getSchId());
		record.setFdOrgName(sys.getFdName());
		record.setFdProName(bamProject.getTemplate().getProgram().getFdName());
		record.setFdCourName(bamProject.getTemplate().getCourse().getFdName());
		record.setFdItemName(bamProject.getTemplate().getItem().getFdName());
		recordList.add(record);
		AbsExportExcel.exportExcel(recordList, "total_eval.xls", response);
		return ;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/excelExp/{gradeId}", method = RequestMethod.GET)
	public void excelExp(Model model, @PathVariable("gradeId") String gradeId, HttpServletRequest request,HttpServletResponse response) {
		Finder finder=Finder.create("from GradeItem gi where gi.grade.fdId=:fdId");
		finder.setParam("fdId", gradeId);
		List<GradeItem> itemList=gradeItemService.find(finder);
		
		List<VPlan> planList=new ArrayList<VPlan>();
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yrSdf = new SimpleDateFormat("MM月dd日"); 
		SimpleDateFormat sfSdf = new SimpleDateFormat("HH:mm"); 
		int totalCount=0;
		for(GradeItem item:itemList){
			String dateStr=dateSdf.format(item.getStartTime()); //2013-01-30
			Calendar c=ComUtils.getCalendarDay(dateStr);
			int min = c.getActualMinimum(Calendar.DAY_OF_WEEK); //获取周开始基准
			int current = c.get(Calendar.DAY_OF_WEEK); //获取当天周内天数
			int year = c.get(Calendar.YEAR);
			int week = c.get(Calendar.WEEK_OF_YEAR);
			int dayOfWeek=c.get(Calendar.DAY_OF_WEEK)-1;//获取当天是星期几
			
			c.add(Calendar.DAY_OF_WEEK, min-current); //当天-基准，获取周开始日期
			String first = yrSdf.format(c.getTime());
			c.add(Calendar.DAY_OF_WEEK, 1); 
			String second = yrSdf.format(c.getTime());
			c.add(Calendar.DAY_OF_WEEK, 1); 
			String third = yrSdf.format(c.getTime());
			c.add(Calendar.DAY_OF_WEEK, 1); 
			String forth = yrSdf.format(c.getTime());
			c.add(Calendar.DAY_OF_WEEK, 1); 
			String fifth = yrSdf.format(c.getTime());
			c.add(Calendar.DAY_OF_WEEK, 1); 
			String sixth = yrSdf.format(c.getTime());
			c.add(Calendar.DAY_OF_WEEK, 1); 
			String seventh = yrSdf.format(c.getTime());
			
			int count=0;
			String timeTitle=year+"年第"+week+"周";
			//日期行
			if(planList.size()==0){
				VPlan v=new VPlan(timeTitle,first,second,third,forth,fifth,sixth,seventh);
				planList.add(v);
			}else{
				for(VPlan v:planList){
					if(timeTitle.equals(v.getTime())){
						count++;
					}
				}
				if(count==0){
					VPlan v=new VPlan(timeTitle,first,second,third,forth,fifth,sixth,seventh);
					planList.add(v);
				}
			}
			//内容行
			VPlan v=new VPlan();
			v.setTime(sfSdf.format(item.getStartTime())+"-"+sfSdf.format(item.getEndTime()));
			switch (dayOfWeek) {
			case 0:
				v.setFirstCont(item.getAddress()+"\n"+item.getRemark());break;
			case 1:
				v.setSecondCont(item.getAddress()+"\n"+item.getRemark());break;
			case 2:
				v.setThirdCont(item.getAddress()+"\n"+item.getRemark());break;
			case 3:
				v.setForthCont(item.getAddress()+"\n"+item.getRemark());break;
			case 4:
				v.setFifthCont(item.getAddress()+"\n"+item.getRemark());break;
			case 5:
				v.setSixthCont(item.getAddress()+"\n"+item.getRemark());break;
			case 6:
				v.setSeventhCont(item.getAddress()+"\n"+item.getRemark());break;
			}
			planList.add(v);
			
			totalCount++;
		}
		//VPlan v=new VPlan(dateSdf.format(new Date()),accountService.findById(itemList.get(0).getUid()).getRealName(),String.valueOf(totalCount));
		//planList.add(v);
		
		AbsExportExcel.exportExcel(planList, "class_plan.xls", response);
		return ;
	}
}
