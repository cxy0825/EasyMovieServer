package com.cxy.Utils;

import cn.hutool.json.JSONObject;
import org.springframework.web.client.RestTemplate;


public class DingDingUtil {


    public boolean sendMsg(String msg) {
        RestTemplate restTemplate = new RestTemplate();
        msg = "EM\n" + msg;
        String url = "https://oapi.dingtalk.com/robot/send?access_token=8a3fd0d74366f1fe5616a6043b7cefb686be69f76db57152703a655cd9c08ee3";
        JSONObject postData = new JSONObject();
        JSONObject text = new JSONObject();
        text.set("content", msg);
        postData.set("msgtype", "text");
        postData.set("text", text);
//        System.out.println(postData.toStringPretty());
        JSONObject body = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();

        return true;
    }
}
