package com.warm.entity.robot.common;

public class SunTaskType
{
    public static final int FRIEND_SEND_MSG = 100;          //给好友发信息
    public static final int DELETE_FRIEND = 101;        //删除好友
    public static final int ADD_FRIEND = 102;       //加好友
    public static final int UPLOAD_FRIEND_LIST = 103;       //上传好友列表
    public static final int FRIEND_ACCEPT_REQUEST = 104;       //接受好友请求
    public static final int FRIEND_ADD_LABEL = 105;         //增加好友标签
    public static final int FRIEND_REMOVE_LABEL = 106;      //移除好友标签
    public static final int UPDATE_SELF_QRCODE = 107;   //更新自身二维码
    public static final int ADD_FRIEND_BY_CARD = 108;   //名片加好友
    public static final int UPLOAD_FOLLOWED_OFFICAL_ACCOUNTS = 109; //上传跟踪的官方账户
    public static final int FOLLOW_OFFICAL_ACCOUNT = 110;   //跟踪官方账户

    public static final int CHATROOM_SEND_MSG = 1;      //聊天室发送信息
    public static final int CHATROOM_INVITE = 2;        //聊天室邀请
    public static final int CHATROOM_KICK_OUT = 3;      //聊天室踢出
    public static final int CHATROOM_TRANSFER_OWNER = 4;    //报告聊天室所有者
    public static final int CHATROOM_UPDATE_NOTICE = 5;     //聊天室更新公告
    public static final int CHATROOM_UPDATE_NAME = 6;       //聊天室改名
    public static final int CHATROOM_LEAVE = 7;         //聊天室离开
    public static final int CHATROOM_ALTER_SELF_DISPLAYNAME = 8;    //聊天室改变自己的昵称
    public static final int CHATROOM_CALLBACK_INVITE = 10;      //聊天室回调邀请
    public static final int CHATROOM_PREPARE = 11;      //建造聊天室
    public static final int CHATROOM_UPDATE_QRCODE_REL = 12;    //聊天室更新二维码
    public static final int CHATROOM_LEAVE_ALL = 13;        //聊天室离开所有
    public static final int CHATROOM_OPEN_VERIFY_INVITATION = 14;   //聊天室打开邀请验证
    public static final int CHATROOM_CLOSE_VERIFY_INVITATION = 15;  //聊天室关闭邀请校验
    public static final int CHATROOM_DISABLE_QRCODE = 16;       //聊天室禁用二维码
    public static final int CHATROOM_ADD_ADMIN = 17;    //聊天室增加管理员
    public static final int CHATROOM_REMOVE_ADMIN = 18; //聊天室移除管理员

    public static final int TIMELINE_NORMAL = 150;  //时间线普通???
    public static final int TIMELINE_LINK = 151;    //时间线链接???

    public static final int UPDATE_IP = 200;        //更新ip
    public static final int CLEAR_CHAT_MSG = 201;       //清除聊天信息
    public static final int REBOOT = 202;           //重启
    public static final int SYNC_BASIC_INFO = 203;  //同步基本信息
    public static final int SYNC_CONF = 204;        //同步配置

    public static final int ALTER_NICKNAME = 300;   //改变昵称
    public static final int ALTER_AVATAR = 301;     //改变化身
    public static final int CLOSE_SILENT_DOWNLOAD = 302;    //关闭静默下载
    public static final int ALTER_SIGNATURE = 303;  //改变签名

    public static final int CHATROOM_INVITE_LT40_DIRECT = 1002; //聊天室邀请
    public static final int CHATROOM_INVITE_GT40_DIRECT = 1102; //聊天室邀请


}
