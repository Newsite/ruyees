package ruyees.otp.model.flow;import java.sql.Timestamp;import java.util.Iterator;import java.util.List;import javax.persistence.CascadeType;import javax.persistence.Column;import javax.persistence.Entity;import javax.persistence.FetchType;import javax.persistence.JoinColumn;import javax.persistence.ManyToOne;import javax.persistence.OneToMany;import javax.persistence.OrderBy;import javax.persistence.Table;import javax.persistence.Transient;import javax.persistence.UniqueConstraint;import org.hibernate.annotations.Cache;import org.hibernate.annotations.CacheConcurrencyStrategy;import ruyees.otp.common.utils.array.ArrayUtils;import ruyees.otp.common.utils.array.SortType;import ruyees.otp.model.IdEntity;import ruyees.otp.model.template.TemplateItem;import ruyees.otp.utils.PhaseUtils;/** *  * 流程阶段记录 * <p/> *  * <pre> * 此记录作为流程状态和个人在备课流程中的进度来存储记录 * </pre> *  * @author Zaric * @date 2013-6-1 上午1:18:05 */@Entity@Table(name = "IXDF_OTP_BAM_STAGE", uniqueConstraints = { @UniqueConstraint(columnNames = {		"newteachId", "projectId", "fdIndex" }) })@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)public class BamStage extends IdEntity {	/**	 * 	 */	private static final long serialVersionUID = 1L;	/**	 * 对应备课流程	 */	private BamProject project;	/**	 * 对应流程阶段	 */	private TemplateItem templateItem;	/**	 * 对应新教师	 */	private String newteachId;	/**	 * 对应指导教师	 */	private String guidId;	/**	 * 进入日期	 */	private Timestamp startTime;	/**	 * 离开日期	 */	private Timestamp endTime;	/**	 * 经历时长	 */	private Long fdTime;	/**	 * 是否通过	 */	private boolean through;	/**	 * 顺序	 */	private int fdIndex;	/**	 * 对应流程步骤记录	 */	private List<BamProjectPhase> bamProjectPhases;	/**	 * 对应备课流程	 * 	 * @return	 */	@ManyToOne(fetch = FetchType.LAZY)	@JoinColumn(name = "projectId", nullable = false)	public BamProject getProject() {		return project;	}	/**	 * 对应备课流程	 * 	 * @param project	 */	public void setProject(BamProject project) {		this.project = project;	}	/**	 * 对应模板阶段	 * 	 * @return	 */	@ManyToOne(fetch = FetchType.LAZY)	@JoinColumn(name = "templateItemId")	public TemplateItem getTemplateItem() {		return templateItem;	}	/**	 * 对应模板阶段	 * 	 * @param templateItem	 */	public void setTemplateItem(TemplateItem templateItem) {		this.templateItem = templateItem;	}	/**	 * 对应新教师id	 * 	 * @return	 */	@Column(length = 32, nullable = false)	public String getNewteachId() {		return newteachId;	}	/**	 * 对应新老师id	 * 	 * @param newteachId	 */	public void setNewteachId(String newteachId) {		this.newteachId = newteachId;	}	/**	 * 对应指导教师id	 * 	 * @return	 */	@Column(length = 32)	public String getGuidId() {		return guidId;	}	/**	 * 对应指导教师id	 * 	 * @param guidId	 */	public void setGuidId(String guidId) {		this.guidId = guidId;	}	/**	 * 进入阶段时间	 * 	 * @return	 */	public Timestamp getStartTime() {		return startTime;	}	/**	 * 进入阶段开始时间	 * 	 * @param startTime	 */	public void setStartTime(Timestamp startTime) {		this.startTime = startTime;	}	/**	 * 离开阶段时间	 * 	 * @return	 */	public Timestamp getEndTime() {		return endTime;	}	/**	 * 离开阶段时间	 * 	 * @param endTime	 */	public void setEndTime(Timestamp endTime) {		this.endTime = endTime;	}	/**	 * 经历时长	 * 	 * @return	 */	public Long getFdTime() {		return fdTime;	}	/**	 * 经历时长	 * 	 * @param fdTime	 */	public void setFdTime(Long fdTime) {		this.fdTime = fdTime;	}	/**	 * 是否通过	 * 	 * @return	 */	public boolean getThrough() {		return through;	}	/**	 * 是否通过	 * 	 * @param through	 */	public void setThrough(boolean through) {		this.through = through;	}	/**	 * 顺序	 * 	 * @return	 */	@Column(nullable = false)	public int getFdIndex() {		return fdIndex;	}	/**	 * 顺序	 * 	 * @param fdIndex	 */	public void setFdIndex(int fdIndex) {		this.fdIndex = fdIndex;	}	/**	 * 流程步骤记录	 * 	 * @return	 */	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,			CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "stage")	@OrderBy("fdIndex asc")	public List<BamProjectPhase> getBamProjectPhases() {		return bamProjectPhases;	}	/**	 * 流程步骤记录	 * 	 * @param bamProjectPhases	 */	public void setBamProjectPhases(List<BamProjectPhase> bamProjectPhases) {		this.bamProjectPhases = bamProjectPhases;	}	/**	 * <pre>	 * 获取当前阶段的	 * </pre>	 * 	 * <pre>	 * 下一个阶段	 * </pre>	 * 	 * @return	 */	@Transient	public BamStage next() {		BamProject project = getProject();		List<BamStage> targetStage = project.getBamStages();		List<BamStage> statges = ArrayUtils.getListByPropertyValue(targetStage,				"newteachId", newteachId);		ArrayUtils.sortListByProperty(statges, "fdIndex", SortType.HIGHT);		for (Iterator<BamStage> iterator = statges.iterator(); iterator				.hasNext();) {			BamStage bamStage = (BamStage) iterator.next();			if (!bamStage.getNewteachId().equals(newteachId)) {				continue;			}			if (bamStage.getFdId().equals(fdId)) {				if (iterator.hasNext()) {					return (BamStage) iterator.next();				}			}		}		return null;	}	/**	 * <pre>	 * 获取当前阶段的	 * </pre>	 * 	 * <pre>	 * 	 * </pre>	 * 	 * 上一个	 * 	 * @return	 */	public BamStage last() {		if (fdIndex == 1)			return null;		BamProject project = getProject();		List<BamStage> targetStage = project.getBamStages();		List<BamStage> statges = ArrayUtils.getListByPropertyValue(targetStage,				"newteachId", newteachId);		ArrayUtils.sortListByProperty(statges, "fdIndex", SortType.HIGHT);		for (int i = 0; i < statges.size(); i++) {			BamStage bamStage = statges.get(i);			if (bamStage.getFdId().equals(fdId)) {				return statges.get(i - 1);			}		}		return null;	}	private String projectId;	@Transient	public String getProjectId() {		if (project != null)			return project.getFdId();		return projectId;	}	public void setProjectId(String projectId) {		this.projectId = projectId;	}	/**	 * 当前状态是否 是否有效	 * 	 * <pre>	 * 1:根据本关去查询上一关是否通过。	 * 	 *   	1.1:如果上一关通过，则本关有效。	 * 	    1.2:如果上一关未通过，则本关无效。	 * 	 * </pre>	 * 	 * @return	 */	@Transient	public boolean getEnable() {		if (fdIndex == 1)			return true;		BamProject project = getProject();		List<BamStage> targetStage = project.getBamStages();		List<BamStage> statges = ArrayUtils.getListByPropertyValue(targetStage,				"newteachId", newteachId);		ArrayUtils.sortListByProperty(statges, "fdIndex", SortType.HIGHT);		for (int i = 0; i < statges.size(); i++) {			BamStage stage = statges.get(i);			if (stage.getFdId().equals(fdId)) {				return statges.get(i - 1).getThrough();			}		}		return false;	}	@Transient	public String getSelectIsLock() {		if (getEnable())			return "<div class=\"ch-info\"></div>";		return "<div class=\"ch-info lock-bg\"><div class=\"lock\"></div></div>";	}	/**	 * ${ctx }/trainee/main/${name}/${s.index+1}	 * 	 * @param baseUrl	 * @param index	 * @return	 */	@Transient	public String checkHref(String baseUrl, String name) {		if (getEnable()) {			return String.format("href=\"%s/trainee/main/%s/%s\"", baseUrl,					name, fdIndex);		}		return null;	}	/**	 * 获取当前阶段的第一个步骤	 * 	 * @return	 */	@Transient	public BamProjectPhase firstPhase() {		Iterator<BamProjectPhase> iterator = getBamProjectPhases().iterator();		if (iterator.hasNext()) {			return (BamProjectPhase) iterator.next();		}		return null;	}	@Transient	public int getGoTo() {		return PhaseUtils.fetchUrl(through, fdIndex);	}	/**	 * 是否需要审核	 */	private boolean assessment;	/**	 * 是否需要审核	 */	public boolean getAssessment() {		return assessment;	}	/**	 * 是否需要审核	 * 	 * @param assessment	 */	public void setAssessment(boolean assessment) {		this.assessment = assessment;	}	/**	 * 默认评语	 */	private String defaultComment;	/**	 * 默认评语	 */	public String getDefaultComment() {		return defaultComment;	}	/**	 * 默认评语	 * 	 * @param defaultComment	 */	public void setDefaultComment(String defaultComment) {		this.defaultComment = defaultComment;	}}