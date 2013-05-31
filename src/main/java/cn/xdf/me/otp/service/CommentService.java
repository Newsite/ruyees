package cn.xdf.me.otp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.page.Pagination;
import cn.xdf.me.otp.common.utils.ComUtils;
import cn.xdf.me.otp.model.base.ContentMovie;
import cn.xdf.me.otp.model.flow.BamProject;
import cn.xdf.me.otp.model.flow.BamSeeCw;
import cn.xdf.me.otp.model.flow.Comment;
import cn.xdf.me.otp.model.sys.SysOrgPerson;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;
import cn.xdf.me.otp.service.base.ContentMovieService;
import cn.xdf.me.otp.service.flow.BamSeeCwService;

@Service
@Transactional(readOnly = true)
public class CommentService extends BaseService {

	/*
	 * SELECT * FROM IXDF_OTP_CONTENTMOVIE A,IXDF_OTP_CORE_CONTENTMOVIE B WHERE
	 * A.contentMovieId = B.FDID AND B.FDNAME='雅思阅读强化课程第06课';
	 */

	/**
	 * 保存评论
	 * 
	 * @param user
	 *            用户
	 * @param cwId
	 *            流程资源id
	 * @param content
	 *            评论内容
	 */
	@Transactional(readOnly = false)
	public void saveComment(ShiroUser user, String cwId, String content) {
		BamSeeCw bamSeeCw = bamSeeCwService.get(cwId);
		BamProject flow = bamSeeCw.getPhase().getStage().getProject();
		String fdName = bamSeeCw.getFdName();
		SysOrgPerson person = accountService.findById(user.getId());
		ContentMovie cm = contentMovieService.findUniqueByProperty("fdName",
				fdName);

		Comment comment = new Comment();
		comment.setUserId(person.getFdId());
		comment.setUserName(person.getRealName());
		comment.setDepartName(person.getDeptName());
		comment.setPoto(person.getPoto());
		comment.setCreatedtime(ComUtils.formatDate(new Date(), 2));
		comment.setConentMovieId(cm.getFdId());
		comment.setConentMovieName(cm.getFdName());
		comment.setFdContent(content);
		comment.setFlowId(flow.getFdId());
		comment.setFlowName(flow.getName());
		save(comment);
	}

	public Pagination getPage(String cwId, int pageNo) {
		BamSeeCw bamSeeCw = bamSeeCwService.get(cwId);
		String fdName = bamSeeCw.getFdName();
		Finder finder = Finder
				.create("from Comment where conentMovieName=:fdName");
		finder.setParam("fdName", fdName);
		return getPage(finder, pageNo);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getList(String cwId) {
		BamSeeCw bamSeeCw = bamSeeCwService.get(cwId);
		String fdName = bamSeeCw.getFdName();
		Finder finder = Finder
				.create("from Comment where conentMovieName=:fdName order by createdtime desc");
		finder.setParam("fdName", fdName);
		return find(finder);
	}

	public Pagination getList(String cwId, int pageNo) {
		BamSeeCw bamSeeCw = bamSeeCwService.get(cwId);
		String fdName = bamSeeCw.getFdName();
		Finder finder = Finder
				.create("from Comment where conentMovieName=:fdName order by createdtime desc");
		finder.setParam("fdName", fdName);
		return getPage(finder, pageNo, 2);
	}

	@Autowired
	private BamSeeCwService bamSeeCwService;

	@Autowired
	private ContentMovieService contentMovieService;

	@Autowired
	private AccountService accountService;

	@SuppressWarnings("unchecked")
	@Override
	public Class<Comment> getEntityClass() {
		return Comment.class;
	}

}
