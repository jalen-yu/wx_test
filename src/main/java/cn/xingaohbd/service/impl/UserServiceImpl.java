package cn.xingaohbd.service.impl;

import cn.xingaohbd.service.UserService;
import cn.xingaohbd.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String login(String code){
        Map<String,String> map = new HashMap<>();
        map.put("appid","wxe2280669db1bbe5d");
        map.put("secret","58a28b04acdeb03280b221f6478da624");
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?";
        return HttpClientUtil.doGet(url,map,"utf-8");
    }

}
