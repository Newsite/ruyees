package ruyees.otp.utils;

import java.util.ArrayList;
import java.util.List;

import ruyees.otp.model.base.Diction;
import ruyees.otp.model.flow.BamProject;
import ruyees.otp.model.template.ProTemplate;
import ruyees.otp.utils.model.FlowModel;

/**
 * 
 * 步骤工具类
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:35:13
 */
public class PhaseUtils {

	private static final String leftJoin = " left join fetch ";

	public static List<FlowModel> getFlowModels(Diction diction,
			List<BamProject> projects) {
		if (diction == null)
			return null;
		List<FlowModel> flowModels = new ArrayList<FlowModel>();
		List<Diction> dictons = diction.getChildrens();
		if (dictons == null)
			return null;
		FlowModel model = null;
		for (Diction d : dictons) {
			model = new FlowModel();
			model.setFdId(diction.getFdId() + d.getFdId());
			model.setFdName(diction.getFdName() + d.getFdName());
			flowModels.add(model);
		}
		for (FlowModel m : flowModels) {
			for (BamProject p : projects) {
				ProTemplate template = p.getTemplate();
				String templetIds = template.getCourse().getFdId()
						+ template.getItem().getFdId();
				if (templetIds.equals(m.getFdId())) {
					m.setFdId(p.getFdId());
					m.setEnableByUser(true);
				}
			}
		}
		return flowModels;
	}

	public static int fetchUrl(boolean enable, int index) {
		if (index == 2 && enable) {
			return 2;
		} else if (index == 4 && enable) {
			return 2;
		} else if (index == 5 && enable) {
			return 2;
		} else if (index == 6 && enable) {
			return 2;
		}
		return 1;
	}

	public static String fetchField(int stage, int step) {
		if (stage == 1 && step == 1) {
			return "cws";// 视频
		} else if (stage == 1 && step == 2) {
			return "examCategories";// 老师
		} else if (stage == 2 && step == 1) {
			return "bamPackage";// 作业包
		} else if (stage == 3 && step == 1) {
			return "cws";// 视频
		} else if (stage == 3 && step == 2) {
			return "examCategories";// 考试
		} else if (stage == 3 && step == 3) {
			return "cws";// 文档
		} else if (stage == 3 && step == 4) {
			return "cws";// 视频
		} else if (stage == 4) {
			return "coursewares";// 课件
		} else if (stage == 5) {// 批课打分
			return "grades";
		} else if (stage == 6) {
			return "coursewares";// 课件
		}
		return null;
	}

	/**
	 * 抓取每个步骤的资源项
	 * 
	 * @param stage
	 *            阶段
	 * @param step
	 *            步骤
	 * @return leftJoin+
	 */
	public static String fetchModel(String name, int stage, int step) {
		if (stage == 1 && step == 1) {
			return leftJoin + name + ".cws";// 视频
		} else if (stage == 1 && step == 2) {
			return leftJoin + name + ".examCategories";// 老师
		} else if (stage == 2 && step == 1) {
			return leftJoin + name + ".packages package";// 作业包
		} else if (stage == 3 && step == 1) {
			return leftJoin + name + ".cws";// 视频
		} else if (stage == 3 && step == 2) {
			return leftJoin + name + ".examCategories";// 考试
		} else if (stage == 3 && step == 3) {
			return leftJoin + name + ".cws";// 视频
		} else if (stage == 3 && step == 4) {
			return leftJoin + name + ".cws";// 视频
		} else if (stage == 4) {
			return leftJoin + name + ".coursewares";// 课件
		} else if (stage == 5) {// 批课打分
			return null;
		} else if (stage == 6) {
			return leftJoin + name + ".coursewares";// 课件
		}
		return null;
	}

	public static String getPhaseContext(int index) {
		if (index == 1) {
			return "<span style=\"font-size:14px;\">在本关卡中，你需要完成以下两个步骤：</span><br /><span style=\"font-size:14px;\"> a. 认真观看视频</span><br /><span style=\"font-size:14px;\"> b. 完成与视频内容相关的试题</span><br /><span style=\"font-size:14px;\"> 你所提交的答案必须完全正确。如果提交答案不正确，你可以选择继续答题或者再次学习视频。</span>";
		} else if (index == 2) {
			return "<span style=\"font-size:14px;\">在本关卡中，你要大量梳理真题，查阅大量资料，然后完成相应的作业。作业列表在屏幕的右侧，所有作业是按照难易程度从上倒下排布的，你需要循序渐进完成它们。每一份作业都有相应的要求、固定的名称和格式，你可以在“成果指标”板块下载作业模板。在完成作业后点击“上传”按钮备份到系统。在你点击“提交”按钮之前，指导教师不会看到你的作业，你可以重复上传覆盖之前的作业。确定“提交”之后，你将不能修改作业，指导教师将对你的作业进行评分。</span>";
		} else if (index == 3) {
			return "<span style=\"font-size:14px;\">在本关卡中，你要完成四个步骤：</span><br /><span style=\"font-size:14px;\">a. 学习教案解读视频。在这里，培训师会告诉你标准化教案是如何编写出来的，你该如何使用标准化教案；</span><br /><span style=\"font-size:14px;\">b. 参加在线测试。你需要在这个步骤完成关于标准化教案使用方法的小测试。若不能完全回答正确，系统会重新为你播放视频，看完视频后，你需要重新完成测试；</span><br /><span style=\"font-size:14px;\">c. 学习标准化教案。在这个步骤中，你将阅读到培训师编写的标准化教案PPT；</span><br /><span style=\"font-size:14px;\">d. 学习名师教学DEMO。在这里，你将看到培训师在课堂上是如何使用标准化教案的。</span>";
		} else if (index == 4) {
			return "<span style=\"font-size:14px;\">在本关卡中，你的主要任务是编写你自己的课件。每完成</span><span class=\"s1\" style=\"font-size:14px;\">1</span><span style=\"font-size:14px;\">次课的课件，请把右侧滚动条下拉到“课件管理”板块，点击“上传”按钮将课件备份到系统。在你点击“提交”按钮之前，指导教师不会看到你的课件，你可以重复上传覆盖之前的课件。确定“提交”之后，你将不能修改课件，指导教师将对你的课件进行审阅。</span>";
		} else if (index == 5) {
			return "<span style=\"font-size:14px;\">在本关卡中，你的指导教师将会对你进行一对一的批课。开始批课前，指导教师预约好批课时间后，你可以在本页查询到每一次的批课安排。每次批课过程中，指导教师会就专业功底、逻辑性、实用性、创新性、例子选取情况、感染力、语言表达和课程熟悉程度8个方面对进行评分，满分5分。每次批课结束后，你可以在本页查看到自己的得分和指导老师对你的建议。在所有批课结束后，系统会指引你为你的指导教师评分。</span>";
		} else if (index == 6) {
			return "<span style=\"font-size:14px;\">在本关卡中，你需要根据批课环节指导教师给你的反馈和建议修改你的课件并将其重新上传（操作同前点击“上传”按钮）。在你确认“提交”之前，可以重复上传覆盖之前课件，在你“提交”之后，指导教师会再次审阅你的课件。如果指导教师审核未通过，你需要再次修改课件并上传提交；如果指导教师审核通过，你将完成在线备课流程。</span>";
		}
		return null;
	}

	public static String getBuzhouContext(int index, int tage) {
		if (index == 1 && tage == 1) {
			return "<span style=\"font-size:14px;\">我们为你准备了多个视频，帮助你充分了解新东方和托福考试，也为你未来的课件编写提供了重要信息。现在把纸笔准备好，仔细观看并做好记录吧。</span>";
		} else if (index == 1 && tage == 2) {
			return "<span style=\"font-size:14px;\">现在认真完成测试吧。如果提交的答案不完全正确，你需要回到1.1“学习入门视频”步骤重新观看相应视频并重新进行测试哦。</span><br />";
		} else if (index == 2 && tage == 1) {
			return "<span style=\"font-size:14px;\">认真完成学术作业是新老师成长的基础。就像建楼房，地基挖得越深，楼房建得越高。仔细完成每一份作业，才能保证未来讲课不出硬伤。Now, roll up your sleeves and get to work!</span>";
		} else if (index == 3 && tage == 1) {
			return "<span style=\"font-size:14px;\">注意观看视频，这样你才能理解标准化教案并且能最有效地利用它。</span>";
		} else if (index == 3 && tage == 2) {
			return "<span style=\"font-size:14px;\">认真答题，祝你一次通关！</span>";
		} else if (index == 3 && tage == 3) {
			return "<span style=\"font-size:14px;\">标准化教案凝结了培训师们多年的心血，是几千个小时的课堂教学经验总结。一定要仔细学习，有不理解的地方，随时和我联系。</span>";
		} else if (index == 3 && tage == 4) {
			return "<span style=\"font-size:14px;\">现在，看看培训师们在课堂上是如何使用标准化教案的吧。每个老师的风格都不一样，你和他们的也不一样，边看边琢磨将来自己的授课风格吧。</span>";
		} else if (index == 4 && tage == 1) {
			return "<span style=\"font-size:14px;\">你可以在“资料查看”板块找到许多我们为你准备的备课素材，包括</span><span class=\"s1\" style=\"font-size:14px;\">PPT</span><span style=\"font-size:14px;\">模板、</span><span class=\"s1\" style=\"font-size:14px;\">PPT</span><span style=\"font-size:14px;\">制作指导、雅思口语评分规则视频等，你可以自由下载。现在开始创作你自己的课件吧！</span>";
		} else if (index == 4 && tage == 2) {
			return "<span style=\"font-size:14px;\">a. 请选取系统提供的任意一个PPT模板完成所有课次的课件编写；</span><br /><span style=\"font-size:14px;\">b. 请在所有PPT页当中使用同一字体（建议“微软雅黑”字体）；</span><br /><span style=\"font-size:14px;\">c. 基础课程的老师，需要完成10次课的课件，每次课2个小时；</span><br /><span style=\"font-size:14px;\">d. 强化课程的老师，需要完成8次课的课件，每次课2个小时。</span>";
		} else if (index == 4 && tage == 3) {
			return "<li><b><span style=\"font-size:14px;\">资料名称：</span></b><span style=\"font-size:14px;\">“集团雅思课题”-多元化智能理论在雅思基础段教学的应用-济南新东方雅思 20121226&nbsp;</span><a href=\"http://me.xdf.cn/otp/resources/www/courseware/1.pdf\" target=\"_blank\"><span style=\"font-size:14px;\">我要下载</span><i class=\"icon-download\"></i></a>&nbsp;<br /></li><li>"
					+ "<b><span style=\"font-size:14px;\">资料名称：</span></b><span style=\"font-size:14px;\">The value of teaching culture in IELTS speaking and writing course【Mat Clark 武汉新东方】&nbsp;</span><a href=\"http://me.xdf.cn/otp/resources/www/courseware/2.pdf\" target=\"_blank\"><span style=\"font-size:14px;\">我要下载</span><i class=\"icon-download\"></i></a>&nbsp;<br />"
					+ "</li><li>"
					+ "<b><span style=\"font-size:14px;\">资料名称：</span></b><span style=\"font-size:14px;\">将英联邦历史及文化融入雅思基础阅读课程【西安新东方 王鹏】&nbsp;</span><a href=\"http://me.xdf.cn/otp/resources/www/courseware/3.pdf\" target=\"_blank\"><span style=\"font-size:14px;\">我要下载</span><i class=\"icon-download\"></i></a>&nbsp;<br />"
					+ "</li><li>"
					+ "<b><span style=\"font-size:14px;\">资料名称：</span></b><span style=\"font-size:14px;\">批改网宣传片&nbsp;</span><a href=\"http://me.xdf.cn/otp/resources/www/courseware/4.mp4\" target=\"_blank\"><span style=\"font-size:14px;\">我要下载</span><i class=\"icon-download\"></i></a>&nbsp;<br />"
					+ "</li><li>"
					+ "<b><span style=\"font-size:14px;\">资料名称：</span></b><span style=\"font-size:14px;\">学生提分指南&nbsp;</span><a href=\"http://me.xdf.cn/otp/resources/www/courseware/5.pdf\" target=\"_blank\"><span style=\"font-size:14px;\">我要下载</span><i class=\"icon-download\"></i></a>&nbsp;<br />"
					+ "</li><li>"
					+ "<b><span style=\"font-size:14px;\">资料名称：</span></b><span style=\"font-size:14px;\">雅思听口常见问题Q&amp;A&nbsp;</span><a href=\"http://me.xdf.cn/otp/resources/www/courseware/6.pptx\" target=\"_blank\"><span style=\"font-size:14px;\">我要下载</span><i class=\"icon-download\"></i></a>&nbsp;<br />"
					+ "</li><li>"
					+ "<b><span style=\"font-size:14px;\">资料名称：</span></b><span style=\"font-size:14px;\">雅思阅写常见问题QA&nbsp;</span><a href=\"http://me.xdf.cn/otp/resources/www/courseware/7.pptx\" target=\"_blank\"><span style=\"font-size:14px;\">我要下载</span><i class=\"icon-download\"></i></a>&nbsp;"
					+ "</li>";
		}

		return null;
	}
}
