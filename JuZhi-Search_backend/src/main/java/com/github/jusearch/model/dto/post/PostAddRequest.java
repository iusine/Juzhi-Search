package com.github.jusearch.model.dto.post;

import cn.hutool.json.JSONUtil;
import com.github.jusearch.model.entity.Post;
import com.github.jusearch.model.vo.user.LoggingUserVO;
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
public class PostAddRequest  implements Serializable {

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
    public static Post voToObj(PostAddRequest postAddRequest) {
        if (postAddRequest == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postAddRequest, post);
        // 类型不同，需要转换
        post.setTags(JSONUtil.toJsonStr(postAddRequest.getTags()));

        return post;
    }

    /**
     * 对象转封装类
     */
    public static PostAddRequest objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostAddRequest postAddRequest = new PostAddRequest();
        BeanUtils.copyProperties(post, postAddRequest);
        // 类型不同，需要转换
        postAddRequest.setTags(JSONUtil.toList(post.getTags(), String.class));
        return postAddRequest;
    }
}