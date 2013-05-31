package cn.xdf.me.otp.common.utils;

import java.util.Date;

/**
 * 日期提供者，使用它而不是直接取得系统时间，将方便测试。
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:54:10
 */
public interface DateProvider {

	Date getDate();

	public static final DateProvider DEFAULT = new CurrentDateProvider();

	public static class CurrentDateProvider implements DateProvider {

		public Date getDate() {
			return new Date();
		}
	}

	public static class ConfigurableDateProvider implements DateProvider {

		private final Date date;

		public ConfigurableDateProvider(Date date) {
			this.date = date;
		}

		public Date getDate() {
			return date;
		}
	}

}