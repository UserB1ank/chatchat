package com.example.chatchat.service.CRUD;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import com.example.chatchat.data.mysql.model.User;
import com.example.chatchat.data.mysql.repository.UserRepository;
import com.example.chatchat.data.neo4j.model.UserNeo4j;
import com.example.chatchat.data.neo4j.repository.UserRepositoryNeo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepositoryMysql;
    @Autowired
    public UserRepositoryNeo4j userRepositoryNeo4j;
    // TODO 好友申请与通过
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

    public SaResult register(String username, String password, String nickname, LocalDate birthday) {
        if (userRepositoryMysql.existsByAccount(username) && userRepositoryNeo4j.existsByAccount(username)) {
            return SaResult.error("账号已存在");
        }
        User newBee = new User(username, password, nickname, birthday);
        userRepositoryMysql.save(newBee);
        UserNeo4j newBeeNeo4j = new UserNeo4j(username);
        userRepositoryNeo4j.save(newBeeNeo4j);
        return SaResult.ok();
    }

    public SaResult changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        String account = StpUtil.getSession().get("account").toString();
        User user = userRepositoryMysql.findByAccount(account);
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepositoryMysql.save(user);
            return SaResult.ok();
        }
        return SaResult.error("原密码错误");
    }

    public SaResult applyToFriend(String friendAccount) {
        if (!userRepositoryNeo4j.existsByAccount(friendAccount))
            return SaResult.error("申请目标不存在");
        String account = StpUtil.getSession().get("account").toString();
        UserNeo4j user = userRepositoryNeo4j.findByAccount(account);
        UserNeo4j friend = userRepositoryNeo4j.findByAccount(friendAccount);

        if (friend.getAccount().equals(account)) {
            return SaResult.error("不能申请自己为好友");
        }
        if (friend.isApplyExist(account)) {
            return SaResult.error("已申请过该好友");
        }
        friend.addApply(user);
        userRepositoryNeo4j.save(friend);
        return SaResult.ok();
    }
}
