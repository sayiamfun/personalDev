package com.warm.entity.robot.responseInfo;

import java.io.Serializable;
import java.util.Set;


public class DeviceConf implements Serializable
{
    private static final long serialVersionUID = -5919387343884246048L;
    private Integer acceptOtherRoomInvite;		//接受其他聊天室邀请
    private Set<String> blockContactCardRegex;	//黑名单	名片
    private Set<String> blockImgMD5;		//黑名单	图片
    private Set<String> blockMsgRegex;		//黑名单	消息
    private Set<String> blockNicknames;		//黑名单	昵称
    private Integer blockTextLength;	//黑名单	最大文本长度
    private Set<String> blockUsernames;	//黑名单	用户名
    private Integer chatroomDownloadPic;	//聊天室下载图片数量???
    private Long defSendItr;		//默认的发送间隔时间
    private Integer individualDownloadPic;	//独立的下载图片数量???
    private Integer logFile;		//是否启用日志文件
    private Long msgCardSendItr;	//消息卡发送间隔时间
    private Integer pickTask;		//是否获取任务???
    private Integer upChatroom;		//是否 上传聊天室信息
    private Integer upNormalMsg;	//是否 上传普通信息
    private Integer upOtherOptMsg;	//是否上传其他可选信息
    private Integer upSelfOptMsg;	//是否上传自身可选信息
    private Integer upSelfSendMsg;	//是否上传自己发送的信息
    private Long voiceSendItr;		//语音信息发送..???
    private Set<String> whiteUsernames;	//白名单

    public static DeviceConf noServiceConf()
    {
        DeviceConf deviceConf = new DeviceConf();
        deviceConf.setChatroomDownloadPic(Integer.valueOf(0));
        deviceConf.setIndividualDownloadPic(Integer.valueOf(0));
        deviceConf.setAcceptOtherRoomInvite(Integer.valueOf(0));
        deviceConf.setPickTask(Integer.valueOf(0));
        deviceConf.setUpNormalMsg(Integer.valueOf(0));
        deviceConf.setUpOtherOptMsg(Integer.valueOf(0));
        deviceConf.setUpSelfOptMsg(Integer.valueOf(0));
        deviceConf.setUpChatroom(Integer.valueOf(0));
        deviceConf.setUpSelfSendMsg(Integer.valueOf(0));
        deviceConf.setLogFile(Integer.valueOf(0));
        return deviceConf;
    }

    public static DeviceConf nurseryConf()
    {
        DeviceConf deviceConf = new DeviceConf();
        deviceConf.setChatroomDownloadPic(Integer.valueOf(0));
        deviceConf.setIndividualDownloadPic(Integer.valueOf(0));
        deviceConf.setAcceptOtherRoomInvite(Integer.valueOf(0));
        deviceConf.setPickTask(Integer.valueOf(1));
        deviceConf.setUpNormalMsg(Integer.valueOf(0));
        deviceConf.setUpOtherOptMsg(Integer.valueOf(0));
        deviceConf.setUpSelfOptMsg(Integer.valueOf(0));
        deviceConf.setUpChatroom(Integer.valueOf(0));
        deviceConf.setUpSelfSendMsg(Integer.valueOf(0));
        deviceConf.setLogFile(Integer.valueOf(0));
        return deviceConf;
    }

    public void appendConf(DeviceConf deviceConf)
    {
        if (deviceConf.chatroomDownloadPic != null)
        {
            this.chatroomDownloadPic = deviceConf.chatroomDownloadPic;
        }
        if (deviceConf.individualDownloadPic != null)
        {
            this.individualDownloadPic = deviceConf.individualDownloadPic;
        }
        if (deviceConf.acceptOtherRoomInvite != null)
        {
            this.acceptOtherRoomInvite = deviceConf.acceptOtherRoomInvite;
        }
        if (deviceConf.pickTask != null)
        {
            this.pickTask = deviceConf.pickTask;
        }
        if (deviceConf.upNormalMsg != null)
        {
            this.upNormalMsg = deviceConf.upNormalMsg;
        }
        if (deviceConf.upOtherOptMsg != null)
        {
            this.upOtherOptMsg = deviceConf.upOtherOptMsg;
        }
        if (deviceConf.upSelfOptMsg != null)
        {
            this.upSelfOptMsg = deviceConf.upSelfOptMsg;
        }
        if (deviceConf.upChatroom != null)
        {
            this.upChatroom = deviceConf.upChatroom;
        }
        if (deviceConf.upSelfSendMsg != null)
        {
            this.upSelfSendMsg = deviceConf.upSelfSendMsg;
        }
        if (deviceConf.defSendItr != null)
        {
            this.defSendItr = deviceConf.defSendItr;
        }
        if (deviceConf.voiceSendItr != null)
        {
            this.voiceSendItr = deviceConf.voiceSendItr;
        }
        if (deviceConf.msgCardSendItr != null)
        {
            this.msgCardSendItr = deviceConf.msgCardSendItr;
        }
        if (deviceConf.logFile != null)
        {
            this.logFile = deviceConf.logFile;
        }
    }

    protected boolean canEqual(Object obj)
    {
        return obj instanceof DeviceConf;
    }

    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (!(obj instanceof DeviceConf))
        {
            return false;
        }
        DeviceConf deviceConf = (DeviceConf) obj;
        if (!deviceConf.canEqual(this))
        {
            return false;
        }
        Integer chatroomDownloadPic = getChatroomDownloadPic();
        Integer chatroomDownloadPic2 = deviceConf.getChatroomDownloadPic();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getIndividualDownloadPic();
        chatroomDownloadPic2 = deviceConf.getIndividualDownloadPic();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getAcceptOtherRoomInvite();
        chatroomDownloadPic2 = deviceConf.getAcceptOtherRoomInvite();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getPickTask();
        chatroomDownloadPic2 = deviceConf.getPickTask();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getUpNormalMsg();
        chatroomDownloadPic2 = deviceConf.getUpNormalMsg();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getUpOtherOptMsg();
        chatroomDownloadPic2 = deviceConf.getUpOtherOptMsg();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getUpSelfOptMsg();
        chatroomDownloadPic2 = deviceConf.getUpSelfOptMsg();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getUpChatroom();
        chatroomDownloadPic2 = deviceConf.getUpChatroom();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getUpSelfSendMsg();
        chatroomDownloadPic2 = deviceConf.getUpSelfSendMsg();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getLogFile();
        chatroomDownloadPic2 = deviceConf.getLogFile();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        Long defSendItr = getDefSendItr();
        Long defSendItr2 = deviceConf.getDefSendItr();
        if (defSendItr != null ? !defSendItr.equals(defSendItr2) : defSendItr2 != null)
        {
            return false;
        }
        defSendItr = getVoiceSendItr();
        defSendItr2 = deviceConf.getVoiceSendItr();
        if (defSendItr != null ? !defSendItr.equals(defSendItr2) : defSendItr2 != null)
        {
            return false;
        }
        defSendItr = getMsgCardSendItr();
        defSendItr2 = deviceConf.getMsgCardSendItr();
        if (defSendItr != null ? !defSendItr.equals(defSendItr2) : defSendItr2 != null)
        {
            return false;
        }
        chatroomDownloadPic = getBlockTextLength();
        chatroomDownloadPic2 = deviceConf.getBlockTextLength();
        if (chatroomDownloadPic != null ? !chatroomDownloadPic.equals(chatroomDownloadPic2) : chatroomDownloadPic2 != null)
        {
            return false;
        }
        Set whiteUsernames = getWhiteUsernames();
        Set whiteUsernames2 = deviceConf.getWhiteUsernames();
        if (whiteUsernames != null ? !whiteUsernames.equals(whiteUsernames2) : whiteUsernames2 != null)
        {
            return false;
        }
        whiteUsernames = getBlockUsernames();
        whiteUsernames2 = deviceConf.getBlockUsernames();
        if (whiteUsernames != null ? !whiteUsernames.equals(whiteUsernames2) : whiteUsernames2 != null)
        {
            return false;
        }
        whiteUsernames = getBlockNicknames();
        whiteUsernames2 = deviceConf.getBlockNicknames();
        if (whiteUsernames != null ? !whiteUsernames.equals(whiteUsernames2) : whiteUsernames2 != null)
        {
            return false;
        }
        whiteUsernames = getBlockMsgRegex();
        whiteUsernames2 = deviceConf.getBlockMsgRegex();
        if (whiteUsernames != null ? !whiteUsernames.equals(whiteUsernames2) : whiteUsernames2 != null)
        {
            return false;
        }
        whiteUsernames = getBlockContactCardRegex();
        whiteUsernames2 = deviceConf.getBlockContactCardRegex();
        if (whiteUsernames != null ? !whiteUsernames.equals(whiteUsernames2) : whiteUsernames2 != null)
        {
            return false;
        }
        whiteUsernames = getBlockImgMD5();
        whiteUsernames2 = deviceConf.getBlockImgMD5();
        if (whiteUsernames == null)
        {
            if (whiteUsernames2 == null)
            {
                return true;
            }
        } else if (whiteUsernames.equals(whiteUsernames2))
        {
            return true;
        }
        return false;
    }

    public Integer getAcceptOtherRoomInvite()
    {
        return this.acceptOtherRoomInvite;
    }

    public Set<String> getBlockContactCardRegex()
    {
        return this.blockContactCardRegex;
    }

    public Set<String> getBlockImgMD5()
    {
        return this.blockImgMD5;
    }

    public Set<String> getBlockMsgRegex()
    {
        return this.blockMsgRegex;
    }

    public Set<String> getBlockNicknames()
    {
        return this.blockNicknames;
    }

    public Integer getBlockTextLength()
    {
        return this.blockTextLength;
    }

    public Set<String> getBlockUsernames()
    {
        return this.blockUsernames;
    }

    public Integer getChatroomDownloadPic()
    {
        return this.chatroomDownloadPic;
    }

    public Long getDefSendItr()
    {
        return this.defSendItr;
    }

    public Integer getIndividualDownloadPic()
    {
        return this.individualDownloadPic;
    }

    public Integer getLogFile()
    {
        if (this.logFile == null)
        {
            this.logFile = Integer.valueOf(0);
        }
        return this.logFile;
    }

    public Long getMsgCardSendItr()
    {
        return this.msgCardSendItr;
    }

    public Integer getPickTask()
    {
        if (this.pickTask == null)
        {
            this.pickTask = Integer.valueOf(1);
        }
        return this.pickTask;
    }

    public Integer getUpChatroom()
    {
        if (this.upChatroom == null)
        {
            this.upChatroom = Integer.valueOf(1);
        }
        return this.upChatroom;
    }

    public Integer getUpNormalMsg()
    {
        if (this.upNormalMsg == null)
        {
            this.upNormalMsg = Integer.valueOf(1);
        }
        return this.upNormalMsg;
    }

    public Integer getUpOtherOptMsg()
    {
        if (this.upOtherOptMsg == null)
        {
            this.upOtherOptMsg = Integer.valueOf(1);
        }
        return this.upOtherOptMsg;
    }

    public Integer getUpSelfOptMsg()
    {
        if (this.upSelfOptMsg == null)
        {
            this.upSelfOptMsg = Integer.valueOf(1);
        }
        return this.upSelfOptMsg;
    }

    public Integer getUpSelfSendMsg()
    {
        return this.upSelfSendMsg;
    }

    public Long getVoiceSendItr()
    {
        return this.voiceSendItr;
    }

    public Set<String> getWhiteUsernames()
    {
        return this.whiteUsernames;
    }

    public int hashCode()
    {
        int i = 43;
        Integer chatroomDownloadPic = getChatroomDownloadPic();
        int hashCode = (chatroomDownloadPic == null ? 43 : chatroomDownloadPic.hashCode()) + 59;
        Integer individualDownloadPic = getIndividualDownloadPic();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getAcceptOtherRoomInvite();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getPickTask();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getUpNormalMsg();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getUpOtherOptMsg();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getUpSelfOptMsg();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getUpChatroom();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getUpSelfSendMsg();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        individualDownloadPic = getLogFile();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        Long defSendItr = getDefSendItr();
        hashCode = (defSendItr == null ? 43 : defSendItr.hashCode()) + (hashCode * 59);
        defSendItr = getVoiceSendItr();
        hashCode = (defSendItr == null ? 43 : defSendItr.hashCode()) + (hashCode * 59);
        defSendItr = getMsgCardSendItr();
        hashCode = (defSendItr == null ? 43 : defSendItr.hashCode()) + (hashCode * 59);
        individualDownloadPic = getBlockTextLength();
        hashCode = (individualDownloadPic == null ? 43 : individualDownloadPic.hashCode()) + (hashCode * 59);
        Set whiteUsernames = getWhiteUsernames();
        hashCode = (whiteUsernames == null ? 43 : whiteUsernames.hashCode()) + (hashCode * 59);
        whiteUsernames = getBlockUsernames();
        hashCode = (whiteUsernames == null ? 43 : whiteUsernames.hashCode()) + (hashCode * 59);
        whiteUsernames = getBlockNicknames();
        hashCode = (whiteUsernames == null ? 43 : whiteUsernames.hashCode()) + (hashCode * 59);
        whiteUsernames = getBlockMsgRegex();
        hashCode = (whiteUsernames == null ? 43 : whiteUsernames.hashCode()) + (hashCode * 59);
        whiteUsernames = getBlockContactCardRegex();
        hashCode = (whiteUsernames == null ? 43 : whiteUsernames.hashCode()) + (hashCode * 59);
        whiteUsernames = getBlockImgMD5();
        hashCode *= 59;
        if (whiteUsernames != null)
        {
            i = whiteUsernames.hashCode();
        }
        return hashCode + i;
    }

    public void setAcceptOtherRoomInvite(Integer num)
    {
        this.acceptOtherRoomInvite = num;
    }

    public void setBlockContactCardRegex(Set<String> set)
    {
        this.blockContactCardRegex = set;
    }

    public void setBlockImgMD5(Set<String> set)
    {
        this.blockImgMD5 = set;
    }

    public void setBlockMsgRegex(Set<String> set)
    {
        this.blockMsgRegex = set;
    }

    public void setBlockNicknames(Set<String> set)
    {
        this.blockNicknames = set;
    }

    public void setBlockTextLength(Integer num)
    {
        this.blockTextLength = num;
    }

    public void setBlockUsernames(Set<String> set)
    {
        this.blockUsernames = set;
    }

    public void setChatroomDownloadPic(Integer num)
    {
        this.chatroomDownloadPic = num;
    }

    public void setDefSendItr(Long l)
    {
        this.defSendItr = l;
    }

    public void setIndividualDownloadPic(Integer num)
    {
        this.individualDownloadPic = num;
    }

    public void setLogFile(Integer num)
    {
        this.logFile = num;
    }

    public void setMsgCardSendItr(Long l)
    {
        this.msgCardSendItr = l;
    }

    public void setPickTask(Integer num)
    {
        this.pickTask = num;
    }

    public void setUpChatroom(Integer num)
    {
        this.upChatroom = num;
    }

    public void setUpNormalMsg(Integer num)
    {
        this.upNormalMsg = num;
    }

    public void setUpOtherOptMsg(Integer num)
    {
        this.upOtherOptMsg = num;
    }

    public void setUpSelfOptMsg(Integer num)
    {
        this.upSelfOptMsg = num;
    }

    public void setUpSelfSendMsg(Integer num)
    {
        this.upSelfSendMsg = num;
    }

    public void setVoiceSendItr(Long l)
    {
        this.voiceSendItr = l;
    }

    public void setWhiteUsernames(Set<String> set)
    {
        this.whiteUsernames = set;
    }

    public String toString()
    {
        return "DeviceConf(chatroomDownloadPic=" + getChatroomDownloadPic() + ", individualDownloadPic=" + getIndividualDownloadPic() + ", acceptOtherRoomInvite=" + getAcceptOtherRoomInvite() + ", pickTask=" + getPickTask() + ", upNormalMsg=" + getUpNormalMsg() + ", upOtherOptMsg=" + getUpOtherOptMsg() + ", upSelfOptMsg=" + getUpSelfOptMsg() + ", upChatroom=" + getUpChatroom() + ", upSelfSendMsg=" + getUpSelfSendMsg() + ", logFile=" + getLogFile() + ", defSendItr=" + getDefSendItr() + ", voiceSendItr=" + getVoiceSendItr() + ", msgCardSendItr=" + getMsgCardSendItr() + ", blockTextLength=" + getBlockTextLength() + ", whiteUsernames=" + getWhiteUsernames() + ", blockUsernames=" + getBlockUsernames() + ", blockNicknames=" + getBlockNicknames() + ", blockMsgRegex=" + getBlockMsgRegex() + ", blockContactCardRegex=" + getBlockContactCardRegex() + ", blockImgMD5=" + getBlockImgMD5() + ")";
    }
}
