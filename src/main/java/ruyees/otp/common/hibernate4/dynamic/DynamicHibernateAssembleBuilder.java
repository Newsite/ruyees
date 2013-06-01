package ruyees.otp.common.hibernate4.dynamic;

import java.io.IOException;
import java.util.Map;

/**
 * 动态sql/hql语句组装器
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:47:45
 */
public interface DynamicHibernateAssembleBuilder {

	/**
	 * hql语句map
	 * 
	 * @return
	 */
	public Map<String, String> getNamedHQLQueries();

	/**
	 * sql语句map
	 * 
	 * @return
	 */
	public Map<String, String> getNamedSQLQueries();

	/**
	 * 初始化
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException;
}
