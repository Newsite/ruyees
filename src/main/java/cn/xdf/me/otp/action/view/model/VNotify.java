package cn.xdf.me.otp.action.view.model;

/**
 * 消息查看内容
 * @author Zaric
 * @date  2013-6-1 上午12:40:07
 */
public class VNotify {
	
	public VNotify() {

	}

	public VNotify(String fdId, String title, String content, String sender, String reveicer, String time, String read) {
		super();
		this.title = title;
		this.content = content;
		this.sender = sender;
		this.reveicer = reveicer;
		this.time = time;
		this.read = read;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	private String fdId;
	private String title;
	private String content;
	private String sender;
	private String reveicer;
	private String time;
	private String read;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReveicer() {
		return reveicer;
	}

	public void setReveicer(String reveicer) {
		this.reveicer = reveicer;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

}
