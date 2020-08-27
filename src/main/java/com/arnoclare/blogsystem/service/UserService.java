package com.arnoclare.blogsystem.service;

import com.arnoclare.blogsystem.model.responsedata.UserVo;
import com.github.pagehelper.PageInfo;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:28 2020/7/22
 */
public interface UserService {

    /**
     * 分页查找所有用户的信息和权限信息
     * @return
     * @param page
     * @param count
     */
    public PageInfo<UserVo> selectAllUserInfo(int page, int count);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteUser(int id);

    /**
     * 根据用户id修改用户权限
     * @param id
     * @param roleChange
     */
    void updateUserRole(int id, String roleChange);

    /**
     * 把生成的随机密码写入数据库
     * @param password
     * @param id
     */
    void resetPwd( int id,String password);

    /**
     * 根据id查找用户的email
     * @param id
     * @return
     */
    String queryUserEmailById(int id);
}
