package com.wood.woodapiclientsdk.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wood.woodapiclientsdk.model.User;
import com.wood.woodapiclientsdk.utils.SignUtils;

import java.util.HashMap;

/**
 * 调用第三方接口的客户端
 */

public class WoodapiClient {

    private String accessKey;

    private String secretKey;

    public WoodapiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        String result1 = HttpUtil.get("localhost:8123/api/name?name=" + name);

        // 当无法识别页面编码的时候，可以自定义请求页面的编码
        String result2 = HttpUtil.get("localhost:8123/api/name" + name, CharsetUtil.CHARSET_UTF_8);

        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "卢本伟");
        String result3 = HttpUtil.get("localhost:8123/api/name", paramMap);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

        return result3;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.post("localhost:8123/api/name", paramMap);
        System.out.println(result);

        return result;
    }

    public String getUsernameByPost(User user) {
        String jsonStr = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post("localhost:8123/api/name/user")
                .addHeaders(getHeaderMap(jsonStr))
                .body(jsonStr).
                execute();
        System.out.println(response.body());

        return response.body();
    }

    private HashMap<String, String> getHeaderMap(String body) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 一定不要直接发送
//        hashMap.put("secretKey", this.secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.getSign(body, secretKey));
        return hashMap;
    }
}
