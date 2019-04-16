package com.warm.entity.robot.responseInfo;


import com.warm.entity.robot.G;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.entity.robot.common.VideoContent;
import com.warm.entity.robot.common.WeChatMsgType;

import java.util.List;


public class SunTaskHelper
{
	// public static SunTask getSunTask(int sunTaskType,int weChatMsgType)
	public static SunTask getTask_personOP(int sunTaskType, List<String> toUsernames, String content_nicknameOrTagOrUrlOrWhatups, String addFriendAlias, String bjOSSKey, String fri)
	{
		SunTask tempSunTask = new SunTask();
		try
		{
			// 根据username判断要返回的任务类型
			tempSunTask.setType(sunTaskType);
			switch (tempSunTask.getType())
			{
			// ==============================================================================================
			// 个人号相关功能
			case SunTaskType.DELETE_FRIEND: // 101 删除好友
											// {"type":3,"toUsernames":["wx_id_xxx"]}
				tempSunTask.setToUsernames(toUsernames);
				break;
			case SunTaskType.ADD_FRIEND: // 102 加好友并发送一条信息
											// {"type":3,"alias":"wx_id_xxx"}
				tempSunTask.setContent(fri);
				tempSunTask.setToUsernames(toUsernames);
				tempSunTask.setAlias(addFriendAlias);
				break;
			case SunTaskType.UPLOAD_FRIEND_LIST: // 103 通知客户端上传好友列表 {"type":103}
				// do nothing
				break;
			case SunTaskType.FRIEND_ACCEPT_REQUEST: // 104 接受好友请求
				tempSunTask.setContent(fri);
				break;
			case SunTaskType.FRIEND_ADD_LABEL: // 105 增加好友标签
				tempSunTask.setContent(content_nicknameOrTagOrUrlOrWhatups); // 微信标签不允许出现标点符号等特殊字符
				tempSunTask.setToUsernames(toUsernames);
				break;
			case SunTaskType.FRIEND_REMOVE_LABEL: // 106 移除好友标签
				tempSunTask.setContent(content_nicknameOrTagOrUrlOrWhatups); // 微信标签不允许出现标点符号等特殊字符
				tempSunTask.setToUsernames(toUsernames);
				break;
			case SunTaskType.ALTER_NICKNAME: // 改变昵称300
				tempSunTask.setContent(content_nicknameOrTagOrUrlOrWhatups);
				break;
			case SunTaskType.ALTER_AVATAR: // 改变头像301// 先把图像存在一个url里
				tempSunTask.setContent(content_nicknameOrTagOrUrlOrWhatups);
				break;
			case SunTaskType.ALTER_SIGNATURE: // 改变签名303
				tempSunTask.setContent(content_nicknameOrTagOrUrlOrWhatups);
				break;
			// ==============================================================================================
			// 没有实现的功能
				/*
			case SunTaskType.UPDATE_SELF_QRCODE: // 107 更新自身二维码
				tempSunTask.setContent(bjOSSKey); // 阿里云oss的一个key，指定要把二维码上传到什么地方
				break;
			case SunTaskType.ADD_FRIEND_BY_CARD: // 108 名片加好友
				fri = new FriendRequestInfo();
				fri.setUsername(toUsernames.get(0));
				fri.setTicket("");
				String json2 = ms_om.writeValueAsString(fri);
				tempSunTask.setContent(json2);
				break;
			case SunTaskType.UPLOAD_FOLLOWED_OFFICAL_ACCOUNTS: // 109 上传跟踪的官方账户
																// //触发uploadFollowedOfficalAccounts
				// do nothing
				break;
			case SunTaskType.FOLLOW_OFFICAL_ACCOUNT: // 110 关注官方账户
				tempSunTask.setContent("官方账户id");
				break;
			case SunTaskType.TIMELINE_NORMAL: // 朋友圈 150 //发布普通朋友圈
				// invalidate
				break;
			case SunTaskType.TIMELINE_LINK: // 151 //发布链接朋友圈
				// invalidate
				break;*/
			default:
				throw new Exception("无法识别的SunTaskType="+tempSunTask.getType());
			}
		}
		catch (Exception e)
		{
			tempSunTask=null;
			G.e(e);
		}
		return tempSunTask;
	}

	public static SunTask getTask_chatroomOP(int sunTaskType, String chatroomWxId, List<String> toUsernames, String content_nameOrNotice)
	{
		SunTask tempSunTask = new SunTask();
		try
		{
			// 根据username判断要返回的任务类型
			tempSunTask.setType(sunTaskType);

			switch (tempSunTask.getType())
			{
				// ============================================================================================
				// 群操作相关功能
				case SunTaskType.CHATROOM_PREPARE: // 11 新建聊天室
					tempSunTask.setContent(content_nameOrNotice); // 群名称
					tempSunTask.setToUsernames(toUsernames); // 新建聊天室要邀请的好友
					break;
				case SunTaskType.CHATROOM_INVITE: // 2 聊天室邀请人入群://
					// {"type":2,"chatroom":"聊天室id","toUsernames":["wx_id_xxx","wx_id_yyy","wx_id_zzz"]}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setToUsernames(toUsernames); // 要邀请的人的wx_id列表
					break;
				case SunTaskType.CHATROOM_KICK_OUT: // 3 聊天室踢出 //
					// {"type":3,"chatroom":"聊天室id","toUsernames":["wx_id_xxx","wx_id_yyy","wx_id_zzz"]}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setToUsernames(toUsernames); // 要踢出的人的wx_id列表
					break;
				case SunTaskType.CHATROOM_TRANSFER_OWNER: // 4 更换聊天室群主 //
					// {"type":3,"chatroom":"聊天室id","toUsernames":["wx_id_xxx"]}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setToUsernames(toUsernames);
					break;
				case SunTaskType.CHATROOM_UPDATE_NOTICE: // 5 聊天室更新公告
					// {"type":3,"chatroom":"聊天室id","content":"公告内容"}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setContent(content_nameOrNotice);
					break;
				case SunTaskType.CHATROOM_UPDATE_NAME: // 6 聊天室改名
					// {"type":3,"chatroom":"聊天室id","content":"聊天室新名称"}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setContent(content_nameOrNotice);
					break;
				case SunTaskType.CHATROOM_LEAVE: // 7 退群
					// {"type":3,"chatroom":"聊天室id"}
					tempSunTask.setChatroom(chatroomWxId);
					break;
				case SunTaskType.CHATROOM_ALTER_SELF_DISPLAYNAME: // 8 聊天室改变自己的昵称
					// {"type":3,"chatroom":"聊天室id","content":"新名称"}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setContent(content_nameOrNotice);
					break;
				case SunTaskType.CHATROOM_UPDATE_QRCODE_REL: // 12 聊天室更新二维码 //未实现
					tempSunTask.setChatroom(chatroomWxId);
					break;
				case SunTaskType.CHATROOM_LEAVE_ALL: // 13 群主退群
					// {"type":3,"chatroom":"聊天室id"}
					tempSunTask.setChatroom(chatroomWxId);
					break;
				case SunTaskType.CHATROOM_OPEN_VERIFY_INVITATION: // 14 聊天室打开邀请验证
					// {"type":3,"chatroom":"聊天室id"}
					tempSunTask.setChatroom(chatroomWxId);
					break;
				case SunTaskType.CHATROOM_CLOSE_VERIFY_INVITATION: // 15 聊天室关闭邀请校验
					// {"type":3,"chatroom":"聊天室id"}
					tempSunTask.setChatroom(chatroomWxId);
					break;
				case SunTaskType.CHATROOM_ADD_ADMIN: // 17 聊天室增加管理员
					// //{"type":3,"chatroom":"聊天室id","toUsernames":["wx_id_xxx"]}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setToUsernames(toUsernames);
					break;
				case SunTaskType.CHATROOM_REMOVE_ADMIN: // 18 聊天室移除管理员 //
					// {"type":3,"chatroom":"聊天室id","toUsernames":["wx_id_xxx"]}
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setToUsernames(toUsernames);
					break;
				case SunTaskType.CHATROOM_INVITE_LT40_DIRECT: // 1002 聊天室邀请
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setToUsernames(toUsernames);
					break;
				case SunTaskType.CHATROOM_INVITE_GT40_DIRECT: // 聊天室邀请1102
					tempSunTask.setChatroom(chatroomWxId);
					tempSunTask.setToUsernames(toUsernames);
					break;

				// ==============================================================================================
				// 无法识别的功能
				/*
			case SunTaskType.CHATROOM_CALLBACK_INVITE: // 10 聊天室回调邀请
				tempSunTask.setChatroom(chatroomWxId);
				break;
			case SunTaskType.CHATROOM_DISABLE_QRCODE: // 16 聊天室禁用二维码
														// {"type":3,"chatroom":"聊天室id","content":""}
				tempSunTask.setChatroom(chatroomWxId);
				tempSunTask.setContent(""); // 不知道是什么意思
				break;
				*/
				default:
					throw new Exception("无法识别的SunTaskType="+tempSunTask.getType());
			}
		}
		catch (Exception e)
		{
			tempSunTask=null;
		}
		return tempSunTask;
	}

	public static SunTask getTask_sendMsg(int sunTaskType, Integer weChatMsgType, String chatroomWxid, List<String> toUsernames, String content, String videoThumbUrl, Integer videoLength, String videoUrl)
	{
		//
		SunTask tempSunTask = new SunTask();
		try
		{
			// 根据username判断要返回的任务类型
			tempSunTask.setType(sunTaskType);
			tempSunTask.setWeChatMsgType(weChatMsgType); // 如果是发送信息任务设置此项,如果不是就不用设置了.//
															// 发送信息任务的处理,在switch的最后
			switch (tempSunTask.getType())
			{
			// ==============================================================================================
			// 发送信息相关功能
			case SunTaskType.FRIEND_SEND_MSG:
				tempSunTask.setToUsernames(toUsernames);
				break;
			case SunTaskType.CHATROOM_SEND_MSG:
				tempSunTask.setChatroom(chatroomWxid);
				break;

			default:
				throw new Exception("无法识别的SunTaskType="+tempSunTask.getType());
			}
			
			switch (tempSunTask.getWeChatMsgType())
			{
			case WeChatMsgType.WECHAT_MESSAGE_TYPE_TEXT:
				tempSunTask.setContent(content); // "要发送的文本内容"
				break;
			case WeChatMsgType.WECHAT_MESSAGE_TYPE_IMG:
				tempSunTask.setContent(content);// "要发送的图片的URL"
				break;
			case WeChatMsgType.WECHAT_MESSAGE_TYPE_VOICE:
				tempSunTask.setContent(content);// "要发送的语音的URL"
				break;
			case WeChatMsgType.WECHAT_MESSAGE_TYPE_CONTACT_CARD:
				tempSunTask.setContent(content);// "要发送的名片的内容"
				break;
			case WeChatMsgType.WECHAT_MESSAGE_TYPE_VIDEO:
				VideoContent tempVideoContent = new VideoContent(videoUrl, videoThumbUrl, videoLength); // "视频URL"
																										// "预览图URL"
																										// 视频尺寸
				tempSunTask.setContent(G.ms_om.writeValueAsString(tempVideoContent));
				break;
			case WeChatMsgType.WECHAT_MESSAGE_TYPE_MSG_CARD:
				tempSunTask.setContent(content);
				break;
			default:
				throw new Exception("无法识别的WeChatMsgType="+tempSunTask.getWeChatMsgType());
			}
		}
		catch (Exception e)
		{
			tempSunTask=null;
			G.e(e);
		}
		return tempSunTask;
	}

	public static SunTask getTask_sysOP(Integer sunTaskType)
	{
		//
		SunTask tempSunTask = new SunTask();
		try
		{
			// 根据username判断要返回的任务类型
			tempSunTask.setType(sunTaskType);

			switch (tempSunTask.getType())
			{
			// ==============================================================================================
			// 系统功能
			case SunTaskType.UPDATE_IP: // 200 更新ip//触发updateRobotIp.do
				// do nothing
				break;
			case SunTaskType.CLEAR_CHAT_MSG: // 清除聊天信息 201
				// do nothing
				break;
			case SunTaskType.REBOOT: // 重启 202
				// do nothing
				break;
			case SunTaskType.SYNC_BASIC_INFO: // 同步基本信息 203//同时触发
												// /robot/updateBasicInfo.do
												// /robot/updateWeChatInfo.do
				// do nothing
				break;
			case SunTaskType.SYNC_CONF: // 同步配置204//触发/robot/queryConf.do
				// do nothing
				break;
			default:
				throw new Exception("无法识别的SunTaskType="+tempSunTask.getType());
			}
		}
		catch (Exception e)
		{
			tempSunTask=null;
			G.e(e);
		}
		return tempSunTask;
	}

}
