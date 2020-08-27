package com.arnoclare.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.arnoclare.blogsystem.dao.CommentMapper;
import com.arnoclare.blogsystem.dao.StatisticMapper;
import com.arnoclare.blogsystem.model.domain.Comment;
import com.arnoclare.blogsystem.model.domain.Statistic;
import com.arnoclare.blogsystem.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * @Classname CommentServiceImpl
 * @Description
 * @Date 2019-3-14 10:15
 * @Created by CrazyStone
 */

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;

    // 根据文章id分页查询评论
    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> commentList = commentMapper.selectCommentWithPage(aid);
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }

    // 用户发表评论
    @CacheEvict(cacheNames = "commentsWithPage")
    @Override
    public void pushComment(Comment comment){
        commentMapper.pushComment(comment);
        // 更新文章评论数据量
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleCommentsWithId(statistic);
    }

    @Cacheable(cacheNames = "commentsWithPage")
    @Override
    public PageInfo<Comment> selectAllCommentWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Comment> commentList = commentMapper.selectAllCommentWithPage();
        // 无 需封装内容
        return new PageInfo<>(commentList);
    }

    @Transactional
    @CacheEvict(cacheNames = "commentsWithPage")
    @Override
    public void deleteComment(int id) {
        // 更新统计数据
        Statistic statistic = statisticMapper.selectStatisticWithCommentId(id);
        statistic.setCommentsNum(statistic.getCommentsNum() - 1);
        statisticMapper.updateArticleCommentsWithId(statistic);
        // 删除评论
        commentMapper.deleteComment(id);
    }
}

