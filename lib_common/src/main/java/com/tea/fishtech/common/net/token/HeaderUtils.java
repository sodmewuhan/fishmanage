package com.tea.fishtech.common.net.token;

import com.google.common.collect.Maps;

import java.util.Map;

public class HeaderUtils {

    public Map<String, String> createBearerToken(String token) {
        Map<String, String> ret = Maps.newHashMap();
        ret.put("Authorization","Bearer " + token);
        return ret;
    }
}
