package ruyees.otp.model.sys;import java.util.ArrayList;import java.util.List;import javax.persistence.AttributeOverride;import javax.persistence.AttributeOverrides;import javax.persistence.Column;import javax.persistence.Embedded;import javax.persistence.Entity;import javax.persistence.FetchType;import javax.persistence.JoinColumn;import javax.persistence.ManyToOne;import javax.persistence.Table;import javax.persistence.Transient;import org.apache.commons.lang3.StringUtils;import org.codehaus.jackson.annotate.JsonIgnore;import org.hibernate.annotations.Cache;import org.hibernate.annotations.CacheConcurrencyStrategy;import org.springframework.util.CollectionUtils;import ruyees.otp.model.IdEntity;import ruyees.otp.model.NotifyEntity;import ruyees.otp.utils.ComUtils;/** * <pre> *         此model以ixdf平台的数据库视图来引用。 *        <b>1</b>：在连接到oracle时，SysOrgPerson是只读的。 *        <b>2</b>：所有域类对SysOrgPerson的引用建议使用弱关联 *        <b>3</b>:如果对此类建立关联的话，必须使用单向关联。 *        关于权限： *        <b>1</b>：详细请查看 @see ruyees.otp.model.sys.UserRole *        @see ruyees.otp.model.sys.RoleEnum * </pre> * 个人 * @author Zaric * @date 2013-6-1 上午1:21:49 */@Entity// @Table(name = "SYS_ORG_PERSON_OTP", schema = "DB_IXDF")@Table(name = "SYS_ORG_PERSON")@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)public class SysOrgPerson extends IdEntity {	/**	 * 	 */	private static final long serialVersionUID = 1L;	/**	 * 人员编码	 */	private String fdNo;	/**	 * 对应登录名	 */	private String loginName;	/**	 * 用户登录密码	 */	private String password;	/**	 * 用户初始密码	 */	private String fdInitPassword;	/**     *     */	private String fdAttendanceCardNumber;	/**	 * 用户工作电话	 */	private String fdWorkPhone;	/**	 * 用户qq	 */	private String fdQq;	/**	 * 用户msn	 */	private String fdMsn;	private String fdIdentityCard;	/**	 * 性别	 */	private String fdSex;	/**	 * 是否在职	 */	private String fdIsEmp;	/**	 * 对应部门编码	 */	private SysOrgElement hbmParent;	// 部门名称	private String deptName;	// 仅作为页面引用，不会持久化到数据库	private String plainPassword;	// 仅作为业务代码引用，不会持久化到数据库	private List<UserRole> userRoles;	private NotifyEntity notifyEntity;	// FD_PHOTO_URL	// 人员头像	private String fdPhotoUrl;	@Embedded	@AttributeOverrides({			@AttributeOverride(name = "fdMobileNo", column = @Column(name = "FD_MOBILE_NO")),			@AttributeOverride(name = "fdEmail", column = @Column(name = "FD_EMAIL")),			@AttributeOverride(name = "realName", column = @Column(name = "realName")), })	public NotifyEntity getNotifyEntity() {		return notifyEntity;	}	public void setNotifyEntity(NotifyEntity notifyEntity) {		this.notifyEntity = notifyEntity;	}	public SysOrgPerson() {	}	public SysOrgPerson(String fdId) {		this.fdId = fdId;	}	/**	 * 人员编码	 * 	 * @return	 */	@Column(name = "FD_NO")	public String getFdNo() {		return fdNo;	}	/**	 * 人员编码	 * 	 * @param fdNo	 */	public void setFdNo(String fdNo) {		this.fdNo = fdNo;	}	/**	 * 登录名	 * 	 * @return	 */	@Column(name = "FD_LOGIN_NAME")	public String getLoginName() {		return loginName;	}	public void setLoginName(String loginName) {		this.loginName = loginName;	}	/**	 * 密码	 * 	 * @return	 */	@Column(name = "FD_PASSWORD")	public String getPassword() {		return password;	}	public void setPassword(String password) {		this.password = password;	}	/**	 * 考勤卡号	 * 	 * @return	 */	@Column(name = "FD_ATTENDANCE_CARD_NUMBER")	public String getFdAttendanceCardNumber() {		return fdAttendanceCardNumber;	}	public void setFdAttendanceCardNumber(String fdAttendanceCardNumber) {		this.fdAttendanceCardNumber = fdAttendanceCardNumber;	}	/**	 * 办公电话	 * 	 * @return	 */	@Column(name = "FD_WORK_PHONE")	public String getFdWorkPhone() {		return fdWorkPhone;	}	public void setFdWorkPhone(String fdWorkPhone) {		this.fdWorkPhone = fdWorkPhone;	}	/**	 * 初使密码用于OMS	 * 	 * @return	 */	@Column(name = "FD_INIT_PASSWORD")	public String getFdInitPassword() {		return fdInitPassword;	}	public void setFdInitPassword(String fdInitPassword) {		this.fdInitPassword = fdInitPassword;	}	/**	 * QQ	 * 	 * @return	 */	@Column(name = "FD_QQ")	public String getFdQq() {		return fdQq;	}	public void setFdQq(String fdQq) {		this.fdQq = fdQq;	}	/**	 * MSN	 * 	 * @return	 */	@Column(name = "FD_MSN")	public String getFdMsn() {		return fdMsn;	}	public void setFdMsn(String fdMsn) {		this.fdMsn = fdMsn;	}	/**	 * 身份证	 * 	 * @return	 */	@Column(name = "FD_IDENTITY_CARD")	public String getFdIdentityCard() {		return fdIdentityCard;	}	public void setFdIdentityCard(String fdIdentityCard) {		this.fdIdentityCard = fdIdentityCard;	}	/**	 * 性别	 * 	 * @return	 */	@Column(name = "FD_SEX")	public String getFdSex() {		return fdSex;	}	public void setFdSex(String fdSex) {		this.fdSex = fdSex;	}	/**	 * 0:未入职 1:在职	 * 	 * @return	 */	@Column(name = "FD_IS_EMP")	public String getFdIsEmp() {		return fdIsEmp;	}	/**	 * 0：未入职 1：入职	 * 	 * @param fdIsEmp	 */	public void setFdIsEmp(String fdIsEmp) {		this.fdIsEmp = fdIsEmp;	}	/**	 * 对应的人员部门id	 * 	 * @return	 */	@Transient	public String getDepatId() {		if (hbmParent == null)			return null;		return hbmParent.getFdId();	}	/**	 * 获取上级部门	 * 	 * @return	 */	@ManyToOne(fetch = FetchType.LAZY)	@JoinColumn(name = "depatId")	public SysOrgElement getHbmParent() {		return hbmParent;	}	/**	 * 获取上级部门	 * 	 * @param parent	 */	public void setHbmParent(SysOrgElement parent) {		this.hbmParent = parent;	}	@Column(name = "FD_PHOTO_URL")	public String getFdPhotoUrl() {		return fdPhotoUrl;	}	public void setFdPhotoUrl(String fdPhotoUrl) {		this.fdPhotoUrl = fdPhotoUrl;	}	@Transient	@JsonIgnore	public String getPlainPassword() {		return plainPassword;	}	public void setPlainPassword(String plainPassword) {		this.plainPassword = plainPassword;	}	@Transient	@JsonIgnore	public List<UserRole> getUserRoles() {		return userRoles;	}	public void setUserRoles(List<UserRole> userRoles) {		this.userRoles = userRoles;	}	/**	 * 部门名称	 * 	 * @return	 */	@Transient	public String getDeptName() {		if (hbmParent != null) {			return hbmParent.getFdName();		}		return deptName;	}	/**	 * 部门名称	 * 	 * @param deptName	 */	public void setDeptName(String deptName) {		this.deptName = deptName;	}	@Transient	@JsonIgnore	public List<String> getRoleList() {		// return ImmutableList.copyOf(StringUtils.split("user", ","));		List<String> list = new ArrayList<String>();		if (!CollectionUtils.isEmpty(userRoles)) {			for (UserRole userRole : userRoles) {				list.add(userRole.getRoleEnum().getKey());			}		} else {			list.add(RoleEnum.default_role.getKey());		}		return list;	}	/**	 * 姓名	 * 	 * @return	 */	@Transient	public String getRealName() {		return notifyEntity.getRealName();	}	@Transient	public String getFdEmail() {		return notifyEntity.getFdEmail();	}	@Transient	public String getFdMobileNo() {		return notifyEntity.getFdMobileNo();	}	private String deptId;	@Transient	public String getDeptId() {		return deptId;	}	public void setDeptId(String deptId) {		this.deptId = deptId;	}	/**	 * 获取人员头像	 * 	 * @return	 */	@Transient	public String getPoto() {		String photo = null;		if ("0".equals(getFdIsEmp())) {// 未入职员工			photo = getFdPhotoUrl();			if (StringUtils.isBlank(photo)) {				return ComUtils.getDefaultPoto();			} else if (photo.contains("http")) {				if (!ComUtils.isConnect(photo)) {					return ComUtils.getDefaultPoto();				}			}		} else {			// 正式员工			photo = ComUtils.getURLByLoginName(getLoginName());			if (StringUtils.isBlank(photo)) {				return ComUtils.getDefaultPoto();			} else if (!ComUtils.isConnect(photo)) {				return ComUtils.getDefaultPoto();			}		}		return photo;	}}