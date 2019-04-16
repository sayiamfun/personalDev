package com.warm.entity.robot.responseInfo;



import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SunApiResponse implements Serializable
{
    protected static final Map<Integer, String> CODE_2_MSG = new HashMap();
    public static final int CODE_BIZ_ERROR = -2;
    public static final int CODE_CUSTOM_ERROR = -4;
    public static final int CODE_DEPRECATED_FUNCTION = -9;
    public static final int CODE_DUPLICATED_RESOURCE = -10;
    public static final int CODE_GENERATE_CLICKID_FAIL = -12;
    public static final int CODE_IMG_UPLOAD_ERROR = -13;
    public static final int CODE_INVALID_IP_ERROR = -7;
    public static final int CODE_NOT_EXIST_X_FRIEND = -8;
    public static final int CODE_NOT_OWN_CHATROOM_ERROR = -6;
    public static final int CODE_PARAM_ERROR = -1;
    public static final int CODE_RESOURCE_LOCKED = -11;
    public static final int CODE_RESOURCE_NOT_ENOUGH_ERROR = -5;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_SYS_ERROR = -3;
    
    public static final int CODE_UNREALIZATION=999;
    
    public static final String MSG_BIZ_ERROR = "biz error";
    public static final String MSG_CUSTOM_ERROR = "custom error";
    public static final String MSG_DEPRECATED_FUNCTION = "deprecated function";
    public static final String MSG_DUPLICATED_RESOURCE = "duplicated resource";
    public static final String MSG_GENERATE_CLICKID_FAIL = "generate clickgod clickId failed ";
    public static final String MSG_IMG_UPLOAD_ERROR = "OSS upload image fail ";
    public static final String MSG_INVALID_IP_ERROR = "invalid ip address";
    public static final String MSG_NOT_EXIST_X_FRIEND = "not exist x friend";
    public static final String MSG_NOT_OWN_CHATROOM_ERROR = "not own chatroom error";
    public static final String MSG_PARAM_ERROR = "param error";
    public static final String MSG_RESOURCE_LOCKED = "resource is locked.";
    public static final String MSG_RESOURCE_NOT_ENOUGH_ERROR = "resource not enough error";
    public static final String MSG_SUCCESS = "success";
    public static final String MSG_SYS_ERROR = "sys error";
    private static final long serialVersionUID = 1792153349798294408L;
    private Integer code;
    private Object data;
    private String msg;

    static
    {
        CODE_2_MSG.put(Integer.valueOf(0), MSG_SUCCESS);
        CODE_2_MSG.put(Integer.valueOf(-1), MSG_PARAM_ERROR);
        CODE_2_MSG.put(Integer.valueOf(-2), MSG_BIZ_ERROR);
        CODE_2_MSG.put(Integer.valueOf(-3), MSG_SYS_ERROR);
        CODE_2_MSG.put(Integer.valueOf(-4), MSG_CUSTOM_ERROR);
        CODE_2_MSG.put(Integer.valueOf(-5), MSG_RESOURCE_NOT_ENOUGH_ERROR);
        CODE_2_MSG.put(Integer.valueOf(-6), MSG_NOT_OWN_CHATROOM_ERROR);
        CODE_2_MSG.put(Integer.valueOf(-7), MSG_INVALID_IP_ERROR);
        CODE_2_MSG.put(Integer.valueOf(-8), MSG_NOT_EXIST_X_FRIEND);
        CODE_2_MSG.put(Integer.valueOf(-9), MSG_DEPRECATED_FUNCTION);
        CODE_2_MSG.put(Integer.valueOf(-10), MSG_DUPLICATED_RESOURCE);
        CODE_2_MSG.put(Integer.valueOf(-11), MSG_RESOURCE_LOCKED);
        CODE_2_MSG.put(Integer.valueOf(-12), MSG_GENERATE_CLICKID_FAIL);
    }

    public static SunApiResponse bizErrorResponse()
    {
        return createResponse(-2);
    }

    public static SunApiResponse bizErrorResponse(String str)
    {
        return createResponse(-2, StringUtils.isEmpty(str) ? (String) CODE_2_MSG.get(Integer.valueOf(-2)) : str);
    }

    public static SunApiResponse createResponse(int i)
    {
        return createResponse(i, (String) CODE_2_MSG.get(Integer.valueOf(i)));
    }

    public static SunApiResponse createResponse(int i, String str)
    {
        SunApiResponse sunApiResponse = new SunApiResponse();
        sunApiResponse.setCode(Integer.valueOf(i));
        if (StringUtils.isNotBlank(str))
        {
            sunApiResponse.setMsg(str);
        } else
        {
            sunApiResponse.setMsg((String) CODE_2_MSG.get(Integer.valueOf(i)));
        }
        sunApiResponse.setData(null);
        return sunApiResponse;
    }

    public static SunApiResponse paramErrorResponse()
    {
        return createResponse(-1);
    }

    public static SunApiResponse paramErrorResponse(String str)
    {
        return createResponse(-1, StringUtils.isEmpty(str) ? (String) CODE_2_MSG.get(Integer.valueOf(-1)) : str);
    }

    public static SunApiResponse successResponse()
    {
        return createResponse(0);
    }

    public static SunApiResponse successResponse(String str)
    {
        return createResponse(0, StringUtils.isEmpty(str) ? (String) CODE_2_MSG.get(Integer.valueOf(0)) : str);
    }

    protected boolean canEqual(Object obj)
    {
        return obj instanceof SunApiResponse;
    }

    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (!(obj instanceof SunApiResponse))
        {
            return false;
        }
        SunApiResponse sunApiResponse = (SunApiResponse) obj;
        if (!sunApiResponse.canEqual(this))
        {
            return false;
        }
        Integer code = getCode();
        Integer code2 = sunApiResponse.getCode();
        if (code != null ? !code.equals(code2) : code2 != null)
        {
            return false;
        }
        String msg = getMsg();
        String msg2 = sunApiResponse.getMsg();
        if (msg != null ? !msg.equals(msg2) : msg2 != null)
        {
            return false;
        }
        Object data = getData();
        Object data2 = sunApiResponse.getData();
        if (data == null)
        {
            if (data2 == null)
            {
                return true;
            }
        } else if (data.equals(data2))
        {
            return true;
        }
        return false;
    }

    public Integer getCode()
    {
        return this.code;
    }

    public Object getData()
    {
        return this.data;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public int hashCode()
    {
        int i = 43;
        Integer code = getCode();
        int hashCode = (code == null ? 43 : code.hashCode()) + 59;
        String msg = getMsg();
        hashCode = (msg == null ? 43 : msg.hashCode()) + (hashCode * 59);
        Object data = getData();
        hashCode *= 59;
        if (data != null)
        {
            i = data.hashCode();
        }
        return hashCode + i;
    }

    public boolean isCodeSuccess()
    {
        return Objects.equals(Integer.valueOf(0), this.code);
    }

    public void setCode(Integer num)
    {
        this.code = num;
    }

    public void setData(Object obj)
    {
        this.data = obj;
    }

    public void setMsg(String str)
    {
        this.msg = str;
    }

    public String toString()
    {
        return "SunApiResponse(code=" + getCode() + ", msg=" + getMsg() + ", data=" + getData() + ")";
    }
}
