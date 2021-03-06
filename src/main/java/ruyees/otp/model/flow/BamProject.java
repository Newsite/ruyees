package ruyees.otp.model.flow;import java.sql.Timestamp;import java.util.ArrayList;import java.util.List;import javax.persistence.Basic;import javax.persistence.CascadeType;import javax.persistence.Column;import javax.persistence.Entity;import javax.persistence.FetchType;import javax.persistence.JoinColumn;import javax.persistence.Lob;import javax.persistence.ManyToOne;import javax.persistence.OneToMany;import javax.persistence.OrderBy;import javax.persistence.Table;import javax.persistence.Transient;import javax.persistence.UniqueConstraint;import org.hibernate.annotations.Cache;import org.hibernate.annotations.CacheConcurrencyStrategy;import org.hibernate.annotations.Cascade;import org.springframework.util.CollectionUtils;import ruyees.otp.model.IdEntity;import ruyees.otp.model.sys.SysOrgPerson;import ruyees.otp.model.template.ProTemplate;/** *  * 备课流程记录信息 * <p/> *  * <pre> * 1：用户进入备课流程发起界面，系统查询备课模板的参与人，如果模板参与人里面含有此登录用户，则此用户有权限来发起备课。 * 2：用户发起流程,选择流程参与人。 *  * 3：当学校主管选择完成用户以后，会相应的把选择的用户信息添加到：{@ruyees.otp.model.flow.BamProjectMember} * 4：如学校教研负责选择4对新教师和学校主管。则： *    4.1 首先读取备课模板数据，嵌套循环选择的人员,再次循环备课阶段，添加到 *          {@see ruyees.otp.model.flow.BamStage} （阶段记录）, *          在备课阶段里循环步骤并添加到 *          {@see ruyees.otp.model.flow.BamProjectPhase} （步骤记录）, *    4.2 注意在添加以上的阶段记录和步骤记录时，需要把对应的是否通过字段(though)值为flase *    4.3 * 流程发起人请参考：{@see ruyees.otp.model.flow.BamProjectMember} * </pre> *  * @author Zaric * @date 2013-6-1 上午1:16:42 */@Entity@Table(name = "IXDF_OTP_BAM_PROJECT", uniqueConstraints = { @UniqueConstraint(columnNames = {"fdName", "templateId", "fdUid" }) })@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)public class BamProject extends IdEntity {	/**	 * 	 */	private static final long serialVersionUID = 1L;	/**	 * 流程未开启	 */	public static final int NO_OPEN = 1;	/**	 * 流程启动	 */	public static final int IS_OPEN = 2;	/**	 * 流程无效	 */	public static final int IS_INVALID = 3;	/**	 * 流程模板	 */	private ProTemplate template;	/**	 * 流程名称	 */	private String name;	/**	 * 发起人	 */	private SysOrgPerson user;	/**	 * 发起日期	 */	private Timestamp createTime;	/**	 * 所属学校(学校)	 */	private String schId;	/**	 * 所属部门	 */	private String deptId;	/**	 * 备注	 */	private String remark;	/**	 * 流程状态 1:未发起 2:启动 3:完成 4：无效	 */	private int status;	/**	 * 对应备课流程参与人	 */	private List<BamProjectMember> bamProjectMembers;	/**	 * 对应备课流程阶段记录	 */	private List<BamStage> bamStages;	/**	 * 是否针对授权人无效	 */	private Boolean invalid;	/**	 * 对应备课流程阶段	 * 	 * @return	 */	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,			CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "project")	@OrderBy("fdIndex asc")	public List<BamStage> getBamStages() {		return bamStages;	}	/**	 * 对应备课流程阶段	 * 	 * @param bamStages	 */	public void setBamStages(List<BamStage> bamStages) {		this.bamStages = bamStages;	}	/**	 * 对应备课流程参与人	 * 	 * @return	 */	@OneToMany(mappedBy = "project", orphanRemoval = true)	@Cascade({ org.hibernate.annotations.CascadeType.ALL })	public List<BamProjectMember> getBamProjectMembers() {		if (bamProjectMembers == null) {			bamProjectMembers = new ArrayList<BamProjectMember>();		}		return bamProjectMembers;	}	/**	 * 对应备课流程参与人	 * 	 * @param bamProjectMembers	 */	public void setBamProjectMembers(List<BamProjectMember> bamProjectMembers) {		this.bamProjectMembers = bamProjectMembers;	}	/**	 * 对应流程模板	 * 	 * @return	 */	@ManyToOne(fetch = FetchType.LAZY)	@JoinColumn(name = "templateId")	public ProTemplate getTemplate() {		return template;	}	/**	 * 对应流程模板	 * 	 * @param template	 */	public void setTemplate(ProTemplate template) {		this.template = template;	}	/**	 * 名称	 * 	 * @return	 */	@Column(length = 200, name = "fdName", unique = true)	public String getName() {		return name;	}	/**	 * 名称	 * 	 * @param name	 */	public void setName(String name) {		this.name = name;	}	/**	 * 发起人	 * 	 * @return	 */	@ManyToOne(fetch = FetchType.LAZY)	@JoinColumn(name = "fdUid")	public SysOrgPerson getUser() {		return user;	}	/**	 * 发起人	 * 	 * @param user	 */	public void setUser(SysOrgPerson user) {		this.user = user;	}	/**	 * 创建时间	 * 	 * @return	 */	public Timestamp getCreateTime() {		return createTime;	}	/**	 * 创建时间	 * 	 * @param createTime	 */	public void setCreateTime(Timestamp createTime) {		this.createTime = createTime;	}	/**	 * 对应学校id	 * 	 * @return	 */	@Column(length = 32)	public String getSchId() {		return schId;	}	/**	 * 对应学校id	 * 	 * @param schId	 */	public void setSchId(String schId) {		this.schId = schId;	}	/**	 * 对应部门id	 * 	 * @return	 */	@Column(length = 32)	public String getDeptId() {		return deptId;	}	/**	 * 对应部门id	 * 	 * @param deptId	 */	public void setDeptId(String deptId) {		this.deptId = deptId;	}	/**	 * 备注	 * 	 * @return	 */	@Lob	@Basic(fetch = FetchType.LAZY)	public String getRemark() {		return remark;	}	/**	 * 备注	 * 	 * @param remark	 */	public void setRemark(String remark) {		this.remark = remark;	}	/**	 * 流程状态 1:未发起 2:启动 3:完成 4：无效	 * 	 * @return	 */	@Column(name = "fdStatus")	public int getStatus() {		return status;	}	/**	 * 流程状态 1:未发起 2:启动 3:完成 4：无效	 * 	 * @param status	 */	public void setStatus(int status) {		this.status = status;	}	/**	 * 根据人员获取流程对应的记录	 * 	 * @param nid	 * @return	 */	@Transient	public BamProjectMember getMember(String nid) {		List<BamProjectMember> members = getBamProjectMembers();		if (!CollectionUtils.isEmpty(members)) {			for (BamProjectMember m : members) {				if (m.getNewteachId().equals(nid)) {					return m;				}			}		}		return null;	}	private boolean enableByUser;	@Transient	public boolean getEnableByUser() {		return enableByUser;	}	public void setEnableByUser(boolean enableByUser) {		this.enableByUser = enableByUser;	}	/**	 * 是否针对流程授权人无效 默认为false	 * 	 * @return	 */	@Column(name = "INVALID", nullable = true)	public Boolean getInvalid() {		if (invalid == null)			return false;		return invalid;	}	public void setInvalid(Boolean invalid) {		this.invalid = invalid;	}	@SuppressWarnings("unused")	private String orderName;	@Transient	public String getOrderName() {		return template.getCourse().getFdName()				+ template.getStage().getFdName()				+ template.getItem().getFdName();	}	public void setOrderName(String orderName) {		this.orderName = orderName;	}}