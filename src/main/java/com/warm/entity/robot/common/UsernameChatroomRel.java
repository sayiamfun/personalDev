package com.warm.entity.robot.common;

import java.io.Serializable;


public class UsernameChatroomRel implements Serializable
{
	private String chatroomName;
	private String nicknameInChatroom;
	private String username;
	
	public UsernameChatroomRel()
	{
		super();
	}
	
	public UsernameChatroomRel(final String chatroomName, final String username, final String nicknameInChatroom)
	{
		super();
		this.chatroomName = chatroomName;
		this.username = username;
		this.nicknameInChatroom = nicknameInChatroom;
	}
	
	protected boolean canEqual(final Object o)
	{
		return o instanceof UsernameChatroomRel;
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (o != this)
		{
			if (!(o instanceof UsernameChatroomRel))
			{
				return false;
			}
			final UsernameChatroomRel usernameChatroomRel = (UsernameChatroomRel) o;
			if (!usernameChatroomRel.canEqual(this))
			{
				return false;
			}
			final String chatroomName = this.getChatroomName();
			final String chatroomName2 = usernameChatroomRel.getChatroomName();
			Label_0059:
			{
				if (chatroomName == null)
				{
					if (chatroomName2 == null)
					{
						break Label_0059;
					}
				}
				else if (chatroomName.equals(chatroomName2))
				{
					break Label_0059;
				}
				return false;
			}
			final String username = this.getUsername();
			final String username2 = usernameChatroomRel.getUsername();
			Label_0087:
			{
				if (username == null)
				{
					if (username2 == null)
					{
						break Label_0087;
					}
				}
				else if (username.equals(username2))
				{
					break Label_0087;
				}
				return false;
			}
			final String nicknameInChatroom = this.getNicknameInChatroom();
			final String nicknameInChatroom2 = usernameChatroomRel.getNicknameInChatroom();
			if (nicknameInChatroom == null)
			{
				if (nicknameInChatroom2 == null)
				{
					return true;
				}
			}
			else if (nicknameInChatroom.equals(nicknameInChatroom2))
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
	
	public String getNicknameInChatroom()
	{
		return this.nicknameInChatroom;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = 43;
		final String chatroomName = this.getChatroomName();
		int hashCode2;
		if (chatroomName == null)
		{
			hashCode2 = 43;
		}
		else
		{
			hashCode2 = chatroomName.hashCode();
		}
		final String username = this.getUsername();
		int hashCode3;
		if (username == null)
		{
			hashCode3 = 43;
		}
		else
		{
			hashCode3 = username.hashCode();
		}
		final String nicknameInChatroom = this.getNicknameInChatroom();
		if (nicknameInChatroom != null)
		{
			hashCode = nicknameInChatroom.hashCode();
		}
		return (hashCode3 + (hashCode2 + 59) * 59) * 59 + hashCode;
	}
	
	public void setChatroomName(final String chatroomName)
	{
		this.chatroomName = chatroomName;
	}
	
	public void setNicknameInChatroom(final String nicknameInChatroom)
	{
		this.nicknameInChatroom = nicknameInChatroom;
	}
	
	public void setUsername(final String username)
	{
		this.username = username;
	}
	
	@Override
	public String toString()
	{
		return "UsernameChatroomRel(chatroomName=" + this.getChatroomName() + ", username=" + this.getUsername() + ", nicknameInChatroom=" + this.getNicknameInChatroom() + ")";
	}
}
