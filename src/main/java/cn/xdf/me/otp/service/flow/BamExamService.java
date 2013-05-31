package cn.xdf.me.otp.service.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.xdf.me.otp.action.view.model.VExam;
import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.hibernate4.Updater;
import cn.xdf.me.otp.common.hibernate4.Updater.UpdateMode;
import cn.xdf.me.otp.model.base.ExamOption;
import cn.xdf.me.otp.model.flow.BamExam;
import cn.xdf.me.otp.model.flow.BamExamCategory;

@Service
@Transactional(readOnly = true)
public class BamExamService extends BamBaseService {

	@Autowired
	private BamProjectPhaseService bamProjectPhaseService;

	/**
	 * 保存答题内容 并返回下一道题内容
	 * 
	 * @param bamExamCategory
	 */
	@Transactional(readOnly = false)
	public VExam addExam(BamExamCategory bamExamCategory) {
		VExam vexam = new VExam();
		List<ExamOption> examOptions = bamExamCategory.getExamOptions();
		for (ExamOption option : examOptions) {
			BamExam bamExam = load(BamExam.class, option.getFdId());
			List<String> rightAnswers = bamExam.getAllRight();
			List<String> answers = option.getValues();
			if (rightAnswers.size() == answers.size()// 答案数相同
					&& rightAnswers.containsAll(answers)) {// 答案一致
				vexam.setRight(vexam.getRight() + 1);// 正确
			} else {
				vexam.setError(vexam.getError() + 1);// 不正确
			}
		}
		boolean throng = (vexam.getError() == 0);
		bamExamCategory.setThrough(throng);
		if (throng) {
			vexam.setThrong(throng);// 设置考试通过
			BamExamCategory b = bamProjectPhaseService.load(
					BamExamCategory.class, bamExamCategory.getFdId());
			setThrongBamExamCategory(bamExamCategory);

			if (b.getPhase().getStage().getFdIndex() == 1) {// 如果是第一关，考题通过后更新步骤状态
				setThrongBamExamCategory(bamExamCategory);
			} else {
				// 如果是第三关，不进行步骤更新
				Updater<BamExamCategory> updater = new Updater<BamExamCategory>(
						bamExamCategory);
				updater.setUpdateMode(UpdateMode.MIN);
				updater.include("through");
				getBaseDao().updateByUpdater(updater);
			}

			BamExamCategory cate = load(BamExamCategory.class,
					bamExamCategory.getFdId());
			BamExamCategory next = findNextExam(cate);
			// 如果没有下一个试卷,则表示全部通过
			if (next == null) {
			} else {
				// 设置下一个考题
				vexam.setNext(next);
			}

			vexam.setPhaseThrong(cate.getPhase().getThrough());// 步骤通过状态
		}
		return vexam;
	}

	/**
	 * 更新流程考试的状态
	 * 
	 * @param bamExamCategory
	 */
	private void setThrongBamExamCategory(BamExamCategory bamExamCategory) {
		Updater<BamExamCategory> updater = new Updater<BamExamCategory>(
				bamExamCategory);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("through");
		updateByUpdater(updater);
	}

	/**
	 * 获取下一个未通过的试卷
	 * 
	 * @param categroyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private BamExamCategory findNextExam(BamExamCategory cate) {

		Finder finder = Finder
				.create("select bam from BamExamCategory bam left join bam.phase phase");
		finder.append("where phase.fdId=:fdid and bam.through=:through")
				.setParam("fdid", cate.getPhase().getFdId())
				.setParam("through", false);
		List<BamExamCategory> categorys = find(finder);
		if (CollectionUtils.isEmpty(categorys))
			return null;
		return categorys.get(0);
	}

	@Override
	public <T> Class<T> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
