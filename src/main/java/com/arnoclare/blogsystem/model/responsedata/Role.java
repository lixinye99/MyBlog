package com.arnoclare.blogsystem.model.responsedata;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:31 2020/7/22
 */
public enum Role {
    ROLE_admin("ROLE_admin","管理员"),
    ROLE_comment("ROLE_comment","可评论"),
    ROLE_nocomment("ROLE_nocomment","不可评论");


    private String type;
    private String role;


    Role(String type, String role) {
        this.type = type;
        this.role = role;
    }

    public static String getValue(String msg) {
        for (Role e : Role.values()) {
            if (e.getType().equals(msg))
                return e.getRole();
        }
        return null;
    }

    public static String getType(String msg) {
        for (Role e : Role.values()) {
            if (e.getRole().equals(msg))
                return e.getType();
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }
}
