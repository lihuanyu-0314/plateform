package com.dcit.controller;

import com.dcit.dto.AccessTokenDTO;
import com.dcit.dto.GithubUser;
import com.dcit.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by lhy on 2019/8/1.
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private  String clientId;

    @Value("${github.clent.secret}")
    private  String clientSecret;

    @Value("${github.redirect.uri}")
    private  String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")  String code,
                           @RequestParam(name="state") String state) throws IOException {
        System.out.print(code);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user=githubProvider.getUser(accessToken);
        return "index";
}
}
