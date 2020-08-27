package com.arnoclare.blogsystem.service.impl;

import com.arnoclare.blogsystem.dao.UserMapper;
import com.arnoclare.blogsystem.model.responsedata.Role;
import com.arnoclare.blogsystem.model.responsedata.UserVo;
import com.arnoclare.blogsystem.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:30 2020/7/22
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 分页查询用户的信息
     * @param page
     * @param count
     * @return
     */
    @Override
    public PageInfo<UserVo> selectAllUserInfo(int page, int count) {
        PageHelper.startPage(page, count);
        List<UserVo> userVos = userMapper.selectAllUserRole();
        List<UserVo> users = userVos.stream().map(userVo ->
        {
            return new UserVo(userVo.getId(), userVo.getUsername(), userVo.getPassword(), userVo.getEmail(), userVo.getCreated(), Role.getValue(userVo.getAuthority()));
        }).collect(Collectors.toList());
        PageInfo<UserVo> userInfo = new PageInfo<>(users);
        return userInfo;
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
        userMapper.deleteUserRole(id);
    }

    @Override
    public void updateUserRole(int id, String roleChange) {
        int roleId = userMapper.selectUserRole(roleChange);
        userMapper.updateUserRole(id,roleId);
    }

    @Override
    public void resetPwd(int id,String password) {
        userMapper.resetPwd(id,password);
    }

    @Override
    public String queryUserEmailById(int id) {
        String email = userMapper.selectEmailById(id);
        return email;
    }
}
