package ruyees.otp.action.view.model;

import ruyees.otp.common.page.Pagination;

/**
 * 资源评论
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:40:28
 */
public class VPagination extends Pagination {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VPagination() {

	}

	public VPagination(Pagination pagination) {
		super(pagination.getPageNo(), pagination.getPageSize(), pagination
				.getTotalCount(), pagination.getList());
	}

	private static final int DEFAULTPAGINATIONSIZE = 5;

	/**
	 * @return
	 */
	public String getPageStr() {
		StringBuilder page = new StringBuilder();
		int current = this.getPageNo();
		int begin = Math.max(1, current - DEFAULTPAGINATIONSIZE / 2);
		int end = Math.min(begin + (DEFAULTPAGINATIONSIZE - 1),
				this.getTotalPage());
		if (this.getTotalPage() > 1) {
			page.append("<div class=\"pagination pagination-centered pagination-small\">");
			page.append("<ul>");
			if (this.hasPreviousPage()) {
				page.append("<li><a href=\"javascript:gopage(" + (current - 1)
						+ ")\">«</a></li>");
			} else {
				page.append("<li class=\"disabled\"><a href=\"#\">«</a></li>");
			}

			for (int i = begin; i <= end; i++) {
				if (i == current) {
					page.append("<li><a href=\"javascript:gopage(" + (i)
							+ ")\">" + i + "</a></li>");
				} else {
					page.append("<li class=\"disabled\"><a href=\"javascript:gopage("
							+ i + ")\">" + i + "</a></li>");
				}
			}

			if (this.hasNextPage()) {
				page.append("<li><a href=\"javascript:gopage(" + (current + 1)
						+ ")\">»</a></li>");
			} else {
				page.append("<li class=\"disabled\"><a href=\"#\">&gt;</a></li>");
			}

			page.append("</ul>");
			page.append("</div>");
		}

		return page.toString();
	}
}
