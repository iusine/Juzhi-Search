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
public class PostEditRequest implements Serializable {

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
    public static Post voToObj(PostEditRequest postEditRequest) {
        if (postEditRequest == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postEditRequest, post);
        // 类型不同，需要转换
        post.setTags(JSONUtil.toJsonStr(postEditRequest.getTags()));

        return post;
    }

    /**
     * 对象转封装类
     */
    public static PostEditRequest objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostEditRequest postEditRequest = new PostEditRequest();
        BeanUtils.copyProperties(post, postEditRequest);
        // 类型不同，需要转换
        postEditRequest.setTags(JSONUtil.toList(post.getTags(), String.class));
        return postEditRequest;
    }
}
