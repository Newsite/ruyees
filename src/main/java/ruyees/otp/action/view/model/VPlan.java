package ruyees.otp.action.view.model;

/**
 * 报表查询条件
 * @author Zaric
 * @date  2013-6-1 上午12:40:46
 */
public class VPlan {

	public VPlan() {
	}

	
	public VPlan(String time, String firstCont, String secondCont, String thirdCont, String forthCont, String fifthCont, String sixthCont, String seventhCont) {
		super();
		this.time = time;
		this.firstCont = firstCont;
		this.secondCont = secondCont;
		this.thirdCont = thirdCont;
		this.forthCont = forthCont;
		this.fifthCont = fifthCont;
		this.sixthCont = sixthCont;
		this.seventhCont = seventhCont;
	}

	public VPlan(String currentDate, String newTeachName, String totalCount) {
		super();
		this.currentDate = currentDate;
		this.newTeachName = newTeachName;
		this.totalCount = totalCount;
	}

	/**
	 * 时间
	 */
	private String time;

	/**
	 * 星期日内容
	 */
	private String firstCont;

	/**
	 * 星期一内容
	 */
	private String secondCont;

	/**
	 * 星期二内容
	 */
	private String thirdCont;

	/**
	 * 星期三内容
	 */
	private String forthCont;

	/**
	 * 星期四内容
	 */
	private String fifthCont;

	/**
	 * 星期五内容
	 */
	private String sixthCont;

	/**
	 * 星期六内容
	 */
	private String seventhCont;
	
	/**
	 * 导出日期
	 */
	private String currentDate;
	
	/**
	 * 新教师姓名
	 */
	private String newTeachName;
	
	/**
	 * 批课次数共计
	 */
	private String totalCount;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFirstCont() {
		return firstCont;
	}

	public void setFirstCont(String firstCont) {
		this.firstCont = firstCont;
	}

	public String getSecondCont() {
		return secondCont;
	}

	public void setSecondCont(String secondCont) {
		this.secondCont = secondCont;
	}

	public String getThirdCont() {
		return thirdCont;
	}

	public void setThirdCont(String thirdCont) {
		this.thirdCont = thirdCont;
	}

	public String getForthCont() {
		return forthCont;
	}

	public void setForthCont(String forthCont) {
		this.forthCont = forthCont;
	}

	public String getFifthCont() {
		return fifthCont;
	}

	public void setFifthCont(String fifthCont) {
		this.fifthCont = fifthCont;
	}

	public String getSixthCont() {
		return sixthCont;
	}

	public void setSixthCont(String sixthCont) {
		this.sixthCont = sixthCont;
	}

	public String getSeventhCont() {
		return seventhCont;
	}

	public void setSeventhCont(String seventhCont) {
		this.seventhCont = seventhCont;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getNewTeachName() {
		return newTeachName;
	}

	public void setNewTeachName(String newTeachName) {
		this.newTeachName = newTeachName;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
}
