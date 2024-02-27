package com.zrx.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
@Data
@Schema(name = "树节点")
public class TreeNode<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 上级ID
     */
    private String pid;
    /**
     * 子节点列表
     */
    private List<T> children = new ArrayList<>();

}
