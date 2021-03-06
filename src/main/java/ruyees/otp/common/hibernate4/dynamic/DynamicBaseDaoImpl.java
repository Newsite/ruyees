package ruyees.otp.common.hibernate4.dynamic;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.HibernateSimpleDao;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.common.page.SimplePage;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Hibernate动态Sql
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:47:22
 */
public abstract class DynamicBaseDaoImpl extends HibernateSimpleDao implements InitializingBean, DynamicBaseDao {

	private static final Logger LOGER = LoggerFactory.getLogger(DynamicBaseDaoImpl.class);

	/**
	 * 模板缓存
	 */
	protected Map<String, StatementTemplate> templateCache;
	protected DynamicHibernateAssembleBuilder dynamicAssembleBuilder;

	@Autowired
	public void setDynamicStatementBuilder(
			DynamicHibernateAssembleBuilder dynamicAssembleBuilder) {
		this.dynamicAssembleBuilder = dynamicAssembleBuilder;
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	protected <X> List<X> findByHQL(final String hql, final Class<?> clazz,
			final Map<String, ?> values) {
		return createHQLQuery(hql, clazz, values).list();
	}

	/**
	 * 按SQL查询对象列表.
	 * 
	 * @param sql
	 *            SQL查询语句
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	protected <X> List<X> findBySQL(final String sql, Class<?> clazz,
			final Map<String, ?> values) {
		return createSQLQuery(sql, clazz, values).list();
	}

	/**
	 * 查询在xxx.dynamic.xml中配置的查询语句
	 * <p>
	 * 可查询sql或者hql占位符的语句,也可以直接写sql或者hql
	 * </p>
	 */
	public <X> List<X> findByNamedQuery(final String queryName,
			final Map<String, Object> parameters, Class<X> clazz) {
		StatementTemplate statementTemplate = templateCache.get(queryName);
		String statement = processTemplate(statementTemplate, parameters);
		// log.info(String.format("sql=%s", statement));
		// System.out.println("sql="+statement);
		if (statementTemplate.getType() == StatementTemplate.TYPE.HQL) {
			return this.findByHQL(statement, clazz, parameters);
		} else {
			return this.findBySQL(statement, clazz, parameters);
		}
	}

	public Pagination findByNamedPage(final String queryName,
			final Map<String, Object> parameters, Class<?> clazz, int pageNo) {
		StatementTemplate statementTemplate = templateCache.get(queryName);
		String statement = processTemplate(statementTemplate, parameters);

		StatementTemplate statementTemplate_pagecount = templateCache
				.get(queryName + "-count");

		// log.info(String.format("sql=%s", statement));
		Finder finder = Finder.create(statement);
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			if (StringUtils.contains(statement, ":" + entry.getKey())) {
				finder.setParam(entry.getKey(), entry.getValue());
			}
		}
		if (statementTemplate_pagecount != null) {
			String statement_pagecount = processTemplate(
					statementTemplate_pagecount, parameters);
			finder.setRowCountSql(statement_pagecount);
		}
		if (statementTemplate.getType() == StatementTemplate.TYPE.HQL) {
			return find(finder, pageNo, SimplePage.DEF_COUNT, clazz);
		} else {
			return findDynamPage(finder, pageNo, SimplePage.DEF_COUNT, clazz);
		}
	}

	/**
	 * 通过Finder获得分页数据
	 * 
	 * @param finder
	 *            Finder对象
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Pagination findDynamPage(Finder finder, int pageNo, int pageSize,
			Class<?> clazz) {
		int totalCount = countSQLQueryResult(finder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query = getSession().createSQLQuery(finder.getOrigHql());
		if (clazz.getName().equals(Map.class.getName())) {
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		} else {
			query.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		List list = query.list();
		p.setList(list);
		return p;
	}

	/**
	 * 获得Finder的记录总数
	 * 
	 * @param finder
	 * @return
	 */
	protected int countSQLQueryResult(Finder finder) {
		Query query = getSession().createSQLQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		Object obj = query.uniqueResult();
		if (obj == null)
			return 0;
		return NumberUtils.toInt(obj.toString(), 0);
	}

	/**
	 * 更新在xxx.dynamic.xml中配置的更新语句
	 * 
	 * @param queryName
	 *            更新名称
	 * @param parameters
	 *            参数
	 */
	public int updateByNamedQuery(final String queryName,
			final Map<String, ?> parameters) {
		StatementTemplate statementTemplate = templateCache.get(queryName);
		String statement = processTemplate(statementTemplate, parameters);
		// log.info(String.format("sql=%s", statement));
		if (statementTemplate.getType() == StatementTemplate.TYPE.HQL) {
			return batchExecuteHQL(statement, parameters);
		} else {
			return batchExecuteSQL(statement, parameters);
		}
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	protected int batchExecuteHQL(final String hql, final Map<String, ?> values) {
		return createHQLQuery(hql, null, values).executeUpdate();
	}

	/**
	 * 执行SQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	protected int batchExecuteSQL(final String sql, final Map<String, ?> values) {
		return createSQLQuery(sql, null, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	protected Query createUpdateHQLQuery(final String queryString,
			final Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	protected Query createSelectHQLQuery(final String queryString,
			Class<?> clazz) {
		Query query = getSession().createQuery(queryString);
		if (clazz.getName().equals(Map.class.getName())) {
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		return query;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param sqlQueryString
	 *            sql语句
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	protected Query createUpdateSQLQuery(final String sqlQueryString,
			final Object... values) {
		Query query = getSession().createSQLQuery(sqlQueryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	protected Query createSelectSqlQuery(final String sqlQueryString,
			final Class<?> clazz) {
		Query query = getSession().createSQLQuery(sqlQueryString);
		if (clazz.getName().equals(Map.class.getName())) {
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		} else {
			query.setResultTransformer(Transformers.aliasToBean(clazz));
		}

		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	protected Query createHQLQuery(final String queryString, Class<?> clazz,
			final Map<String, ?> values) {
		Query query = getSession().createQuery(queryString);
		if (clazz != null) {
			if (clazz.getName().equals(Map.class.getName())) {
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} else {
				query.setResultTransformer(Transformers.aliasToBean(clazz));
			}
		}
		if (values != null) {
			for (Map.Entry<String, ?> entry : values.entrySet()) {
				if (StringUtils.contains(queryString,
						String.format(":%s", entry.getKey()))) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		return query;
	}

	/**
	 * 根据查询SQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param queryString
	 *            SQL语句
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	protected Query createSQLQuery(final String queryString, Class<?> clazz,
			final Map<String, ?> values) {
		Query query = getSession().createSQLQuery(queryString);
		if (clazz != null) {
			if (clazz.getName().equals(Map.class.getName())) {
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} else {
				query.setResultTransformer(Transformers.aliasToBean(clazz));
			}
		}
		if (values != null) {
			for (Map.Entry<String, ?> entry : values.entrySet()) {
				if (StringUtils.contains(queryString,
						String.format(":%s", entry.getKey()))) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		return query;
	}

	public void afterPropertiesSet() throws Exception {
		templateCache = new ConcurrentHashMap<String, StatementTemplate>();

		dynamicAssembleBuilder.init();
		Map<String, String> namedHQLQueries = dynamicAssembleBuilder
				.getNamedHQLQueries();
		Map<String, String> namedSQLQueries = dynamicAssembleBuilder
				.getNamedSQLQueries();
		Configuration configuration = new Configuration();
		configuration.setNumberFormat("#");
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		for (Entry<String, String> entry : namedHQLQueries.entrySet()) {
			stringLoader.putTemplate(entry.getKey(), entry.getValue());
			templateCache
					.put(entry.getKey(),
							new StatementTemplate(StatementTemplate.TYPE.HQL,
									new Template(entry.getKey(),
											new StringReader(entry.getValue()),
											configuration)));
		}
		for (Entry<String, String> entry : namedSQLQueries.entrySet()) {
			stringLoader.putTemplate(entry.getKey(), entry.getValue());
			templateCache
					.put(entry.getKey(),
							new StatementTemplate(StatementTemplate.TYPE.SQL,
									new Template(entry.getKey(),
											new StringReader(entry.getValue()),
											configuration)));
		}
		configuration.setTemplateLoader(stringLoader);
	}

	protected String processTemplate(StatementTemplate statementTemplate,
			Map<String, ?> parameters) {
		StringWriter stringWriter = new StringWriter();
		try {
			statementTemplate.getTemplate().process(parameters, stringWriter);
		} catch (Exception e) {
			LOGER.error("处理DAO查询参数模板时发生错误：{}", e.toString());
			throw new RuntimeException(e);
		}
		return stringWriter.toString();
	}
}