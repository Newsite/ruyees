package ruyees.otp.action.view.model;

import ruyees.otp.model.flow.BamExamCategory;

/**
 * 页面考试提示信息
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:39:31
 */
public class VExam {
	
	/**
	 * 正确的题目数量
	 */
	private int right;
	/**
	 * 错误的题目数量
	 */
	private int error;
	/**
	 * 是否通过
	 */
	private boolean throng;

	/**
	 * 下一个Id
	 */
	private BamExamCategory next;

	/**
	 * 步骤是否通过
	 */
	private boolean phaseThrong;

	public boolean isPhaseThrong() {
		return phaseThrong;
	}

	public void setPhaseThrong(boolean phaseThrong) {
		this.phaseThrong = phaseThrong;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public boolean isThrong() {
		return throng;
	}

	public void setThrong(boolean throng) {
		this.throng = throng;
	}

	public BamExamCategory getNext() {
		return next;
	}

	public void setNext(BamExamCategory next) {
		this.next = next;
	}

}
