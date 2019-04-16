package com.warm.entity.robot.responseInfo;


import java.io.Serializable;
import java.util.List;


public class SunTask implements Serializable
{
	private static final long serialVersionUID = 5352440211057883528L;
	private String alias;        //别名
	private String chatroom;    //聊天室
	private String content;        //内容		//新建聊天室时的聊天室名称
	private List<String> contentList;    //内容列表
	private Long createTime;    //创建时间
	private String ext;        //扩展信息
	private Long id;        //任务唯一id
	private String owner;    //所有者
	private Long packageId;    //包id
	private Integer priority;    //优先权
	private List<String> toUsernames;    //目标用户名列表		//新建聊天室用来存储聊天室成员列表
	private Integer type;        //类型
	private Integer weChatMsgType;        //微信消息类型
	private String whatever;        //保留信息
	
	public SunTask()
	{
		this.createTime = Long.valueOf(System.currentTimeMillis() / 1000);
	}
	
	public SunTask(Integer num)
	{
		this(num, null);
	}
	
	public SunTask(Integer num, String str)
	{
		this.type = num;
		this.chatroom = str;
		this.createTime = Long.valueOf(System.currentTimeMillis() / 1000);
	}
	
	protected boolean canEqual(Object obj)
	{
		return obj instanceof SunTask;
	}
	
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (!(obj instanceof SunTask))
		{
			return false;
		}
		SunTask sunTask = (SunTask) obj;
		if (!sunTask.canEqual(this))
		{
			return false;
		}
		Long id = getId();
		Long id2 = sunTask.getId();
		if (id != null ? !id.equals(id2) : id2 != null)
		{
			return false;
		}
		Integer type = getType();
		Integer type2 = sunTask.getType();
		if (type != null ? !type.equals(type2) : type2 != null)
		{
			return false;
		}
		id = getCreateTime();
		id2 = sunTask.getCreateTime();
		if (id != null ? !id.equals(id2) : id2 != null)
		{
			return false;
		}
		String chatroom = getChatroom();
		String chatroom2 = sunTask.getChatroom();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		List toUsernames = getToUsernames();
		List toUsernames2 = sunTask.getToUsernames();
		if (toUsernames != null ? !toUsernames.equals(toUsernames2) : toUsernames2 != null)
		{
			return false;
		}
		chatroom = getAlias();
		chatroom2 = sunTask.getAlias();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		type = getWeChatMsgType();
		type2 = sunTask.getWeChatMsgType();
		if (type != null ? !type.equals(type2) : type2 != null)
		{
			return false;
		}
		chatroom = getContent();
		chatroom2 = sunTask.getContent();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		toUsernames = getContentList();
		toUsernames2 = sunTask.getContentList();
		if (toUsernames != null ? !toUsernames.equals(toUsernames2) : toUsernames2 != null)
		{
			return false;
		}
		chatroom = getExt();
		chatroom2 = sunTask.getExt();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		id = getPackageId();
		id2 = sunTask.getPackageId();
		if (id != null ? !id.equals(id2) : id2 != null)
		{
			return false;
		}
		type = getPriority();
		type2 = sunTask.getPriority();
		if (type != null ? !type.equals(type2) : type2 != null)
		{
			return false;
		}
		chatroom = getOwner();
		chatroom2 = sunTask.getOwner();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		chatroom = getWhatever();
		chatroom2 = sunTask.getWhatever();
		if (chatroom == null)
		{
			if (chatroom2 == null)
			{
				return true;
			}
		} else if (chatroom.equals(chatroom2))
		{
			return true;
		}
		return false;
	}
	
	public String getAlias()
	{
		return this.alias;
	}
	
	public String getChatroom()
	{
		return this.chatroom;
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	public List<String> getContentList()
	{
		return this.contentList;
	}
	
	public Long getCreateTime()
	{
		return this.createTime;
	}
	
	public String getExt()
	{
		return this.ext;
	}
	
	public Long getId()
	{
		return this.id;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public Long getPackageId()
	{
		return this.packageId;
	}
	
	public Integer getPriority()
	{
		return this.priority;
	}
	
	public List<String> getToUsernames()
	{
		return this.toUsernames;
	}
	
	public Integer getType()
	{
		return this.type;
	}
	
	public Integer getWeChatMsgType()
	{
		return this.weChatMsgType;
	}
	
	public String getWhatever()
	{
		return this.whatever;
	}
	
	public int hashCode()
	{
		int i = 43;
		Long id = getId();
		int hashCode = (id == null ? 43 : id.hashCode()) + 59;
		Integer type = getType();
		hashCode = (type == null ? 43 : type.hashCode()) + (hashCode * 59);
		Long createTime = getCreateTime();
		hashCode = (createTime == null ? 43 : createTime.hashCode()) + (hashCode * 59);
		String chatroom = getChatroom();
		hashCode = (chatroom == null ? 43 : chatroom.hashCode()) + (hashCode * 59);
		List toUsernames = getToUsernames();
		hashCode = (toUsernames == null ? 43 : toUsernames.hashCode()) + (hashCode * 59);
		chatroom = getAlias();
		hashCode = (chatroom == null ? 43 : chatroom.hashCode()) + (hashCode * 59);
		type = getWeChatMsgType();
		hashCode = (type == null ? 43 : type.hashCode()) + (hashCode * 59);
		chatroom = getContent();
		hashCode = (chatroom == null ? 43 : chatroom.hashCode()) + (hashCode * 59);
		toUsernames = getContentList();
		hashCode = (toUsernames == null ? 43 : toUsernames.hashCode()) + (hashCode * 59);
		chatroom = getExt();
		hashCode = (chatroom == null ? 43 : chatroom.hashCode()) + (hashCode * 59);
		createTime = getPackageId();
		hashCode = (createTime == null ? 43 : createTime.hashCode()) + (hashCode * 59);
		type = getPriority();
		hashCode = (type == null ? 43 : type.hashCode()) + (hashCode * 59);
		chatroom = getOwner();
		hashCode = (chatroom == null ? 43 : chatroom.hashCode()) + (hashCode * 59);
		chatroom = getWhatever();
		hashCode *= 59;
		if (chatroom != null)
		{
			i = chatroom.hashCode();
		}
		return hashCode + i;
	}
	
	public void setAlias(String str)
	{
		this.alias = str;
	}
	
	public void setChatroom(String str)
	{
		this.chatroom = str;
	}
	
	public void setContent(String str)
	{
		this.content = str;
	}
	
	public void setContentList(List<String> list)
	{
		this.contentList = list;
	}
	
	public void setCreateTime(Long l)
	{
		this.createTime = l;
	}
	
	public void setExt(String str)
	{
		this.ext = str;
	}
	
	public void setId(Long l)
	{
		this.id = l;
	}
	
	public void setOwner(String str)
	{
		this.owner = str;
	}
	
	public void setPackageId(Long l)
	{
		this.packageId = l;
	}
	
	public void setPriority(Integer num)
	{
		this.priority = num;
	}
	
	public void setToUsernames(List<String> list)
	{
		this.toUsernames = list;
	}
	
	public void setType(Integer num)
	{
		this.type = num;
	}
	
	public void setWeChatMsgType(Integer num)
	{
		this.weChatMsgType = num;
	}
	
	public void setWhatever(String str)
	{
		this.whatever = str;
	}
	
	public String toString()
	{
		return "SunTask(id=" + getId() + ", type=" + getType() + ", createTime=" + getCreateTime() +
			   ", chatroom=" + getChatroom() + ", toUsernames=" + getToUsernames() + ", alias=" +
			   getAlias() + ", weChatMsgType=" + getWeChatMsgType() + ", content=" + getContent() +
			   ", contentList=" + getContentList() + ", ext=" + getExt() + ", packageId=" +
			   getPackageId() + ", priority=" + getPriority() + ", owner=" + getOwner() +
			   ", whatever=" + getWhatever() + ")";
	}
}
