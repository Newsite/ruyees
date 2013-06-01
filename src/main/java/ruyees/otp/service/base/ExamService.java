package ruyees.otp.service.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Finder.SearchType;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.model.base.Exam;
import ruyees.otp.model.base.ExamCategory;
import ruyees.otp.service.BaseService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;

@Service
@Transactional(readOnly = true)
public class ExamService extends BaseService {

	@Autowired
	private ExamCategoryService examCategoryService;

	public Pagination getPageByDynamic(Map<String, Object> map, Integer pageNo) {
		return getBaseDao().findByNamedPage("exam-page", map, Exam.class,
				pageNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Exam> getEntityClass() {
		return Exam.class;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public Boolean isHas(Exam exam, String method, String fdCategoryId) {
		Boolean isHas = false;
		if ("add".equals(method)) {
			String fdSubject = exam.getFdSubject();
			Finder finder = Finder.create("from Exam e where 1=1");
			finder.search("e.examCategory.fdId", fdCategoryId, SearchType.EQ);
			finder.search("e.fdSubject", fdSubject, SearchType.EQ);
			List<Exam> exams = find(finder);
			isHas = exams.size() > 0;
		}
		return isHas;
	}

	@Transactional(readOnly = false)
	public Exam saveExam(Exam exam, String fdCategoryId) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		exam.setFdCreatorId(uId);
		exam.setFdCreateTime(new Date());
		exam.setFdStandardScore(0.0);
		// 维护试卷关联
		exam.setExamCategory((ExamCategory) examCategoryService
				.get(fdCategoryId));
		return super.save(exam);
	}

}
