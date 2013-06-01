package ruyees.otp.model.base;

import java.util.List;

/**
 * 考题选项
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:09:59
 */
public class ExamOption {

	/**
	 * 对应答题内容
	 */
	private List<String> values;

	/**
	 * 对应答题内容
	 * 
	 * @return
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * 对应答题内
	 * 
	 * @param values
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}

	/**
	 * 对应BamExam的fdId
	 */
	private String fdId;

	/**
	 * 对应BamExam的fdId
	 * 
	 * @return
	 */
	public String getFdId() {
		return fdId;
	}

	/**
	 * 对应BamExam的fdId
	 * 
	 * @param fdId
	 */
	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

}
