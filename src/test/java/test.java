import com.warm.utils.HttpClientUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class test {
    public static void main(String[] args) {
//        String temp = "0/2";
//        String[] split = temp.split("/");
//        System.err.println(split.length);
//        System.err.println(split[0] + split[1]);
        String redirect_uri_encode = "";
        String cmd2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8e17aa77af6c4ae3&redirect_uri=http://www.jiazhang111.xyz/taskCallback&response_type=code&scope=snsapi_userinfo&state=0&connect_redirect=1#wechat_redirect";
        try {
            redirect_uri_encode = URLEncoder.encode(cmd2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String s = HttpClientUtil.sendGet("https://12i.cn/api.ashx?format=txt&userId=4590&key=AF5A09C5C41AF4625087B47224381808&url="+redirect_uri_encode);
        System.err.println(s);
    }
}
