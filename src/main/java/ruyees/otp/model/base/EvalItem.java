package ruyees.otp.model.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;
import ruyees.otp.model.template.TemplateContent;

/**
 * 批课老师评分分项配置
 * @author Zaric
 * @date  2013-6-1 上午1:09:03
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_OTP_EVAL_ITEM")
public class EvalItem extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 对应步骤
	 */
	private TemplateContent templateContent;

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * 顺序号
	 */
	private int fdIndex;

	/**
	 * 分项说明
	 */
	private String fdExplain;

	/**
	 * 标准分
	 */
	private int fdValue;

	/**
	 * 对应步骤
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdTemplateContentId")
	public TemplateContent getTemplateContent() {
		return templateContent;
	}

	/**
	 * 对应步骤
	 * 
	 * @param templateContent
	 */
	public void setTemplateContent(TemplateContent templateContent) {
		this.templateContent = templateContent;
	}

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

	@Column(length = 500)
	public String getFdExplain() {
		return fdExplain;
	}

	public void setFdExplain(String fdExplain) {
		this.fdExplain = fdExplain;
	}

	public int getFdValue() {
		return fdValue;
	}

	public void setFdValue(int fdValue) {
		this.fdValue = fdValue;
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
		final EvalItem other = (EvalItem) object;

		if (!this.fdId.equals(other.fdId)) {
			return false;
		}
		return true;
	}

}
