package ruyees.otp.model.core;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import ruyees.otp.model.IdEntity;

@Entity
@Table(name = "IXDF_OTP_CORE_COURSEWARE_ITEM")
@Inheritance(strategy = InheritanceType.JOINED)
public class CoreCoursewareItem extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 课件名称
	 */
	private String name;

	/**
	 * 课件名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 课件名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
