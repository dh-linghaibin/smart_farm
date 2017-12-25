package com.jt28.a6735.mirro.weather;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a6735 on 2017/7/14.
 */

public class Constant {
    public final static int MSG_ERROR = 0;
    public final static int MSG_SUCCESS = 1;
    public static final String URL_WEATHER_2345 =
            "http://tianqi.2345.com/t/new_mobile_json/%s.json";
    public static final String URL_FORECAST_FLYME =
            "http://res.aider.meizu.com/1.0/weather/%s.json";
    public final static Map<String, Integer> ZHISHU = new HashMap();
}
