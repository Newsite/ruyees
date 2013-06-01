package ruyees.otp.common.upload;

/**
 * 文件存储类型
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:52:50
 */
public enum StoreType {
	
	/**
	 * 返回系统路径
	 */
	SYSTEM {

		@Override
		public int getValue() {
			return 2;
		}

		@Override
		public String getName() {
			return "System";
		}

	},
	/**
	 * 返回项目路径
	 */
	FOLDER {
		@Override
		public int getValue() {
			return 1;
		}

		@Override
		public String getName() {
			return "Folder";
		}
	};

	public abstract int getValue();

	public abstract String getName();
}