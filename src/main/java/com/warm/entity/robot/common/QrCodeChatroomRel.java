package com.warm.entity.robot.common;

import java.io.Serializable;


public class QrCodeChatroomRel implements Serializable
{
	private String chatroomName;
	private Long createTime;
	private String qrcodeUrl;
	private String username;
	
	public QrCodeChatroomRel()
	{
		super();
	}
	
	public QrCodeChatroomRel(final String qrcodeUrl, final String chatroomName, final String username, final Long createTime)
	{
		super();
		this.qrcodeUrl = qrcodeUrl;
		this.chatroomName = chatroomName;
		this.username = username;
		this.createTime = createTime;
	}
	
	protected boolean canEqual(final Object o)
	{
		return o instanceof QrCodeChatroomRel;
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (o != this)
		{
			if (!(o instanceof QrCodeChatroomRel))
			{
				return false;
			}
			final QrCodeChatroomRel qrCodeChatroomRel = (QrCodeChatroomRel) o;
			if (!qrCodeChatroomRel.canEqual(this))
			{
				return false;
			}
			final String qrcodeUrl = this.getQrcodeUrl();
			final String qrcodeUrl2 = qrCodeChatroomRel.getQrcodeUrl();
			Label_0059:
			{
				if (qrcodeUrl == null)
				{
					if (qrcodeUrl2 == null)
					{
						break Label_0059;
					}
				}
				else if (qrcodeUrl.equals(qrcodeUrl2))
				{
					break Label_0059;
				}
				return false;
			}
			final String chatroomName = this.getChatroomName();
			final String chatroomName2 = qrCodeChatroomRel.getChatroomName();
			Label_0087:
			{
				if (chatroomName == null)
				{
					if (chatroomName2 == null)
					{
						break Label_0087;
					}
				}
				else if (chatroomName.equals(chatroomName2))
				{
					break Label_0087;
				}
				return false;
			}
			final String username = this.getUsername();
			final String username2 = qrCodeChatroomRel.getUsername();
			Label_0115:
			{
				if (username == null)
				{
					if (username2 == null)
					{
						break Label_0115;
					}
				}
				else if (username.equals(username2))
				{
					break Label_0115;
				}
				return false;
			}
			final Long createTime = this.getCreateTime();
			final Long createTime2 = qrCodeChatroomRel.getCreateTime();
			if (createTime == null)
			{
				if (createTime2 == null)
				{
					return true;
				}
			}
			else if (createTime.equals(createTime2))
			{
				return true;
			}
			return false;
		}
		return true;
	}
	
	public String getChatroomName()
	{
		return this.chatroomName;
	}
	
	public Long getCreateTime()
	{
		return this.createTime;
	}
	
	public String getQrcodeUrl()
	{
		return this.qrcodeUrl;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = 43;
		final String qrcodeUrl = this.getQrcodeUrl();
		int hashCode2;
		if (qrcodeUrl == null)
		{
			hashCode2 = 43;
		}
		else
		{
			hashCode2 = qrcodeUrl.hashCode();
		}
		final String chatroomName = this.getChatroomName();
		int hashCode3;
		if (chatroomName == null)
		{
			hashCode3 = 43;
		}
		else
		{
			hashCode3 = chatroomName.hashCode();
		}
		final String username = this.getUsername();
		int hashCode4;
		if (username == null)
		{
			hashCode4 = 43;
		}
		else
		{
			hashCode4 = username.hashCode();
		}
		final Long createTime = this.getCreateTime();
		if (createTime != null)
		{
			hashCode = createTime.hashCode();
		}
		return (hashCode4 + (hashCode3 + (hashCode2 + 59) * 59) * 59) * 59 + hashCode;
	}
	
	public void setChatroomName(final String chatroomName)
	{
		this.chatroomName = chatroomName;
	}
	
	public void setCreateTime(final Long createTime)
	{
		this.createTime = createTime;
	}
	
	public void setQrcodeUrl(final String qrcodeUrl)
	{
		this.qrcodeUrl = qrcodeUrl;
	}
	
	public void setUsername(final String username)
	{
		this.username = username;
	}
	
	@Override
	public String toString()
	{
		return "QrCodeChatroomRel(qrcodeUrl=" + this.getQrcodeUrl() + ", chatroomName=" + this.getChatroomName() + ", username=" + this.getUsername() + ", createTime=" + this.getCreateTime() + ")";
	}
}
