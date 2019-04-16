package com.warm.entity.robot.common;

import java.io.Serializable;



//表示单个微信好友实体

public class WeChatFriend implements Serializable
{
	private String alias;
	private String city;
	private String conRemark;
	private String nickname;
	private String picUrl;
	private String province;
	private String softbankName;
	private String username;
	
	public WeChatFriend()
	{
		super();
	}
	
	public WeChatFriend(final String username, final String alias, final String nickname, final String conRemark)
	{
		super();
		this.username = username;
		this.alias = alias;
		this.nickname = nickname;
		this.conRemark = conRemark;
	}
	
	public WeChatFriend(final String s, final String s2, final String s3, final String softbankName, final String s4)
	{
		this(s, s2, s3, s4);
		this.softbankName = softbankName;
	}
	
	protected boolean canEqual(final Object o)
	{
		return o instanceof WeChatFriend;
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (o != this)
		{
			if (!(o instanceof WeChatFriend))
			{
				return false;
			}
			final WeChatFriend weChatFriend = (WeChatFriend) o;
			if (!weChatFriend.canEqual(this))
			{
				return false;
			}
			final String username = this.getUsername();
			final String username2 = weChatFriend.getUsername();
			Label_0059:
			{
				if (username == null)
				{
					if (username2 == null)
					{
						break Label_0059;
					}
				}
				else if (username.equals(username2))
				{
					break Label_0059;
				}
				return false;
			}
			final String alias = this.getAlias();
			final String alias2 = weChatFriend.getAlias();
			Label_0087:
			{
				if (alias == null)
				{
					if (alias2 == null)
					{
						break Label_0087;
					}
				}
				else if (alias.equals(alias2))
				{
					break Label_0087;
				}
				return false;
			}
			final String nickname = this.getNickname();
			final String nickname2 = weChatFriend.getNickname();
			Label_0115:
			{
				if (nickname == null)
				{
					if (nickname2 == null)
					{
						break Label_0115;
					}
				}
				else if (nickname.equals(nickname2))
				{
					break Label_0115;
				}
				return false;
			}
			final String softbankName = this.getSoftbankName();
			final String softbankName2 = weChatFriend.getSoftbankName();
			Label_0143:
			{
				if (softbankName == null)
				{
					if (softbankName2 == null)
					{
						break Label_0143;
					}
				}
				else if (softbankName.equals(softbankName2))
				{
					break Label_0143;
				}
				return false;
			}
			final String conRemark = this.getConRemark();
			final String conRemark2 = weChatFriend.getConRemark();
			Label_0171:
			{
				if (conRemark == null)
				{
					if (conRemark2 == null)
					{
						break Label_0171;
					}
				}
				else if (conRemark.equals(conRemark2))
				{
					break Label_0171;
				}
				return false;
			}
			final String picUrl = this.getPicUrl();
			final String picUrl2 = weChatFriend.getPicUrl();
			Label_0199:
			{
				if (picUrl == null)
				{
					if (picUrl2 == null)
					{
						break Label_0199;
					}
				}
				else if (picUrl.equals(picUrl2))
				{
					break Label_0199;
				}
				return false;
			}
			final String city = this.getCity();
			final String city2 = weChatFriend.getCity();
			Label_0227:
			{
				if (city == null)
				{
					if (city2 == null)
					{
						break Label_0227;
					}
				}
				else if (city.equals(city2))
				{
					break Label_0227;
				}
				return false;
			}
			final String province = this.getProvince();
			final String province2 = weChatFriend.getProvince();
			if (province == null)
			{
				if (province2 == null)
				{
					return true;
				}
			}
			else if (province.equals(province2))
			{
				return true;
			}
			return false;
		}
		return true;
	}
	
	public String getAlias()
	{
		return this.alias;
	}
	
	
	public String getCity()
	{
		return this.city;
	}
	
	public String getConRemark()
	{
		return this.conRemark;
	}
	
	public String getNickname()
	{
		return this.nickname;
	}
	
	public String getPicUrl()
	{
		return this.picUrl;
	}
	
	public String getProvince()
	{
		return this.province;
	}
	
	public String getSoftbankName()
	{
		return this.softbankName;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = 43;
		final String username = this.getUsername();
		int hashCode2;
		if (username == null)
		{
			hashCode2 = 43;
		}
		else
		{
			hashCode2 = username.hashCode();
		}
		final String alias = this.getAlias();
		int hashCode3;
		if (alias == null)
		{
			hashCode3 = 43;
		}
		else
		{
			hashCode3 = alias.hashCode();
		}
		final String nickname = this.getNickname();
		int hashCode4;
		if (nickname == null)
		{
			hashCode4 = 43;
		}
		else
		{
			hashCode4 = nickname.hashCode();
		}
		final String softbankName = this.getSoftbankName();
		int hashCode5;
		if (softbankName == null)
		{
			hashCode5 = 43;
		}
		else
		{
			hashCode5 = softbankName.hashCode();
		}
		final String conRemark = this.getConRemark();
		int hashCode6;
		if (conRemark == null)
		{
			hashCode6 = 43;
		}
		else
		{
			hashCode6 = conRemark.hashCode();
		}
		final String picUrl = this.getPicUrl();
		int hashCode7;
		if (picUrl == null)
		{
			hashCode7 = 43;
		}
		else
		{
			hashCode7 = picUrl.hashCode();
		}
		final String city = this.getCity();
		int hashCode8;
		if (city == null)
		{
			hashCode8 = 43;
		}
		else
		{
			hashCode8 = city.hashCode();
		}
		final String province = this.getProvince();
		if (province != null)
		{
			hashCode = province.hashCode();
		}
		return (hashCode8 + (hashCode7 + (hashCode6 + (hashCode5 + (hashCode4 + (hashCode3 + (hashCode2 + 59) * 59) * 59) * 59) * 59) * 59) * 59) * 59 + hashCode;
	}
	
	public void setAlias(final String alias)
	{
		this.alias = alias;
	}
	
	public void setCity(final String city)
	{
		this.city = city;
	}
	
	public void setConRemark(final String conRemark)
	{
		this.conRemark = conRemark;
	}
	
	public void setNickname(final String nickname)
	{
		this.nickname = nickname;
	}
	
	public void setPicUrl(final String picUrl)
	{
		this.picUrl = picUrl;
	}
	
	public void setProvince(final String province)
	{
		this.province = province;
	}
	
	public void setSoftbankName(final String softbankName)
	{
		this.softbankName = softbankName;
	}
	
	public void setUsername(final String username)
	{
		this.username = username;
	}
	
	@Override
	public String toString()
	{
		return "WeChatFriend(username=" + this.getUsername() + ", alias=" + this.getAlias() + ", nickname=" + this.getNickname() + ", softbankName=" + this.getSoftbankName() + ", conRemark=" + this.getConRemark() + ", picUrl=" + this.getPicUrl() + ", city=" + this.getCity() + ", province=" + this.getProvince() + ")";
	}
}
