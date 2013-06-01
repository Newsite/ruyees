package ruyees.otp.model.core;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import ruyees.otp.model.IdEntity;

@Entity
@Table(name = "IXDF_OTP_CORE_COURSEWARE")
@Inheritance(strategy = InheritanceType.JOINED )
public class CoreCourseware extends IdEntity{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 课件要求
     */
    private String couReq;

    /**
     * 需要的资料
     */
    private String couNeedInfo;
    
    /**
     * 课件要求
     *
     * @return
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    public String getCouReq() {
        return couReq;
    }

    /**
     * 课件要求
     *
     * @param couReq
     */
    public void setCouReq(String couReq) {
        this.couReq = couReq;
    }

    /**
     * 需要的资料
     *
     * @return
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    public String getCouNeedInfo() {
        return couNeedInfo;
    }

    /**
     * 需要的资料
     *
     * @param couNeedInfo
     */
    public void setCouNeedInfo(String couNeedInfo) {
        this.couNeedInfo = couNeedInfo;
    }

}
