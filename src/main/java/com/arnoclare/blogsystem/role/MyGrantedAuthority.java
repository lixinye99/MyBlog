package com.arnoclare.blogsystem.role;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 20:18 2020/7/21
 */
public class MyGrantedAuthority implements GrantedAuthority {

    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
