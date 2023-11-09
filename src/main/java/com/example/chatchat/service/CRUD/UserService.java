package com.example.chatchat.service.CRUD;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import com.example.chatchat.data.Friend;
import com.example.chatchat.data.mysql.model.Story;
import com.example.chatchat.data.mysql.model.User;
import com.example.chatchat.data.mysql.repository.UserRepository;
import com.example.chatchat.data.neo4j.model.UserNeo4j;
import com.example.chatchat.data.neo4j.repository.UserRepositoryNeo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.plaf.PanelUI;
import java.time.LocalDate;
import java.util.*;

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

    public SaResult changePassword(String oldPassword, String newPassword) {
        String account = StpUtil.getSession().get("account").toString();
        User user = userRepositoryMysql.findByAccount(account);
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepositoryMysql.save(user);
            return SaResult.ok();
        }
        return SaResult.error("原密码错误");
    }

    /**
     * 修改用户信息
     */
    public SaResult updateInfo(String nickname, String motto,
                               String avatar, String birthday) {
        String account = StpUtil.getSession().get("account").toString();
        User user = userRepositoryMysql.findByAccount(account);
        // Todo 这里有很大的优化空间
        user.setNickname(nickname.isEmpty() ? user.getNickname() : nickname);
        user.setMotto(motto.isEmpty() ? user.getMotto() : motto);
        user.setAvatar(avatar.isEmpty() ? user.getAvatar() : avatar);
        user.setBirthday(birthday.isEmpty() ? user.getBirthday() : LocalDate.parse(birthday));
        userRepositoryMysql.save(user);
        return SaResult.ok();
    }

    /**
     * 获取当前用户信息
     */
    public User getUserInfo() {
        String account = StpUtil.getSession().get("account").toString();
        User user = userRepositoryMysql.findByAccount(account);
        user.setPassword(null);
        return user;
    }

    /**
     * 获取所有好友申请
     * 遍历出帐号列表并返回
     * @return 帐号列表
     */
    public List<String> getApply(Integer index) {
        String account = StpUtil.getSession().get("account").toString();
        UserNeo4j user = userRepositoryNeo4j.findByAccount(account);
        List<UserNeo4j> applyList = new ArrayList<>(user.getApplyList());
        List<String> accountList = new ArrayList<>();
        for (UserNeo4j tmp : applyList.subList(index <= 0 ? 0 : --index, Math.min(index + 10, applyList.size()))) {
            accountList.add(tmp.getAccount());
        }
        return accountList;
    }

    /**
     * 申请
     *
     * @param friendAccount
     * @return
     */
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

    /**
     * 拒绝好友申请
     */
    public SaResult refuseApply(String account) {
        UserNeo4j user = userRepositoryNeo4j.findByAccount(StpUtil.getSession().get("account").toString());
        if (!user.isApplyExist(account)) {
            return SaResult.error("申请不存在");
        }
        if (user.isFriendExist(account)) {
            return SaResult.error("已是好友");
        }
        user.removeApply(account);
        //需要删除持久化的实例后再添加，以达到修改的目的
        userRepositoryNeo4j.delete(user);
        userRepositoryNeo4j.save(user);
        return SaResult.ok();
    }

    /**
     * 同意好友申请
     */
    public SaResult agreeApply(String account) {
        UserNeo4j user = userRepositoryNeo4j.findByAccount(StpUtil.getSession().get("account").toString());
        if (!user.isApplyExist(account)) {
            return SaResult.error("申请不存在");
        }
        if (user.isFriendExist(account)) {
            return SaResult.error("已是好友");
        }
        //获取好友的实例
        UserNeo4j friend = userRepositoryNeo4j.findByAccount(account);
        //删除请求
        user.removeApply(account);
        userRepositoryNeo4j.delete(user);
        //相互添加好友
        user.addFriend(friend);
        friend.addFriend(user);
        userRepositoryNeo4j.save(user);
        userRepositoryNeo4j.save(friend);

        return SaResult.ok();
    }

    /**
     * 删除好友
     */

    public SaResult deleteFriend(String account) {
        UserNeo4j user = userRepositoryNeo4j.findByAccount(StpUtil.getSession().get("account").toString());
        if (!user.isFriendExist(account)) {
            return SaResult.error("好友不存在");
        }
        UserNeo4j friend = userRepositoryNeo4j.findByAccount(account);
        user.removeFriend(friend);
        friend.removeFriend(user);
        userRepositoryNeo4j.delete(friend);
        userRepositoryNeo4j.delete(user);
        userRepositoryNeo4j.save(friend);
        userRepositoryNeo4j.save(user);
        return SaResult.ok();
    }

    /**
     * 获取好友列表
     */
    public Set<Friend> getFriends(Integer index) {
        String account = StpUtil.getSession().get("account").toString();
        UserNeo4j user = userRepositoryNeo4j.findByAccount(account);
        if (user.isFriendListEmpty()) {
            return null;
        }
        Set<Friend> friends = new HashSet<>();
        //用户列表预处理
        List<UserNeo4j> friendList = new ArrayList<>(user.getFriends());
        for (UserNeo4j friend : friendList.subList(index <= 0 ? 0 : --index, Math.min(index + 10, friendList.size()))) {//查询出10条记录
            User friendUser = userRepositoryMysql.findByAccount(friend.getAccount());
            Friend data = new Friend(friendUser.getAccount(), friendUser.getNickname(), friendUser.getAvatar(), friendUser.getMotto(), friendUser.getBirthday());
            friends.add(data);
        }
        return friends;
    }
}
