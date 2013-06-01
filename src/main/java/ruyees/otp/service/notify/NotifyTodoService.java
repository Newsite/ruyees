package ruyees.otp.service.notify;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Updater;
import ruyees.otp.common.hibernate4.Updater.UpdateMode;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.model.notify.NotifyEnum;
import ruyees.otp.model.notify.NotifyTodo;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.BaseService;

/**
 * 
 * 代办消息service
 * 
 * @see ruyees.otp.model.notify.NotifyEnum
 * @author Zaric
 * @date 2013-6-1 上午1:39:36
 */
@Service
@Transactional(readOnly = true)
public class NotifyTodoService extends BaseService {

	private static final Logger log = LoggerFactory
			.getLogger(NotifyTodoService.class);

	/**
	 * 获取所有未发的邮件
	 * 
	 * @return
	 */
	public List<NotifyTodo> findAllIsNotSend() {
		return findByCriteria(NotifyTodo.class, Value.eq("isSend", false));
	}

	/**
	 * 根据人员和读取状态抓取数据
	 * 
	 * @param person
	 * @param isread
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NotifyTodo> findByUserAndReadStatus(SysOrgPerson person,
			boolean isread) {
		Finder finder = Finder
				.create("select todo from NotifyTodo todo left join todo.acceptUser accept");
		finder.append("left join accept.person person");
		finder.append("where person.fdId=:fdId").setParam("fdId",
				person.getFdId());
		finder.append("and todo.isRead=:read").setParam("read", isread);
		return find(finder);
	}

	/**
	 * 发送
	 * 
	 * <pre>
	 * 未发送的内容
	 * </pre>
	 */
	@Transactional(readOnly = false)
	public void excuteNotSend() {
		try {
			log.info("start execute send mail");
			List<NotifyTodo> todos = findAllIsNotSend();
			if (CollectionUtils.isEmpty(todos))
				return;
			log.info("需要发送的邮件数量为：" + todos.size());
			for (NotifyTodo todo : todos) {
				// notifyEnum.getObject().get(todo.getNotifyEnum()).excute(todo);
				notifyEnum.getObject().get(NotifyEnum.EMAIL).excute(todo);
				updateSendByNotSend(todo);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Transactional(readOnly = false)
	private void updateSendByNotSend(NotifyTodo todo) {
		NotifyTodo bean = new NotifyTodo();
		bean.setFdId(todo.getFdId());
		bean.setIsSend(true);
		Updater<NotifyTodo> updater = new Updater<NotifyTodo>(bean);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("isSend");
		updateByUpdater(updater);
	}

	/**
	 * 把代办值为已读
	 * 
	 * @param todo
	 */
	public void updateRead(NotifyTodo todo) {
		NotifyTodo bean = new NotifyTodo();
		bean.setFdId(todo.getFdId());
		bean.setIsRead(true);
		Updater<NotifyTodo> updater = new Updater<NotifyTodo>(bean);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("isRead");
		updateByUpdater(updater);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<NotifyTodo> getEntityClass() {
		return NotifyTodo.class;
	}

	@Autowired
	private NotifyTodoFacotry notifyEnum;
}
