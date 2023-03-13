package cn.tedu.mall.paypal;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName URLUtils
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/28、上午11:26
 */
@Slf4j
public class URLUtils {

    /**
     * 分析URL
     * @param request
     * @return
     */
    public static String getBaseURl(HttpServletRequest request) {

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url =  new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if(url.toString().endsWith("/")){
            url.append("/");
        }
        return url.toString();
    }

}