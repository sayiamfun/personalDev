package com.warm.entity.robot.common;

import java.io.Serializable;
import java.util.List;


public class WeChatroom implements Serializable
{
	private List<String> adminUsernameList;
	private String chatroomName;
	//chatroomNickname = gname 群名称
	private String chatroomNickname;
	private String chatroomNotice;
	private Long chatroomNoticePublishTime;
	private List<String> memberUsernameList;
	private String roomowner;
	
	public WeChatroom()
	{
		super();
	}
	
	public WeChatroom(final String chatroomName, final String roomowner, final String chatroomNickname, final List<String> memberUsernameList, final String chatroomNotice, final Long chatroomNoticePublishTime)
	{
		super();
		this.chatroomName = chatroomName;
		this.roomowner = roomowner;
		this.chatroomNickname = chatroomNickname;
		this.memberUsernameList = memberUsernameList;
		this.chatroomNotice = chatroomNotice;
		this.chatroomNoticePublishTime = chatroomNoticePublishTime;
	}
	
	public WeChatroom(final String chatroomName, final String roomowner, final String chatroomNickname, final List<String> memberUsernameList, final String chatroomNotice, final Long chatroomNoticePublishTime, final List<String> adminUsernameList)
	{
		super();
		this.chatroomName = chatroomName;
		this.roomowner = roomowner;
		this.chatroomNickname = chatroomNickname;
		this.memberUsernameList = memberUsernameList;
		this.chatroomNotice = chatroomNotice;
		this.chatroomNoticePublishTime = chatroomNoticePublishTime;
		this.adminUsernameList = adminUsernameList;
	}
	
	public WeChatroom(final String chatroomName, final String chatroomNickname, final List<String> memberUsernameList)
	{
		super();
		this.chatroomName = chatroomName;
		this.chatroomNickname = chatroomNickname;
		this.memberUsernameList = memberUsernameList;
	}
	
	public WeChatroom(final String chatroomName, final List<String> memberUsernameList)
	{
		super();
		this.chatroomName = chatroomName;
		this.memberUsernameList = memberUsernameList;
	}
	
	protected boolean canEqual(final Object o)
	{
		return o instanceof WeChatroom;
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (o != this)
		{
			if (!(o instanceof WeChatroom))
			{
				return false;
			}
			final WeChatroom weChatroom = (WeChatroom) o;
			if (!weChatroom.canEqual(this))
			{
				return false;
			}
			final String chatroomName = this.getChatroomName();
			final String chatroomName2 = weChatroom.getChatroomName();
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
			final String roomowner = this.getRoomowner();
			final String roomowner2 = weChatroom.getRoomowner();
			Label_0087:
			{
				if (roomowner == null)
				{
					if (roomowner2 == null)
					{
						break Label_0087;
					}
				}
				else if (roomowner.equals(roomowner2))
				{
					break Label_0087;
				}
				return false;
			}
			final String chatroomNickname = this.getChatroomNickname();
			final String chatroomNickname2 = weChatroom.getChatroomNickname();
			Label_0115:
			{
				if (chatroomNickname == null)
				{
					if (chatroomNickname2 == null)
					{
						break Label_0115;
					}
				}
				else if (chatroomNickname.equals(chatroomNickname2))
				{
					break Label_0115;
				}
				return false;
			}
			final List<String> memberUsernameList = this.getMemberUsernameList();
			final List<String> memberUsernameList2 = weChatroom.getMemberUsernameList();
			Label_0143:
			{
				if (memberUsernameList == null)
				{
					if (memberUsernameList2 == null)
					{
						break Label_0143;
					}
				}
				else if (memberUsernameList.equals(memberUsernameList2))
				{
					break Label_0143;
				}
				return false;
			}
			final String chatroomNotice = this.getChatroomNotice();
			final String chatroomNotice2 = weChatroom.getChatroomNotice();
			Label_0171:
			{
				if (chatroomNotice == null)
				{
					if (chatroomNotice2 == null)
					{
						break Label_0171;
					}
				}
				else if (chatroomNotice.equals(chatroomNotice2))
				{
					break Label_0171;
				}
				return false;
			}
			final Long chatroomNoticePublishTime = this.getChatroomNoticePublishTime();
			final Long chatroomNoticePublishTime2 = weChatroom.getChatroomNoticePublishTime();
			Label_0199:
			{
				if (chatroomNoticePublishTime == null)
				{
					if (chatroomNoticePublishTime2 == null)
					{
						break Label_0199;
					}
				}
				else if (chatroomNoticePublishTime.equals(chatroomNoticePublishTime2))
				{
					break Label_0199;
				}
				return false;
			}
			final List<String> adminUsernameList = this.getAdminUsernameList();
			final List<String> adminUsernameList2 = weChatroom.getAdminUsernameList();
			if (adminUsernameList == null)
			{
				if (adminUsernameList2 == null)
				{
					return true;
				}
			}
			else if (adminUsernameList.equals(adminUsernameList2))
			{
				return true;
			}
			return false;
		}
		return true;
	}
	
	public List<String> getAdminUsernameList()
	{
		return this.adminUsernameList;
	}
	
	public String getChatroomName()
	{
		return this.chatroomName;
	}
	
	public String getChatroomNickname()
	{
		return this.chatroomNickname;
	}
	
	public String getChatroomNotice()
	{
		return this.chatroomNotice;
	}
	
	public Long getChatroomNoticePublishTime()
	{
		return this.chatroomNoticePublishTime;
	}
	
	public List<String> getMemberUsernameList()
	{
		return this.memberUsernameList;
	}
	
	public String getRoomowner()
	{
		return this.roomowner;
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
		final String roomowner = this.getRoomowner();
		int hashCode3;
		if (roomowner == null)
		{
			hashCode3 = 43;
		}
		else
		{
			hashCode3 = roomowner.hashCode();
		}
		final String chatroomNickname = this.getChatroomNickname();
		int hashCode4;
		if (chatroomNickname == null)
		{
			hashCode4 = 43;
		}
		else
		{
			hashCode4 = chatroomNickname.hashCode();
		}
		final List<String> memberUsernameList = this.getMemberUsernameList();
		int hashCode5;
		if (memberUsernameList == null)
		{
			hashCode5 = 43;
		}
		else
		{
			hashCode5 = memberUsernameList.hashCode();
		}
		final String chatroomNotice = this.getChatroomNotice();
		int hashCode6;
		if (chatroomNotice == null)
		{
			hashCode6 = 43;
		}
		else
		{
			hashCode6 = chatroomNotice.hashCode();
		}
		final Long chatroomNoticePublishTime = this.getChatroomNoticePublishTime();
		int hashCode7;
		if (chatroomNoticePublishTime == null)
		{
			hashCode7 = 43;
		}
		else
		{
			hashCode7 = chatroomNoticePublishTime.hashCode();
		}
		final List<String> adminUsernameList = this.getAdminUsernameList();
		if (adminUsernameList != null)
		{
			hashCode = adminUsernameList.hashCode();
		}
		return (hashCode7 + (hashCode6 + (hashCode5 + (hashCode4 + (hashCode3 + (hashCode2 + 59) * 59) * 59) * 59) * 59) * 59) * 59 + hashCode;
	}
	
	public void setAdminUsernameList(final List<String> adminUsernameList)
	{
		this.adminUsernameList = adminUsernameList;
	}
	
	public void setChatroomName(final String chatroomName)
	{
		this.chatroomName = chatroomName;
	}
	
	public void setChatroomNickname(final String chatroomNickname)
	{
		this.chatroomNickname = chatroomNickname;
	}
	
	public void setChatroomNotice(final String chatroomNotice)
	{
		this.chatroomNotice = chatroomNotice;
	}
	
	public void setChatroomNoticePublishTime(final Long chatroomNoticePublishTime)
	{
		this.chatroomNoticePublishTime = chatroomNoticePublishTime;
	}
	
	public void setMemberUsernameList(final List<String> memberUsernameList)
	{
		this.memberUsernameList = memberUsernameList;
	}
	
	public void setRoomowner(final String roomowner)
	{
		this.roomowner = roomowner;
	}
	
	@Override
	public String toString()
	{
		return "WeChatroom(chatroomName=" + this.getChatroomName() + ", roomowner=" + this.getRoomowner() + ", chatroomNickname=" + this.getChatroomNickname() + ", memberUsernameList=" + this.getMemberUsernameList() + ", chatroomNotice=" + this.getChatroomNotice() + ", chatroomNoticePublishTime=" + this.getChatroomNoticePublishTime() + ", adminUsernameList=" + this.getAdminUsernameList() + ")";
	}
}
