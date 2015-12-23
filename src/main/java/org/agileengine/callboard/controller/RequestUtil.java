package org.agileengine.callboard.controller;

import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    public static Map<String, String> getParamMap(String query) {
        Map<String, String> map = new HashMap<>();
        if(Strings.isNullOrEmpty(query)) return map;
        try {
            query = java.net.URLDecoder.decode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return map;
        }

        String[] params = query.split("&");
        for (String param : params)
        {
            int separatorIndex = param.indexOf("=");
            if(separatorIndex != -1) {
                String key = param.substring(0,separatorIndex);
                if(param.length() > separatorIndex + 1) {
                    String value = param.substring(separatorIndex + 1);
                    map.put(key, value);
                }
            }
        }
        return map;
    }
}
