package com.dcit.provider;

import com.alibaba.fastjson.JSON;
import com.dcit.dto.AccessTokenDTO;
import com.dcit.dto.GithubUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by lhy on 2019/8/1.
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
            try (Response response = client.newCall(request).execute()) {
                String string=response.toString();
                System.out.print("string值"+string);
                return string;
            } catch (IOException e) {
                e.printStackTrace();

        }
        return  null;
    }

    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
         Response response = client.newCall(request).execute();
         String string =response.body().string();
         GithubUser githubUser= JSON.parseObject(string,GithubUser.class);
         return  githubUser;
        }
}
