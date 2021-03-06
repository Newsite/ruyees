package ruyees.otp.model.sys;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;

/**
 * 用户对应的项目
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:22:32
 */
@Entity
@Table(name = "SYS_USER_DICTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserDiction extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SysOrgPerson sysOrgPerson;

	/**
	 * 对应项目的ID
	 */
	private String dictionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = true)
	public SysOrgPerson getSysOrgPerson() {
		return sysOrgPerson;
	}

	public void setSysOrgPerson(SysOrgPerson sysOrgPerson) {
		this.sysOrgPerson = sysOrgPerson;
	}

	/**
	 * 对应项目的ID
	 * 
	 * @return
	 */
	public String getDictionId() {
		return dictionId;
	}

	/**
	 * 对应项目的ID
	 * 
	 * @param dictionId
	 */
	public void setDictionId(String dictionId) {
		this.dictionId = dictionId;
	}

}
