package com.warm.entity.robot.responseInfo;

import java.io.Serializable;


public class FriendRequestInfo implements  Serializable
{
    private static final long serialVersionUID = -7242186942306481839L;
    private String content;   //好友请求消息
    private Long createTime;  //加好友时间
    private String nickname;  //昵称
    private Integer scene;
    private String ticket;
    private String username;  //微信id

    protected boolean canEqual(Object obj)
    {
        return obj instanceof FriendRequestInfo;
    }

    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (!(obj instanceof FriendRequestInfo))
        {
            return false;
        }
        FriendRequestInfo friendRequestInfo = (FriendRequestInfo) obj;
        if (!friendRequestInfo.canEqual(this))
        {
            return false;
        }
        String username = getUsername();
        String username2 = friendRequestInfo.getUsername();
        if (username != null ? !username.equals(username2) : username2 != null)
        {
            return false;
        }
        username = getNickname();
        username2 = friendRequestInfo.getNickname();
        if (username != null ? !username.equals(username2) : username2 != null)
        {
            return false;
        }
        username = getTicket();
        username2 = friendRequestInfo.getTicket();
        if (username != null ? !username.equals(username2) : username2 != null)
        {
            return false;
        }
        Long createTime = getCreateTime();
        Long createTime2 = friendRequestInfo.getCreateTime();
        if (createTime != null ? !createTime.equals(createTime2) : createTime2 != null)
        {
            return false;
        }
        username = getContent();
        username2 = friendRequestInfo.getContent();
        if (username != null ? !username.equals(username2) : username2 != null)
        {
            return false;
        }
        Integer scene = getScene();
        Integer scene2 = friendRequestInfo.getScene();
        if (scene == null)
        {
            if (scene2 == null)
            {
                return true;
            }
        } else if (scene.equals(scene2))
        {
            return true;
        }
        return false;
    }

    public String getContent()
    {
        return this.content;
    }

    public Long getCreateTime()
    {
        return this.createTime;
    }

    public String getNickname()
    {
        return this.nickname;
    }

    public Integer getScene()
    {
        return this.scene;
    }

    public String getTicket()
    {
        return this.ticket;
    }

    public String getUsername()
    {
        return this.username;
    }

    public int hashCode()
    {
        int i = 43;
        String username = getUsername();
        int hashCode = (username == null ? 43 : username.hashCode()) + 59;
        String nickname = getNickname();
        hashCode = (nickname == null ? 43 : nickname.hashCode()) + (hashCode * 59);
        nickname = getTicket();
        hashCode = (nickname == null ? 43 : nickname.hashCode()) + (hashCode * 59);
        Long createTime = getCreateTime();
        hashCode = (createTime == null ? 43 : createTime.hashCode()) + (hashCode * 59);
        nickname = getContent();
        hashCode = (nickname == null ? 43 : nickname.hashCode()) + (hashCode * 59);
        Integer scene = getScene();
        hashCode *= 59;
        if (scene != null)
        {
            i = scene.hashCode();
        }
        return hashCode + i;
    }

    public boolean isValid()
    {
        return (this.username == null || this.ticket == null || this.createTime == null || this.scene == null) ? false : true;
    }

    public void setContent(String str)
    {
        this.content = str;
    }

    public void setCreateTime(Long l)
    {
        this.createTime = l;
    }

    public void setNickname(String str)
    {
        this.nickname = str;
    }

    public void setScene(Integer num)
    {
        this.scene = num;
    }

    public void setTicket(String str)
    {
        this.ticket = str;
    }

    public void setUsername(String str)
    {
        this.username = str;
    }

    public String toString()
    {
        return "FriendRequestInfo(username=" + getUsername() + ", nickname=" + getNickname() + ", ticket=" + getTicket() + ", createTime=" + getCreateTime() + ", content=" + getContent() + ", scene=" + getScene() + ")";
    }
}
