package com.warm.entity.robot.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class PrismRecordType
{
	public static final int CHAT_MSG = 1;
	public static final int JOIN_CHATROOM = 2;
	public static final int TRANSFER_OWNER = 4;
	public static final int INITIATIVE_LEAVE_CHATROOM = 5;
	public static final int PASSIVITY_LEAVE_CHATROOM = 6;
	public static final int UPDATE_CHATROOM_QRCODE = 7;
	public static final int KICK_OUT_MEMBER = 8;
	public static final int ADDED_FRIEND = 9;
	public static final int MODIFY_CHATROOM_NICKNAME = 10;
	
	public static final int MODIFY_SELF_DISPLAYNAME = 11;
	public static final int CHATROOM_NEW_USERNAME = 12;
	public static final int CHATROOM_PREPARE = 14;
	public static final int CHATROOM_QRCODE_DELETED = 15;
	@Deprecated
	public static final int JOIN_CHATROOM_V2 = 16;
	public static final int CHATROOM_OPEN_VERIFY_INVITATION = 17;
	public static final int CHATROOM_CLOSE_VERIFY_INVITATION = 18;
	public static final int CHATROOM_INVITATION_VERIFY_NOTIFY = 19;
	public static final int CHATROOM_ONLY_OWNER_CAN_ALTER_NAME = 20;
	
	public static final int KICK_BLOCK_USER = 21;
	public static final int ADD_OTHER_SUCCESS = 22;
	public static final int CHATROOM_ADD_ADMIN = 23;
	public static final int CHATROOM_REMOVE_ADMIN = 24;
	
	public static final int FRIEND_BE_DELETED = 40;
	public static final int INITIATIVE_DELETE_FRIEND = 41;
	public static final int NEW_FRIEND_REQUEST = 42;
	public static final int FRIEND_BE_DELETED_B = 43;
	public static final int FRIEND_BE_REJECTED = 44;
	public static final int FRIEND_BE_DECLINED = 45;
	
	public static final int WARNING_OTHER_ACCOUNT = 50;
	public static final int DIALOG_MESSAGE = 51;
	public static final int ACCEPT_FRIEND_TOOOFTEN = 52;
	public static final int USER_BE_BLOCKED = 53;
	public static final int MODIFY_AVATAR = 54;
	public static final int LOGOUT = 88;
	public static final int OTHER_OPT = 99;
	public static final int FRIEND_CHAT_MSG = 100;
	public static final int TASK_FINISHED = 101;
	public static final int ROBOT_SAID = 102;
	public static final int NEW_INDIVIDUAL_ROBOT = 200;
	
	
	public static final Set<Integer> TYPE_SET_ACT = Collections.unmodifiableSet(new HashSet<Integer>()
	{
		private static final long serialVersionUID = 493594109145309105L;
		
		{
			add(Integer.valueOf(2));
			add(Integer.valueOf(4));
			add(Integer.valueOf(5));
			add(Integer.valueOf(6));
			add(Integer.valueOf(9));
			add(Integer.valueOf(14));
			add(Integer.valueOf(40));
			add(Integer.valueOf(51));
			add(Integer.valueOf(102));
			add(Integer.valueOf(41));
			add(Integer.valueOf(18));
			add(Integer.valueOf(17));
			add(Integer.valueOf(52));
			add(Integer.valueOf(88));
			add(Integer.valueOf(22));
			add(Integer.valueOf(54));
		}
	});
	
	public static final Set<Integer> TYPE_SET_ALL = Collections.unmodifiableSet(new HashSet<Integer>()
	{
		private static final long serialVersionUID = -4796584991946825445L;
		
		{
			add(Integer.valueOf(1));
			add(Integer.valueOf(2));
			add(Integer.valueOf(4));
			add(Integer.valueOf(5));
			add(Integer.valueOf(6));
			add(Integer.valueOf(7));
			add(Integer.valueOf(8));
			add(Integer.valueOf(9));
			add(Integer.valueOf(10));
			add(Integer.valueOf(100));
			add(Integer.valueOf(101));
			add(Integer.valueOf(11));
			add(Integer.valueOf(12));
			add(Integer.valueOf(102));
			add(Integer.valueOf(50));
			add(Integer.valueOf(51));
			add(Integer.valueOf(40));
			add(Integer.valueOf(14));
			add(Integer.valueOf(15));
			add(Integer.valueOf(41));
			add(Integer.valueOf(99));
			add(Integer.valueOf(42));
			add(Integer.valueOf(17));
			add(Integer.valueOf(18));
			add(Integer.valueOf(52));
			add(Integer.valueOf(200));
			add(Integer.valueOf(43));
			add(Integer.valueOf(19));
			add(Integer.valueOf(44));
			add(Integer.valueOf(45));
			add(Integer.valueOf(53));
			add(Integer.valueOf(54));
			add(Integer.valueOf(20));
			add(Integer.valueOf(21));
			add(Integer.valueOf(88));
			add(Integer.valueOf(22));
			add(Integer.valueOf(23));
			add(Integer.valueOf(24));
		}
	});
	
	public static final Set<Integer> TYPE_SET_REPORT_REPEATEDLY = Collections.unmodifiableSet(new HashSet<Integer>()
	{
		private static final long serialVersionUID = -8831701376205760436L;
		
		{
			add(Integer.valueOf(1));
			add(Integer.valueOf(2));
			add(Integer.valueOf(10));
			add(Integer.valueOf(12));
		}
	});
	
	public static final Set<Integer> TYPE_SET_TASK_CALLBACK = Collections.unmodifiableSet(new HashSet<Integer>()
	{
		private static final long serialVersionUID = 7393794954621619225L;
		
		{
			add(Integer.valueOf(2));
			add(Integer.valueOf(4));
		}
	});
	public static final Set<Integer> WECHAT_RECEIVE_MSG_TYPE = Collections.unmodifiableSet(new HashSet<Integer>()
	{
		private static final long serialVersionUID = -8831701376205760436L;
		
		{
			add(Integer.valueOf(1));
			add(Integer.valueOf(100));
			add(Integer.valueOf(2));
			add(Integer.valueOf(4));
			add(Integer.valueOf(6));
			add(Integer.valueOf(10));
			add(Integer.valueOf(17));
			add(Integer.valueOf(18));
			add(Integer.valueOf(12));
			add(Integer.valueOf(22));
		}
	});
}
