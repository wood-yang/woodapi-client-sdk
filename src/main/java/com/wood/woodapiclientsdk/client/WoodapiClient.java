package com.wood.woodapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
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

    private static final String EXTRA_BODY = "userInfoWoodAPI";

    private static final String GATEWAY_HOST = "http://localhost:8091";// 请求网关，网关再将请求转发到真实接口地址


    public WoodapiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 随机获取一句毒鸡汤
     * @return
     */
    public String getPoisonousChickenSoup() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/poisonousChickenSoup")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机壁纸
     * @return
     */
    public String getRandomWallpaper() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/bing")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机土味情话
     * @return
     */
    public String getLoveTalk() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/text/love")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 每日一句励志英语
     * @return
     */
    public String getDailyEnglish() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/dailyEnglish")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机笑话
     * @return
     */
    public String getRandomJoke() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/text/joke")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机笑话
     * @return
     */
    public String getWeather() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/weather")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

//    public HttpResponse getTest() {
//        HttpResponse response = HttpRequest.get("http://localhost:8091/api/name/test")
//                .header("X-Redirect-Uri", "http://localhost:8123")
//                .execute();
//        if (response.getStatus() != HttpStatus.HTTP_OK) {
//            throw new RuntimeException();
//        }
//        System.out.println(response.body());
//
//        return response;
//    }

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
