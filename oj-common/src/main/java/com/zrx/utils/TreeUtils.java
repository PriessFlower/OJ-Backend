package com.zrx.utils;

import com.zrx.exception.BusinessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树节点工具类
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
public class TreeUtils {

	/**
	 * 根据pid，构建树节点
	 * @param treeNodes 树节点列表
	 * @param pid 父节点ID
	 * @return 根据pid构建的树节点列表
	 * @throws BusinessException 如果pid为空
	 */
	public static <T extends TreeNode> List<T> build(List<T> treeNodes, String pid) {
		if (pid == null) {
			throw new BusinessException("pid 不能为空");
		}

		List<T> treeList = new ArrayList<>();
		for (T treeNode : treeNodes) {
			if (pid.equals(treeNode.getPid())) {
				treeList.add(findChildren(treeNodes, treeNode));
			}
		}

		return treeList;
	}

	/**
	 * 查找子节点
	 * @param treeNodes 树节点列表
	 * @param rootNode 根节点
	 * @return 根节点及其子节点
	 */
	private static <T extends TreeNode> T findChildren(List<T> treeNodes, T rootNode) {
		for (T treeNode : treeNodes) {
			if (rootNode.getId().equals(treeNode.getPid())) {
				rootNode.getChildren().add(findChildren(treeNodes, treeNode));
			}
		}
		return rootNode;
	}

	/**
	 * 构建树节点
	 * @param treeNodes 树节点列表
	 * @return 构建的树节点列表
	 */
	public static <T extends TreeNode> List<T> build(List<T> treeNodes) {
		List<T> result = new ArrayList<>();

		// 使用HashMap来存储节点，提高性能
		Map<String, T> nodeMap = new HashMap<>(treeNodes.size());
		for (T treeNode : treeNodes) {
			nodeMap.put(treeNode.getId(), treeNode);
		}

		for (T node : nodeMap.values()) {
			T parent = nodeMap.get(node.getPid());
			if (parent != null && !node.getId().equals(parent.getId())) {
				parent.getChildren().add(node);
			}
			else {
				result.add(node);
			}
		}

		return result;
	}

}
