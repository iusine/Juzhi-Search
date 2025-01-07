package com.github.jusearch.service;

import com.github.jusearch.model.entity.PostThumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jusearch.model.entity.User;

/**
* 针对表【post_thumb(帖子点赞)】的数据库操作Service
* @author iusine
* @email iusine@163.com
* @date 2025-01-07 09:17:20
*/
public interface PostThumbService extends IService<PostThumb> {
    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}
