package com.arnoclare.blogsystem.model.responsedata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:18 2020/7/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Date created;
    private String authority;
}
