package cn.xdf.me.otp.action.view.model;


/**
 * 页面日历提示信息
 * @author Zaric
 * @date  2013-6-1 上午12:39:45
 */
//{title:"广州学校/英联帮",pass:true,date:'2012-11-19',place:'新东方南楼第五会议室',teach:'郑茵 乐静',newteach:'张三',remark:'请携带雅思听力第四课课件打印稿3份。'},
public class VGrade {

	/**
	 * 批课主题
	 */
	private String title;

	/**
	 * 已批｜未批： true|false
	 */
	private Boolean pass;

	/**
	 * 批课日期
	 */
	private String date;
	
	/**
	 * 批课时间
	 */
	private String time;

	/**
	 * 批课地点
	 */
	private String place;
	
	/**
	 * 批课老师
	 */
	private String teach;
	
	/**
	 * 新教师
	 */
	private String newteach;
	
	/**
	 * 批课备注
	 */
	private String remark;
	
	public VGrade() {
	}
	
	public VGrade(String title, Boolean pass, String date, String time,String place, String teach, String newteach, String remark) {
		super();
		this.title = title;
		this.pass = pass;
		this.date = date;
		this.time = time;
		this.place = place;
		this.teach = teach;
		this.newteach = newteach;
		this.remark = remark;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTeach() {
		return teach;
	}

	public void setTeach(String teach) {
		this.teach = teach;
	}

	public String getNewteach() {
		return newteach;
	}

	public void setNewteach(String newteach) {
		this.newteach = newteach;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
