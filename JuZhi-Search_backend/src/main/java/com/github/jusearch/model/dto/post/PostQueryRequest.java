package com.github.jusearch.model.dto.post;

import cn.hutool.json.JSONUtil;
import com.github.jusearch.common.PageRequest;
import com.github.jusearch.model.entity.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author iusine
 * @description .class无描述
 * @email iusine@163.com
 * @date 2025/1/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryRequest extends PageRequest implements Serializable {


    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 至少有一个标签
     */
    private List<String> orTags;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 收藏用户 id
     */
    private Long favourUserId;

    private static final long serialVersionUID = 1L;

    /**
     * 封装类转对象
     */
    public static Post voToObj(PostQueryRequest postQueryRequest) {
        if (postQueryRequest == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postQueryRequest, post);
        // 类型不同，需要转换
        post.setTags(JSONUtil.toJsonStr(postQueryRequest.getTags()));

        return post;
    }

    /**
     * 对象转封装类
     */
    public static PostQueryRequest objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        BeanUtils.copyProperties(post, postQueryRequest);
        // 类型不同，需要转换
        postQueryRequest.setTags(JSONUtil.toList(post.getTags(), String.class));
        return postQueryRequest;
    }
}
