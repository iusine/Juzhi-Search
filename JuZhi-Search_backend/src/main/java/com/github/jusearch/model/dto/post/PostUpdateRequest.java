package com.github.jusearch.model.dto.post;

import cn.hutool.json.JSONUtil;
import com.github.jusearch.model.entity.Post;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author iusine
 * @description .class无描述
 * @email iusine@163.com
 * @date 2025/1/7
 */
@Data
public class PostUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;

    /**
     * 封装类转对象
     */
    public static Post voToObj(PostUpdateRequest postUpdateRequest) {
        if (postUpdateRequest == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postUpdateRequest, post);
        // 类型不同，需要转换
        post.setTags(JSONUtil.toJsonStr(postUpdateRequest.getTags()));

        return post;
    }

    /**
     * 对象转封装类
     */
    public static PostUpdateRequest objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest();
        BeanUtils.copyProperties(post, postUpdateRequest);
        // 类型不同，需要转换
        postUpdateRequest.setTags(JSONUtil.toList(post.getTags(), String.class));
        return postUpdateRequest;
    }
}
