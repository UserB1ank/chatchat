package com.example.chatchat.service.CRUD;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import com.example.chatchat.data.mysql.repository.UserRepository;
import com.example.chatchat.data.neo4j.repository.UserRepositoryNeo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepositoryMysql;
    @Autowired
    public UserRepositoryNeo4j userRepositoryNeo4j;

    private String account;

    public UserService() {
    }

    public SaResult checkUser(String username, String Password) {
        if (userRepositoryMysql.existsByAccountAndPassword(username, Password)) {
            /*
              注册Token
             */
            byte[] uuidBytes = username.getBytes();
            UUID uuid = UUID.nameUUIDFromBytes(uuidBytes);
            StpUtil.login(uuid.toString());
            /*
              在会话中存储帐号，用于后续查询；
             */
            SaSession session = StpUtil.getSession();
            session.set("account", username);
            return SaResult.ok();
        }
        String token = StpUtil.getTokenValue();
        StpUtil.logoutByTokenValue(token);
        return SaResult.error("账号或密码错误");
    }

//    public List<Story> getUserStory() {
//        String account = StpUtil.getSession().get("account").toString();
//
//    }
}
