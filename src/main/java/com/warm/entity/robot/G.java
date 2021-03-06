package com.warm.entity.robot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warm.system.entity.PersonalNoRequestException;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.JsonObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class G
{
	// 基础设置
	public static boolean ms_isDeubgVersion;		// = true;
	public static String ms_baseUrl;		// = "";

	public static int ms_OPERATION_PROJECT_INSTANCE_ID = 8;
	public static String ms_SERVER_PORT = "http://www.jiazhang111.xyz";
	public static final String ADDLOGADDRESS = "http://www.jiazhang111.xyz/personalNoLogInfo/addLog";
	public static String ms_FILE_SERVER_URL;
	public static String ms_FILE_SERVER_LOCAL_DIR;

	
	public static String ms_currProjectInstanceName;
	public static List<String> ms_phoneList;

	// 微信公众号配置
	public static String WX_APPID = "wx8e17aa77af6c4ae3";
	public static String WX_SECRET = "702c3bfc75d8bed0bfe105679513c7d0";
	public static String WX_GRANT_TYPE = "authorization_code";


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

	public static void i(int logicId, String s, String s1) {
		System.err.println(logicId + ":" + s + ":" + s1);
	}

	public static void requestException(String dbRequestException, PersonalNoRequestExceptionService requestExceptionService, HttpServletRequest request, String requestBody, String remarks, String responseBody, Integer code,Exception e) {
		try {
			PersonalNoRequestException exception = new PersonalNoRequestException();
			exception.setMethod(request.getMethod());
			exception.setCreateTime(new Date());
			exception.setUrl(request.getRequestURI());
			exception.setStatusCode(code);
			exception.setRequestBody(requestBody);
			exception.setResponseBody(responseBody);
			exception.setRemarks(remarks);
			exception.setException(getErrorInfoFromException(e));
			exception.setDb(dbRequestException);
			requestExceptionService.add(exception);
		}catch (Exception ex){
			System.err.println("插入异常失败");
		}
	}

	private static String getErrorInfoFromException(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e2) {
			return "bad getErrorInfoFromException";
		}
	}
}
