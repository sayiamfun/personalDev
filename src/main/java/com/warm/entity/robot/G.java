package com.warm.entity.robot;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class G
{
	// 基础设置
	public static boolean ms_isDeubgVersion;		// = true;
	public static String ms_baseUrl;		// = "";

	public static int ms_OPERATION_PROJECT_INSTANCE_ID = 0;		//=4;
	public static int ms_SERVER_PORT;
	public static String ms_FILE_SERVER_URL;		//="http://www.youyoudk.cn:18888";
	public static String ms_FILE_SERVER_LOCAL_DIR;		//="C:/Users/Administrator/Desktop/AI/apache-tomcat-8.5.37/webapps/ROOT";

	
	public static String ms_currProjectInstanceName;		// = null;
	public static List<String> ms_phoneList;		// = Arrays.asList("17319402380", "17310011324", "18513668642", "17073549676");

	// 微信公众号配置
	public static String WX_APPID = "wx8e17aa77af6c4ae3";		// = "wx8e17aa77af6c4ae3";
	public static String WX_SECRET = "702c3bfc75d8bed0bfe105679513c7d0";   // = "702c3bfc75d8bed0bfe105679513c7d0";
	public static String WX_GRANT_TYPE = "authorization_code";		// = "authorization_code";


	// 日志控制——机器人
	public static boolean ms_IS_LOG_ROBOT_INFO_TO_DB;		// = true;
	public static boolean ms_IS_LOG_ROBOT_INFO_TO_FILE;		// = false;

	// 日志控制——HTTP请求
	public static List<String> ms_LOG_BEFORE_IGNORE_REQUEST_LIST;		// = Arrays.asList("/robot/writePrismRecord.do");
	public static List<String> ms_LOG_AFTER_IGNORE_REQUEST_LIST;		// = Arrays.asList("/robot/writePrismRecord.do");

	public static boolean ms_IS_LOG_EMPTY_PICKUP_REQUEST;		// = false;
	public static boolean ms_IS_LOG_BEFORE_REQUEST_TO_DB;		// = false;
	public static boolean ms_IS_LOG_AFTER_REQUEST_TO_DB;		// = true;
	public static boolean ms_IS_LOG_REQUEST_TO_CONSOLE;		// = false;
	public static boolean ms_IS_LOG_REQUEST_TO_FILE;		// = false;
	public static String ms_LOG_REQUEST_FILE_NAME;		// = "./log_request.txt";

	// 运行时状态辅助
	public static HashMap<String, Integer> ms_robotStatusCodeMap = new HashMap<String, Integer>();
	public static Integer ms_ROBOT_STATUS_START_CODE = 10000;

	// 全局工具对象
	public static ObjectMapper ms_om = new ObjectMapper(); // 对象转json字符串


	static
	{
		if (ms_isDeubgVersion)
		{
			// G.TIMED_TASK_THREAD_SLEEP_TIME=10*1000;
		}

	}

	public static void e(Exception e)
	{
		System.out.println("捕捉到异常！" + e.getMessage());
		e.printStackTrace();
	}
	//取request中body的数据
	public static String ReadAsChars(HttpServletRequest request)
	{

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder("");
		try
		{
			br = request.getReader();
			String str;
			while ((str = br.readLine()) != null)
			{
				sb.append(str);
			}
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (null != br)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}


	public static void i(int logicId, String s, String s1) {
		System.err.println(logicId + ":" + s + ":" + s1);
	}
}
