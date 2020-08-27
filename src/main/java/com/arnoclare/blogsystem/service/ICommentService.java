package com.arnoclare.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.arnoclare.blogsystem.model.domain.Comment;
/**
 * @Classname ICommentService
 * @Description 文章评论业务处理接口
 * @Date 2019-3-14 10:13
 * @Created by CrazyStone
 */
public interface ICommentService {
    // 获取文章下的评论
    public PageInfo<Comment> getComments(Integer aid, int page, int count);

    // 用户发表评论
    public void pushComment(Comment comment);

    /**
     * 分页查询评论列表
     * @author Arno Clare
     */
    public PageInfo<Comment> selectAllCommentWithPage(Integer page, Integer count);

    /**
     * 根据主键删除评论
     * @author Arno Clare
     */
    public void deleteComment(int id);

}

