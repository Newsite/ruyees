package ruyees.otp.action.view.model;

/**
 * 报表查询条件
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:41:35
 */
public class VReportFactor {

	public VReportFactor() {
	}

	/**
	 * 所属课程
	 */
	private String course;

	/**
	 * 所属分项
	 */
	private String item;

	/**
	 * 所属项目
	 */
	private String program;

	/**
	 * 所属阶段
	 */
	private String stage;

	/**
	 * 学校
	 */
	private String schIds;

	/**
	 * 指导教师
	 */
	private String guidIds;

	/**
	 * 新教师
	 */
	private String newTeachId;

	/**
	 * 开始日期
	 */
	private String startDate;

	/**
	 * 结束日期
	 */
	private String endDate;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 生成报表者
	 */
	private String reporterId;

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSchIds() {
		return schIds;
	}

	public void setSchIds(String schIds) {
		this.schIds = schIds;
	}

	public String getGuidIds() {
		return guidIds;
	}

	public void setGuidIds(String guidIds) {
		this.guidIds = guidIds;
	}

	public String getNewTeachId() {
		return newTeachId;
	}

	public void setNewTeachId(String newTeachId) {
		this.newTeachId = newTeachId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReporterId() {
		return reporterId;
	}

	public void setReporterId(String reporterId) {
		this.reporterId = reporterId;
	}

}
