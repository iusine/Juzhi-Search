package com.github.jusearch.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jusearch.model.entity.Post;
import com.github.jusearch.model.entity.PostFavour;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jusearch.model.entity.User;

/**
* 针对表【post_favour(帖子收藏)】的数据库操作Service
* @author iusine
* @email iusine@163.com
* @date 2025-01-07 09:17:20
*/
public interface PostFavourService extends IService<PostFavour> {

    /**
     * 帖子收藏
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostFavour(long postId, User loginUser);

    /**
     * 分页获取用户收藏的帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Post> listFavourPostByPage(IPage<Post> page, Wrapper<Post> queryWrapper,
                                    long favourUserId);

    /**
     * 帖子收藏（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostFavourInner(long userId, long postId);
}
