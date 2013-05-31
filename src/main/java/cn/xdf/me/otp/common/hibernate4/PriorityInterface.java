package cn.xdf.me.otp.common.hibernate4;

/**
 * 实体按优先级排序类的接口。用于hibernate的sort排序
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:45:18
 */
public interface PriorityInterface {
	
	/**
	 * 获得优先级
	 * 
	 * @return
	 */
	public Number getPriority();

	/**
	 * 获得ID
	 * 
	 * @return
	 */
	public Number getId();
}