package cn.xdf.me.otp.model;

import cn.xdf.me.otp.model.flow.BamProjectPhase;

/**
 * 流程资源项资源
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:06:16
 */
public interface BamProcess {

	public static final String THROGHT_FIELD = "through";

	public static final String PRI_FIELD = "fdId";

	public static final String PRI_THROUGH = "through";

	public String getFdId();

	public boolean getThrough();

	public void setThrough(boolean through);

	public BamProjectPhase getPhase();

}
