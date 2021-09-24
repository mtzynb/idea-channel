package com.rahand.idea.channel.utils;

import com.rahand.idea.channel.enums.ChannelRequestHeaderKeys;
import com.rahand.idea.channel.exception.ChannelRequestException;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aglitchyy
 * @created 24/09/2021
 */

public class WebUtils {
    private static Logger logger = LogUtils.getLogger();

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public static void validateChannelReqHeaders(Map<String, String> reqHeaders) {

        logger.info("****** In validateChannelReqHeaders() with reqHeader : {} ****** ", reqHeaders.toString());

        if (!reqHeaders.containsKey(ChannelRequestHeaderKeys.BANK)) {
            throw new ChannelRequestException("Request Header should contain '" + ChannelRequestHeaderKeys.BANK + "'");
        }
        logger.info("****** End validateChannelReqHeaders() successfully ******");

    }

}
