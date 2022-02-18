package com.juelunn.lightcloud.module.blog.service;

import com.juelunn.common.entity.Comment;
import com.juelunn.common.dto.CommentBackDTO;
import com.juelunn.common.dto.CommentDTO;
import com.juelunn.common.vo.CommentVO;
import com.juelunn.common.vo.ConditionVO;
import com.juelunn.common.vo.PageResult;
import com.juelunn.common.vo.ReviewVO;
import com.juelunn.common.dto.ReplyDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 评论服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/29
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查看评论
     *
     * @param articleId 文章id
     * @return 评论列表
     */
    PageResult<CommentDTO> listComments(Integer articleId);

    /**
     * 查看评论下的回复
     *
     * @param commentId 评论id
     * @return 回复列表
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    /**
     * 添加评论
     *
     * @param commentVO 评论对象
     */
    void saveComment(CommentVO commentVO);

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     */
    void saveCommentLike(Integer commentId);

    /**
     * 审核评论
     *
     * @param reviewVO 审核信息
     */
    void updateCommentsReview(ReviewVO reviewVO);

    /**
     * 查询后台评论
     *
     * @param condition 条件
     * @return 评论列表
     */
    PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition);

}
