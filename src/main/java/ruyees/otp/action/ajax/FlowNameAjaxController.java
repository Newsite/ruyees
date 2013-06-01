package ruyees.otp.action.ajax;import javax.servlet.http.HttpServletRequest;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Scope;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.bind.annotation.ResponseBody;import ruyees.otp.common.hibernate4.Finder;import ruyees.otp.service.flow.BamProjectService;/** *  * @author Zaric * @date 2013-6-1 上午12:32:28 */@Controller@RequestMapping(value = "/ajax/flow")@Scope("request")public class FlowNameAjaxController {	@Autowired	private BamProjectService bamProjectService;	@RequestMapping(value = "findName")	@ResponseBody	public String findByName(HttpServletRequest request) {		String key = request.getParameter("key");		Boolean isExists = false;		Finder finder = Finder.create("from BamProject b where b.name=:name");		finder.setParam("name", key);		isExists = bamProjectService.hasValue(finder);		return isExists.toString();	}	/**	 * 删除人员	 * 	 * @param request	 * @return	 */	@RequestMapping(value = "deleteAss")	@ResponseBody	public String deleteAss(HttpServletRequest request) {		String key = request.getParameter("key");		bamProjectService.deleteProject(key);		return "true";	}	/**	 * 移出授权	 * 	 * @param request	 * @return	 */	@RequestMapping(value = "removeAss")	@ResponseBody	public String removeAss(HttpServletRequest request) {		String key = request.getParameter("key");		bamProjectService.removeAss(key);		return "true";	}	/**	 * 添加授权	 * 	 * @param request	 * @return	 */	@RequestMapping(value = "addAss")	@ResponseBody	public String addAss(HttpServletRequest request) {		String key = request.getParameter("key");		bamProjectService.addAss(key);		return "true";	}	@RequestMapping(value = "checkFlowUser", method = RequestMethod.POST)	@ResponseBody	public String checkFlowUser(HttpServletRequest request) {		String flowId = request.getParameter("flowId");		String userId = request.getParameter("uid");		String schId = request.getParameter("schId");		if (bamProjectService.checkFlowAndUser(flowId, schId, userId)) {			return "true";		}		return "false";	}}