package ruyees.otp.model.demo;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "DEMO_LEADER")
@PrimaryKeyJoinColumn(name = "leaderId")
public class Leader extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
