package cn.xdf.me.otp.common.web.springmvc;

/**
 * 绝对路径提供类
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:01:24
 */
public interface RealPathResolver {

	/**
	 * 获得绝对路径
	 * 
	 * @param path
	 * @return
	 * @see javax.servlet.ServletContext#getRealPath(String)
	 */
	public String get(String path);
}
