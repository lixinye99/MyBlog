package com.arnoclare.blogsystem.dao;

import com.arnoclare.blogsystem.model.responsedata.UserVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:17 2020/7/22
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 查找所有的用户的信息和权限信息
     * @return
     */
    @Select("select u.id,u.username,u.password,u.email,u.created,a.authority from t_user u,t_authority a,t_user_authority ua where u.id = ua.user_id and a.id = ua.authority_id ")
    List<UserVo> selectAllUserRole();

    @Delete("delete from t_user where id = #{id}")
    void deleteUser(int id);

    @Delete("delete from t_user_authority where user_id = #{id}")
    void deleteUserRole(int id);

    @Update("update t_user_authority set authority_id = #{role} where user_id = #{id} ")
    void updateUserRole(int id, int role);

    @Select("select id from t_authority where authority = #{role}")
    int selectUserRole(String role);

    @Update("update t_user set password = #{password} where id = #{id}")
    void resetPwd(int id,String password);

    @Select("select email from t_user where id = #{id}")
    String selectEmailById(int id);
}
