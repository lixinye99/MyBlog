package com.arnoclare.blogsystem.web.admin;

import com.arnoclare.blogsystem.model.domain.Article;
import com.arnoclare.blogsystem.model.domain.Comment;
import com.arnoclare.blogsystem.model.responsedata.ArticleResponseData;
import com.arnoclare.blogsystem.model.responsedata.Role;
import com.arnoclare.blogsystem.model.responsedata.StaticticsBo;
import com.arnoclare.blogsystem.model.responsedata.UserVo;
import com.arnoclare.blogsystem.service.IArticleService;
import com.arnoclare.blogsystem.service.ICommentService;
import com.arnoclare.blogsystem.service.ISiteService;
import com.arnoclare.blogsystem.service.UserService;
import com.arnoclare.blogsystem.utils.MailUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname AdminController
 * @Description 后台管理模块
 * @Date 2019-3-14 10:39
 * @Created by CrazyStone
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ISiteService siteServiceImpl;
    @Autowired
    private IArticleService articleServiceImpl;
    @Autowired
    private ICommentService commentServiceImpl;
    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private MailUtils mailUtils;

    // 管理中心起始页
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        // 获取最新的5篇博客、评论以及统计数据
        List<Article> articles = siteServiceImpl.recentArticles(5);
        List<Comment> comments = siteServiceImpl.recentComments(5);
        StaticticsBo staticticsBo = siteServiceImpl.getStatistics();
        // 向Request域中存储数据
        request.setAttribute("comments", comments);
        request.setAttribute("articles", articles);
        request.setAttribute("statistics", staticticsBo);
        return "back/index";
    }

    // 向文章发表页面跳转
    @GetMapping(value = "/article/toEditPage")
    public String newArticle( ) {
        return "back/article_edit";
    }
    // 发表文章
    @PostMapping(value = "/article/publish")
    @ResponseBody
    public ArticleResponseData publishArticle(Article article) {
        if (StringUtils.isBlank(article.getCategories())) {
            article.setCategories("默认分类");
        }
        try {
            articleServiceImpl.publish(article);
            logger.info("文章发布成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章发布失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    // 跳转到后台文章列表页面
    @GetMapping(value = "/article")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "count", defaultValue = "10") int count,
                        HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    //跳转到后台的系统设置页面
    @GetMapping(value = "/config")
    public String systemConfig(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "count", defaultValue = "10") int count,
                                HttpServletRequest request){
        PageInfo<UserVo> userVos = userServiceImpl.selectAllUserInfo(page,count);
        request.setAttribute("users",userVos);
        return "back/systemConfig";
    }

    //用户删除
    @PostMapping("/config/delete")
    @ResponseBody
    public ArticleResponseData deleteUser(@RequestParam int id){
        try {
            userServiceImpl.deleteUser(id);
            logger.info("删除用户成功");
            return ArticleResponseData.ok();
        }catch (Exception e){
            logger.error("用户删除失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    //用户权限修改
    @PostMapping("/config/updateRole")
    @ResponseBody
    public ArticleResponseData updateRole(@RequestParam int id,@RequestParam String role){
        try {
            String roleChange = Role.getType(role);
            userServiceImpl.updateUserRole(id,roleChange);
            logger.info("用户权限修改成功");
            return ArticleResponseData.ok();
        }catch (Exception e){
            logger.error("用户权限修改失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    //重置用户的密码
    @PostMapping("/config/resetPwd")
    @ResponseBody
    public ArticleResponseData resetPwd(@RequestParam int id){

        String email = userServiceImpl.queryUserEmailById(id);
        String filename= RandomStringUtils.randomAlphanumeric(6);
        mailUtils.sendSimpleEmail(email,"重置密码成功!","您登录的用户的密码已经重置为"+filename);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(filename);
        try {
            userServiceImpl.resetPwd(id,password);
            logger.info("用户密码重置成功");
            return ArticleResponseData.ok();
        }catch (Exception e){
            logger.error("用户密码重置失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }


    // 向文章修改页面跳转
    @GetMapping(value = "/article/{id}")
    public String editArticle(@PathVariable("id") String id, HttpServletRequest request) {
        Article article = articleServiceImpl.selectArticleWithId(Integer.parseInt(id));
        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());
        return "back/article_edit";
    }

    // 文章修改处理
    @PostMapping(value = "/article/modify")
    @ResponseBody
    public ArticleResponseData modifyArticle(Article article) {
        try {
            articleServiceImpl.updateArticleWithId(article);
            logger.info("文章更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 文章删除
    @PostMapping(value = "/article/delete")
    @ResponseBody
    public ArticleResponseData delete(@RequestParam int id) {
        try {
            articleServiceImpl.deleteArticleWithId(id);
            logger.info("文章删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章删除失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    /**
     * 跳转到评论管理页面
     * @author Arno Clare
     */
    @GetMapping(value = "/comment")
    public String manageComment(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "count", defaultValue = "10") int count,
                                HttpServletRequest request) {
        PageInfo<Comment> pageInfo = commentServiceImpl.selectAllCommentWithPage(page, count);
        request.setAttribute("comments", pageInfo);
        return "back/comment_list";
    }

    /**
     * 删除评论，并返回响应结果
     * @author Arno Clare
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/comment/delete")
    public ArticleResponseData deleteComment(@RequestParam int id) {
        try {
            commentServiceImpl.deleteComment(id);
            logger.info("评论删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("评论删除失败，错误信息: " + e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    /**
     * 跳转到分类/标签页面
     * @author Arno Clare
     */
    @GetMapping(value = "/category")
    public String categoryAndTag(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "count", defaultValue = "10") int count,
                                 HttpServletRequest request) {
        // 文章列表
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        // 已存在分类列表
        List<String> categories = articleServiceImpl.selectAllCategories();
        request.setAttribute("categories", categories);
        // 已存在标签列表
        List<String> tags = articleServiceImpl.selectAllTags();
        request.setAttribute("tags", tags);
        return "back/category_list";
    }

    /**
     * 更新分类和标签
     * @author Arno Clare
     */
    @ResponseBody
    @PostMapping(value = "/category/update")
    public ArticleResponseData updateCategoryAndTag(@RequestParam int id,
                                                    @RequestParam(value = "category") String category,
                                                    @RequestParam(value = "tags") String tags) {
        Article article = new Article();
        article.setId(id);
        article.setCategories(category);
        article.setTags(tags);
        try {
            articleServiceImpl.updateArticleWithId(article);
            logger.info("文章分类/标签更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章分类/标签更新失败，错误信息: " + e.getMessage());
            return ArticleResponseData.fail();
        }
    }

}

