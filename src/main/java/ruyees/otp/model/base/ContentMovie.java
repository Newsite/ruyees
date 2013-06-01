package ruyees.otp.model.base;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.util.CollectionUtils;

import ruyees.otp.model.core.CoreContentMovie;
import ruyees.otp.model.template.TemplateContent;

/**
 * 文档、视频
 * @author Zaric
 * @date  2013-6-1 上午1:07:46
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_OTP_CONTENTMOVIE")
@PrimaryKeyJoinColumn(name = "contentMovieId")
public class ContentMovie extends CoreContentMovie implements Comparable<ContentMovie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 域模型
	 */
	public static final String MODEL_NAME = "ruyees.otp.model.base.ContentMovie";

	/**
	 * 对应步骤实例
	 */
	private Set<TemplateContent> templateContents;

	/**
	 * 对应流程步骤
	 * 
	 * @return
	 */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "contentMovies")
	public Set<TemplateContent> getTemplateContents() {
		if (templateContents == null) {
			templateContents = new TreeSet<TemplateContent>();
		}
		return templateContents;
	}

	/**
	 * 对应流程步骤
	 * 
	 * @param templateContents
	 */
	public void setTemplateContents(Set<TemplateContent> templateContents) {
		this.templateContents = templateContents;
	}

	/**
	 * 对应文档存储
	 */
	private AttMain attMain;

	@Transient
	public AttMain getAttMain() {
		if (!CollectionUtils.isEmpty(attMains)) {
			return attMains.get(0);
		}
		return attMain;
	}

	/**
	 * 对应文档存储
	 */
	private List<AttMain> attMains;

	@OneToMany(mappedBy = "contentMovie", orphanRemoval = true)
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	@OrderBy("fdIndex asc")
	public List<AttMain> getAttMains() {
		return attMains;
	}

	public void setAttMains(List<AttMain> attMains) {
		this.attMains = attMains;
	}

	@Override
	public int hashCode() {
		final int prime = 11;
		int result = 1;
		result = prime * result + getFdId().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!getClass().equals(object.getClass())) {
			return false;
		}
		final ContentMovie other = (ContentMovie) object;

		if (!this.fdId.equals(other.fdId)) {
			return false;
		}
		return true;
	}

	public int compareTo(ContentMovie o) {
		return this.getFdId().compareTo(o.getFdId());
	}

	/**
	 * 文档编辑时内容超过80页就不用编辑每一个内容
	 * @return
	 */
	@Transient
	public Boolean getIsLimit(){
		if(!CollectionUtils.isEmpty(attMains)){
			if(attMains.size()<=80){
				return true;
			}
		}
		return false;
	}
}