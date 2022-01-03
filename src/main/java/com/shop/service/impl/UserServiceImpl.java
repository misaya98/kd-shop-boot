package com.shop.service.impl;

import com.shop.dao.UserMapper;
import com.shop.ex.PhoneNotFoundException;
import com.shop.ex.UsernameTakenException;
import com.shop.pojo.User;
import com.shop.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        User user = userMapper.getUserByPhone(record.getPhone());
        if(user == null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            record.setCreateAt(sdf.format(new Date()));
            record.setGoodsNum(0);
            record.setPower((byte)10);
            record.setSex("男");
            record.setImgUrl("b78c6caa-0175-4610-a591-c6aa2917c3aa.png");
            record.setBirthday(sdf.format(new Date()));
            return userMapper.insert(record);
        }else{
            throw new UsernameTakenException("手机号已注册");
        }
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int updateLastLoginByPrimaryKey(User user) {
        return userMapper.updateLastLoginByPrimaryKey(user);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getUserByPhone(String phone) {
        User user = userMapper.getUserByPhone(phone);
        if(user == null){
            throw new PhoneNotFoundException("手机号未注册");
        }else{
            return user;
        }
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public int updateGoodsNum(Integer id, Integer goodsNum) {
        return userMapper.updateGoodsNum(id,goodsNum);
    }

    @Override
    public int updateImgUrl(Integer id, String url) {
        return userMapper.updateImgUrl(id,url);
    }

    @Override
    public Integer checkUserByPhone(String phone) {
        return userMapper.checkUserByPhone(phone);
    }

    @Override
    public int updatePasswordByPrimaryKey(Integer uid, String password) {
        return userMapper.updatePasswordByPrimaryKey(uid,password);
    }

    @Override
    public int updatePowerByPrimaryKey(Integer uid, Integer power) {
        return userMapper.updatePowerByPrimaryKey(uid,power);
    }

    @Override
    public List<User> searchUserByNameOrPhone(String name, String phone) {
        return userMapper.searchUserByNameOrPhone(name,phone);
    }
}
