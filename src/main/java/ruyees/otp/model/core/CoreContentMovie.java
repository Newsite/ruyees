package ruyees.otp.model.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_OTP_CORE_CONTENTMOVIE")
@Inheritance(strategy = InheritanceType.JOINED)
public class CoreContentMovie extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fdName;
	private String fdDescribe;
	private String fdCreatorId;
	private Date fdCreateTime;

	/**
	 * 路径的格式为: /resource/www/
	 */
	private String backgroundUrl;

	/**
	 * 文档类型 1:文档 2:视频
	 */
	private int fileType;

	public CoreContentMovie() {
	}

	public CoreContentMovie(String fdId) {
		this.fdId = fdId;
	}

	/**
	 * 对应文档存储
	 */
	/*
	 * private AttMain attMain;
	 * 
	 * @Transient public AttMain getAttMain() {
	 * if(!CollectionUtils.isEmpty(attMains)){ return attMains.get(0); } return
	 * attMain; }
	 */

	/**
	 * 对应文档存储
	 */
	/*
	 * private List<AttMain> attMains;
	 * 
	 * @OneToMany(mappedBy = "contentMovie", orphanRemoval = true)
	 * 
	 * @Cascade({ org.hibernate.annotations.CascadeType.ALL }) public
	 * List<AttMain> getAttMains() { return attMains; }
	 * 
	 * public void setAttMains(List<AttMain> attMains) { this.attMains =
	 * attMains; }
	 */

	/**
	 * 
	 * 名称
	 */
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 
	 * 创建人
	 */
	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	/**
	 * 
	 * 创建时间
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 
	 * 描述
	 */
	@Column(length = 2000)
	public String getFdDescribe() {
		return fdDescribe;
	}

	public void setFdDescribe(String fdDescribe) {
		this.fdDescribe = fdDescribe;
	}

	/**
	 * 对应视频和文档的背景图片的url
	 * 
	 * @return
	 */
	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	/**
	 * 对应视频和文档的背景图片的url
	 * 
	 * @param backgroundUrl
	 */
	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	@Transient
	public String getTypeName() {
		if (fileType == 1)
			return "文档";
		return "视频";
	}
}
