package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.CommentVo;
import cn.knowei.sbg.domain.vo.PageVo;
import cn.knowei.sbg.entity.Comment;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.exception.SystemException;
import cn.knowei.sbg.mapper.CommentMapper;
import cn.knowei.sbg.service.CommentService;
import cn.knowei.sbg.service.UserService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-02-24 19:12:11
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    /**
     * 评论查找
     *
     * @param commentType
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper<>();
        //判断articleId
        qw.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        //根评论
        qw.eq(Comment::getRootId, -1);
        //评论类型
        qw.eq(Comment::getType, commentType);

        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, qw);

        //转换为前端格式
        List<CommentVo> commentVos = ToCommentVoList(page.getRecords());

        for (CommentVo commentVo : commentVos) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVos,page.getTotal()));
    }

    /**
     *
     * @param comment
     * @return
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.COMMENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 查找子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id){
        LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper<>();
        qw.eq(Comment::getRootId, id);
        qw.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(qw);

        List<CommentVo> commentVos = ToCommentVoList(comments);
        return commentVos;
    }

    /**
     * 转换
     * @param list
     * @return
     */
    private List<CommentVo> ToCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        for (CommentVo commentVo : commentVos) {
            //根据id查找用户信息
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);

            if (commentVo.getToCommentId() != -1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}

