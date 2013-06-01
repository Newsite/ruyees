package ruyees.otp.model.base;import java.util.List;import javax.persistence.CascadeType;import javax.persistence.Entity;import javax.persistence.FetchType;import javax.persistence.OneToMany;import javax.persistence.OneToOne;import javax.persistence.PrimaryKeyJoinColumn;import javax.persistence.Table;import org.hibernate.annotations.Cache;import org.hibernate.annotations.CacheConcurrencyStrategy;import ruyees.otp.model.core.CoreOperPackage;import ruyees.otp.model.template.TemplateContent;/** * 作业包 *  * @author Zaric * @date 2013-6-1 上午1:10:56 */@Entity@Table(name = "IXDF_OTP_OPER_PACKAGE")@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)@PrimaryKeyJoinColumn(name = "managerId")public class OperPackage extends CoreOperPackage implements		Comparable<OperPackage> {	/**	 * 	 */	private static final long serialVersionUID = 1L;	/**	 * 对应作业	 */	private List<Operation> operations;	private TemplateContent templateContent;	public OperPackage() {	}	public OperPackage(String fdId) {		this.fdId = fdId;	}	@OneToOne(mappedBy = "operPackage")	public TemplateContent getTemplateContent() {		return templateContent;	}	public void setTemplateContent(TemplateContent templateContent) {		this.templateContent = templateContent;	}	/**	 * 对应作业	 * 	 * @return	 */	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,			CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "operPackage")	public List<Operation> getOperations() {		return operations;	}	public void setOperations(List<Operation> operations) {		this.operations = operations;	}	@Override	public int hashCode() {		final int prime = 11;		int result = 1;		result = prime * result + getFdId().hashCode();		return result;	}	@Override	public boolean equals(Object object) {		if (object == null) {			return false;		}		if (!getClass().equals(object.getClass())) {			return false;		}		OperPackage other = (OperPackage) object;		if (!this.fdId.equals(other.fdId)) {			return false;		}		return true;	}	public int compareTo(OperPackage o) {		return this.getFdId().compareTo(o.getFdId());	}}