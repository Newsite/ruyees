package ruyees.otp.model.flow;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;

/**
 * 资源评论
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:18:18
 */
@Entity
@Table(name = "IXDF_OTP_COMMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 评论人id
	 */
	private String userId;
	/**
	 * 评论人姓名
	 */
	private String userName;

	/**
	 * 人员部门
	 */
	private String departName;

	/**
	 * 评论人头像
	 */
	private String poto;
	/**
	 * 评论时间
	 */
	private String createdtime;
	/**
	 * 文档id
	 */
	private String conentMovieId;
	/**
	 * 文档名称
	 */
	private String conentMovieName;
	/**
	 * 评论内容
	 */
	private String fdContent;

	/**
	 * 对应流程id
	 */
	private String flowId;

	/**
	 * 对应流程名称
	 */
	private String flowName;

	/**
	 * 评论人id
	 * 
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 评论人id
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 评论人姓名
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 评论人姓名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 人员部门
	 * 
	 * @return
	 */
	public String getDepartName() {
		return departName;
	}

	/**
	 * 人员部门
	 * 
	 * @param departName
	 */
	public void setDepartName(String departName) {
		this.departName = departName;
	}

	/**
	 * 评论人头像
	 * 
	 * @return
	 */
	public String getPoto() {
		return poto;
	}

	/**
	 * 评论人头像
	 * 
	 * @param poto
	 */
	public void setPoto(String poto) {
		this.poto = poto;
	}

	/**
	 * 评论时间
	 * 
	 * @return
	 */
	public String getCreatedtime() {
		return createdtime;
	}

	/**
	 * 评论时间
	 * 
	 * @param createdtime
	 */
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	/**
	 * 资源id
	 * 
	 * @return
	 */
	public String getConentMovieId() {
		return conentMovieId;
	}

	/**
	 * 资源id
	 * 
	 * @param conentMovieId
	 */
	public void setConentMovieId(String conentMovieId) {
		this.conentMovieId = conentMovieId;
	}

	/**
	 * 资源名称
	 * 
	 * @return
	 */
	public String getConentMovieName() {
		return conentMovieName;
	}

	/**
	 * 资源名称
	 * 
	 * @param conentMovieName
	 */
	public void setConentMovieName(String conentMovieName) {
		this.conentMovieName = conentMovieName;
	}

	/**
	 * 评论内容
	 * 
	 * @return
	 */
	public String getFdContent() {
		return fdContent;
	}

	/**
	 * 评论内容
	 * 
	 * @param fdContent
	 */
	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	/**
	 * 流程id
	 * 
	 * @return
	 */
	public String getFlowId() {
		return flowId;
	}

	/**
	 * 流程id
	 * 
	 * @param flowId
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	/**
	 * 流程名称
	 * 
	 * @return
	 */
	public String getFlowName() {
		return flowName;
	}

	/**
	 * 流程名称
	 * 
	 * @param flowName
	 */
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

}
