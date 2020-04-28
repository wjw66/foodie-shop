package com.wjw.impl;
import com.wjw.UserService;
import com.wjw.mapper.UsersMapper;
import com.wjw.pojo.Users;
import com.wjw.pojo.bo.UserBO;
import com.wjw.utils.DateUtil;
import com.wjw.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static com.wjw.enums.Sex.SECRET;


/**
 * @author : wjwjava01@163.com
 * @date : 20:37 2020/4/26
 * @description : 用户Service实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private Sid sid;

    private static final String USER_FACE = "http://img2.imgtn.bdimg.com/it/u=1354268575,1268995723&fm=26&gp=0.jpg";
    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 用户已存在返回true,否则为false
     */
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users(username);
        int count = usersMapper.selectCount(user);
        return count > 0;
    }

    /**
     * 创建用户
     *
     * @param userBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Users createUser(UserBO userBO) {
        //全局自动生成id
        String userId = sid.nextShort();
        Users users = new Users();
        users.setId(userId);
        users.setUsername(userBO.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
        }
        //用户昵称
        users.setNickname(userBO.getUsername());
        //用户头像
        users.setFace(USER_FACE);
        users.setSex(SECRET.getType());
        //默认生日
        users.setBirthday(DateUtil.stringToDate("1900-01-01"));
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());

        usersMapper.insert(users);
        return users;
    }
}
