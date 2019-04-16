package com.warm.entity.robot.requestInfo;

import java.io.Serializable;


public class DeviceWeChatInfo implements Serializable
{
	public static final long serialVersionUID = 1615534915558476294L;
	public String email;
	public String nickname;
	public String phone;
	public String region;
	public String whatsUp;
	
	protected boolean canEqual(Object obj)
	{
		return obj instanceof DeviceWeChatInfo;
	}
	
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (!(obj instanceof DeviceWeChatInfo))
		{
			return false;
		}
		DeviceWeChatInfo deviceWeChatInfo = (DeviceWeChatInfo) obj;
		if (!deviceWeChatInfo.canEqual(this))
		{
			return false;
		}
		String nickname = getNickname();
		String nickname2 = deviceWeChatInfo.getNickname();
		if (nickname != null ? !nickname.equals(nickname2) : nickname2 != null)
		{
			return false;
		}
		nickname = getPhone();
		nickname2 = deviceWeChatInfo.getPhone();
		if (nickname != null ? !nickname.equals(nickname2) : nickname2 != null)
		{
			return false;
		}
		nickname = getEmail();
		nickname2 = deviceWeChatInfo.getEmail();
		if (nickname != null ? !nickname.equals(nickname2) : nickname2 != null)
		{
			return false;
		}
		nickname = getWhatsUp();
		nickname2 = deviceWeChatInfo.getWhatsUp();
		if (nickname != null ? !nickname.equals(nickname2) : nickname2 != null)
		{
			return false;
		}
		nickname = getRegion();
		nickname2 = deviceWeChatInfo.getRegion();
		if (nickname == null)
		{
			if (nickname2 == null)
			{
				return true;
			}
		} else if (nickname.equals(nickname2))
		{
			return true;
		}
		return false;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getNickname()
	{
		return this.nickname;
	}
	
	public String getPhone()
	{
		return this.phone;
	}
	
	public String getRegion()
	{
		return this.region;
	}
	
	public String getWhatsUp()
	{
		return this.whatsUp;
	}
	
	public int hashCode()
	{
		int i = 43;
		String nickname = getNickname();
		int hashCode = (nickname == null ? 43 : nickname.hashCode()) + 59;
		String phone = getPhone();
		hashCode = (phone == null ? 43 : phone.hashCode()) + (hashCode * 59);
		phone = getEmail();
		hashCode = (phone == null ? 43 : phone.hashCode()) + (hashCode * 59);
		phone = getWhatsUp();
		hashCode = (phone == null ? 43 : phone.hashCode()) + (hashCode * 59);
		phone = getRegion();
		hashCode *= 59;
		if (phone != null)
		{
			i = phone.hashCode();
		}
		return hashCode + i;
	}
	
	public void setEmail(String str)
	{
		this.email = str;
	}
	
	public void setNickname(String str)
	{
		this.nickname = str;
	}
	
	public void setPhone(String str)
	{
		this.phone = str;
	}
	
	public void setRegion(String str)
	{
		this.region = str;
	}
	
	public void setWhatsUp(String str)
	{
		this.whatsUp = str;
	}
	
	public String toString()
	{
		return "DeviceWeChatInfo(nickname=" + getNickname() + ", phone=" + getPhone() + ", email=" + getEmail() + ", whatsUp=" + getWhatsUp() + ", region=" + getRegion() + ")";
	}
}
