package com.warm.entity.robot.common;

import java.io.Serializable;


public class EarthErrorPrompt implements Serializable
{
	private static final long serialVersionUID = 7673145821920382719L;
	private String chatroom;
	private long createTime;
	private long id;
	private int level;
	private int logicId;
	private String message;
	private String stackTrace;
	private String systemInfo;
	
	protected boolean canEqual(Object obj)
	{
		return obj instanceof EarthErrorPrompt;
	}
	
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (!(obj instanceof EarthErrorPrompt))
		{
			return false;
		}
		EarthErrorPrompt earthErrorPrompt = (EarthErrorPrompt) obj;
		if (!earthErrorPrompt.canEqual(this))
		{
			return false;
		}
		if (getId() != earthErrorPrompt.getId())
		{
			return false;
		}
		if (getCreateTime() != earthErrorPrompt.getCreateTime())
		{
			return false;
		}
		if (getLogicId() != earthErrorPrompt.getLogicId())
		{
			return false;
		}
		if (getLevel() != earthErrorPrompt.getLevel())
		{
			return false;
		}
		String chatroom = getChatroom();
		String chatroom2 = earthErrorPrompt.getChatroom();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		chatroom = getMessage();
		chatroom2 = earthErrorPrompt.getMessage();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		chatroom = getSystemInfo();
		chatroom2 = earthErrorPrompt.getSystemInfo();
		if (chatroom != null ? !chatroom.equals(chatroom2) : chatroom2 != null)
		{
			return false;
		}
		chatroom = getStackTrace();
		chatroom2 = earthErrorPrompt.getStackTrace();
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
	
	public String getChatroom()
	{
		return this.chatroom;
	}
	
	public long getCreateTime()
	{
		return this.createTime;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public int getLogicId()
	{
		return this.logicId;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public String getStackTrace()
	{
		return this.stackTrace;
	}
	
	public String getSystemInfo()
	{
		return this.systemInfo;
	}
	
	public int hashCode()
	{
		int i = 43;
		long id = getId();
		int i2 = ((int) (id ^ (id >>> 32))) + 59;
		id = getCreateTime();
		i2 = (((((i2 * 59) + ((int) (id ^ (id >>> 32)))) * 59) + getLogicId()) * 59) + getLevel();
		String chatroom = getChatroom();
		i2 = (chatroom == null ? 43 : chatroom.hashCode()) + (i2 * 59);
		chatroom = getMessage();
		i2 = (chatroom == null ? 43 : chatroom.hashCode()) + (i2 * 59);
		chatroom = getSystemInfo();
		i2 = (chatroom == null ? 43 : chatroom.hashCode()) + (i2 * 59);
		chatroom = getStackTrace();
		i2 *= 59;
		if (chatroom != null)
		{
			i = chatroom.hashCode();
		}
		return i2 + i;
	}
	
	public void setChatroom(String str)
	{
		this.chatroom = str;
	}
	
	public void setCreateTime(long j)
	{
		this.createTime = j;
	}
	
	public void setId(long j)
	{
		this.id = j;
	}
	
	public void setLevel(int i)
	{
		this.level = i;
	}
	
	public void setLogicId(int i)
	{
		this.logicId = i;
	}
	
	public void setMessage(String str)
	{
		this.message = str;
	}
	
	public void setStackTrace(String str)
	{
		this.stackTrace = str;
	}
	
	public void setSystemInfo(String str)
	{
		this.systemInfo = str;
	}
	
	public String toString()
	{
		return "EarthErrorPrompt(id=" + getId() + ", createTime=" + getCreateTime() + ", logicId=" + getLogicId() + ", level=" + getLevel() + ", chatroom=" + getChatroom() + ", message=" + getMessage() + ", systemInfo=" + getSystemInfo() + ", stackTrace=" + getStackTrace() + ")";
	}
}
