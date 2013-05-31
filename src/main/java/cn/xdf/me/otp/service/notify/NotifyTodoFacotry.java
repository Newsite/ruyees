package cn.xdf.me.otp.service.notify;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.GenericCollectionTypeResolver;

import cn.xdf.me.otp.model.notify.NotifyEnum;
import cn.xdf.me.otp.model.notify.NotifyExcute;

/**
 * 
 * 代办工厂类
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:39:44
 */
public class NotifyTodoFacotry extends
		AbstractFactoryBean<Map<NotifyEnum, NotifyExcute>> {

	private Map<NotifyEnum, NotifyExcute> sourceMap;

	private Class<Map<NotifyEnum, NotifyExcute>> targetMapClass;

	public void setSourceMap(Map<NotifyEnum, NotifyExcute> sourceMap) {
		this.sourceMap = sourceMap;
	}

	/***
	 * Set the class to use for the target Map. Can be populated with a fully
	 * qualified class name when defined in a Spring application context.
	 * <p>
	 * Default is a linked HashMap, keeping the registration order.
	 * 
	 * @see java.util.LinkedHashMap
	 */
	public void setTargetMapClass(
			Class<Map<NotifyEnum, NotifyExcute>> targetMapClass) {
		if (targetMapClass == null) {
			throw new IllegalArgumentException(
					"'targetMapClass' must not be null");
		}
		if (!Map.class.isAssignableFrom(targetMapClass)) {
			throw new IllegalArgumentException(
					"'targetMapClass' must implement [java.util.Map]");
		}
		this.targetMapClass = targetMapClass;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<Map> getObjectType() {
		return Map.class;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map<NotifyEnum, NotifyExcute> createInstance() {
		if (this.sourceMap == null) {
			throw new IllegalArgumentException("'sourceMap' is required");
		}
		Map<NotifyEnum, NotifyExcute> result = null;
		if (this.targetMapClass != null) {
			result = (Map<NotifyEnum, NotifyExcute>) BeanUtils
					.instantiateClass(this.targetMapClass);
		} else {
			result = new LinkedHashMap<NotifyEnum, NotifyExcute>(
					this.sourceMap.size());
		}
		Class<String> keyType = null;
		Class<NotifyExcute> valueType = null;
		if (this.targetMapClass != null) {
			keyType = (Class<String>) GenericCollectionTypeResolver
					.getMapKeyType(this.targetMapClass);
			valueType = (Class<NotifyExcute>) GenericCollectionTypeResolver
					.getMapValueType(this.targetMapClass);
		}
		if (keyType != null || valueType != null) {
			TypeConverter converter = getBeanTypeConverter();
			for (Map.Entry entry : this.sourceMap.entrySet()) {
				String convertedKey = converter.convertIfNecessary(
						entry.getKey(), keyType);
				NotifyExcute convertedValue = converter.convertIfNecessary(
						entry.getValue(), valueType);
				result.put(NotifyEnum.valueOf(convertedKey), convertedValue);
			}
		} else {
			result.putAll(this.sourceMap);
		}
		return result;
	}

}
