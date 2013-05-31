package cn.xdf.me.otp.model.template;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.xdf.me.otp.model.IdEntity;

/**
 * 活动模板(阶段)
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:24:00
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_OTP_TEMPLATE_ITEM")
public class TemplateItem extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 阶段名称
	 */
	private String fdName;

	private ProTemplate template;

	/**
	 * 序号
	 */
	private int fdIndex;

	/**
	 * 计划天数
	 */
	private int fdDayCount;

	/**
	 * 延时天数
	 */
	private int fdDelayCount;

	/**
	 * 备注说明
	 */
	private String fdRemarke;

	/**
	 * 阶段下的步骤
	 */
	private List<TemplateContent> templateContents;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public int getFdIndex() {
		return fdIndex;
	}

	public void setFdIndex(int fdIndex) {
		this.fdIndex = fdIndex;
	}

	public int getFdDayCount() {
		return fdDayCount;
	}

	public void setFdDayCount(int fdDayCount) {
		this.fdDayCount = fdDayCount;
	}

	public int getFdDelayCount() {
		return fdDelayCount;
	}

	public void setFdDelayCount(int fdDelayCount) {
		this.fdDelayCount = fdDelayCount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "templateId")
	public ProTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ProTemplate template) {
		this.template = template;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdRemarke() {
		return fdRemarke;
	}

	public void setFdRemarke(String fdRemarke) {
		this.fdRemarke = fdRemarke;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "item")
	@OrderBy("fdIndex asc")
	public List<TemplateContent> getTemplateContents() {
		return templateContents;
	}

	public void setTemplateContents(List<TemplateContent> templateContents) {
		this.templateContents = templateContents;
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
		TemplateItem other = (TemplateItem) object;

		if (!this.fdId.equals(other.fdId)) {
			return false;
		}

		return true;
	}

	@Transient
	public String getDescription() {
		if (fdIndex == 1) {
			return "业务学习<br />踏上我的成长路";
		} else if (fdIndex == 2) {
			return "学术准备<br />夯实我学术基础";
		} else if (fdIndex == 3) {
			return "标准化教案学习<br />标准备课不马虎";
		} else if (fdIndex == 4) {
			return "课件编写<br />我的课件我做主";
		} else if (fdIndex == 5) {
			return "批课<br />批课天天见进步";
		} else if (fdIndex == 6) {
			return "课件确认<br />三尺讲台见功夫";
		}
		return null;
	}

	/**
	 * 是否需要审核
	 */
	private boolean assessment;

	/**
	 * 是否需要审核
	 */
	public boolean getAssessment() {
		return assessment;
	}

	/**
	 * 是否需要审核
	 * 
	 * @param assessment
	 */
	public void setAssessment(boolean assessment) {
		this.assessment = assessment;
	}

	/**
	 * 默认评语
	 */
	private String defaultComment;

	/**
	 * 默认评语
	 */
	public String getDefaultComment() {
		return defaultComment;
	}

	/**
	 * 默认评语
	 * 
	 * @param defaultComment
	 */
	public void setDefaultComment(String defaultComment) {
		this.defaultComment = defaultComment;
	}
}
