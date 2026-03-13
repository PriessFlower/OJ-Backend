package com.zt.codesandbox.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * cgroups 内存监控工具类
 * 用于直接读取容器的峰值内存使用量
 */
@Slf4j
public class CgroupsMemoryUtils {

    /**
     * 获取容器的峰值内存使用量（字节）
     *
     * 优先尝试 cgroups v1，如果失败则尝试 cgroups v2
     *
     * @param containerId Docker 容器 ID（完整 ID 或短 ID）
     * @return 峰值内存（字节），如果读取失败返回 -1
     */
    public static long getMaxMemoryUsage(String containerId) {
        if (containerId == null || containerId.isEmpty()) {
            log.warn("容器 ID 为空");
            return -1;
        }

        // 尝试 cgroups v1
        long memory = readCgroupsV1(containerId);
        if (memory > 0) {
            log.info("从 cgroups v1 读取峰值内存: {} bytes ({} MB)",
                     memory, memory / 1024 / 1024);
            return memory;
        }

        // 尝试 cgroups v2
        memory = readCgroupsV2(containerId);
        if (memory > 0) {
            log.info("从 cgroups v2 读取峰值内存: {} bytes ({} MB)",
                     memory, memory / 1024 / 1024);
            return memory;
        }

        log.warn("无法读取容器 {} 的峰值内存（cgroups v1 和 v2 均失败）", containerId);
        return -1;
    }

    /**
     * 读取 cgroups v1 的峰值内存
     * 路径：/sys/fs/cgroup/memory/docker/{container_id}/memory.max_usage_in_bytes
     */
    private static long readCgroupsV1(String containerId) {
        // 尝试完整容器 ID
        long memory = tryReadFile("/sys/fs/cgroup/memory/docker/" + containerId + "/memory.max_usage_in_bytes");
        if (memory > 0) {
            return memory;
        }

        // 尝试短容器 ID（前 12 位）
        if (containerId.length() > 12) {
            String shortId = containerId.substring(0, 12);
            memory = tryReadFile("/sys/fs/cgroup/memory/docker/" + shortId + "/memory.max_usage_in_bytes");
            if (memory > 0) {
                return memory;
            }
        }

        // 尝试其他可能的路径（某些 Docker 版本）
        memory = tryReadFile("/sys/fs/cgroup/memory/system.slice/docker-" + containerId + ".scope/memory.max_usage_in_bytes");
        if (memory > 0) {
            return memory;
        }

        return -1;
    }

    /**
     * 读取 cgroups v2 的峰值内存
     * 路径：/sys/fs/cgroup/docker/{container_id}/memory.peak
     */
    private static long readCgroupsV2(String containerId) {
        // 尝试完整容器 ID
        long memory = tryReadFile("/sys/fs/cgroup/docker/" + containerId + "/memory.peak");
        if (memory > 0) {
            return memory;
        }

        // 尝试短容器 ID
        if (containerId.length() > 12) {
            String shortId = containerId.substring(0, 12);
            memory = tryReadFile("/sys/fs/cgroup/docker/" + shortId + "/memory.peak");
            if (memory > 0) {
                return memory;
            }
        }

        // 尝试其他可能的路径
        memory = tryReadFile("/sys/fs/cgroup/system.slice/docker-" + containerId + ".scope/memory.peak");
        if (memory > 0) {
            return memory;
        }

        return -1;
    }

    /**
     * 尝试读取指定路径的文件内容并解析为 long
     */
    private static long tryReadFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                return -1;
            }

            String content = Files.readString(path).trim();
            return Long.parseLong(content);
        } catch (IOException e) {
            log.trace("读取文件失败 {}: {}", filePath, e.getMessage());
            return -1;
        } catch (NumberFormatException e) {
            log.warn("解析内存值失败 {}: {}", filePath, e.getMessage());
            return -1;
        }
    }

    /**
     * 重置容器的峰值内存计数器
     * 注意：需要 root 权限，且仅支持 cgroups v1
     *
     * @param containerId Docker 容器 ID
     * @return 是否重置成功
     */
    public static boolean resetMaxMemoryUsage(String containerId) {
        if (containerId == null || containerId.isEmpty()) {
            return false;
        }

        try {
            String filePath = "/sys/fs/cgroup/memory/docker/" + containerId + "/memory.max_usage_in_bytes";
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                // 尝试短 ID
                if (containerId.length() > 12) {
                    String shortId = containerId.substring(0, 12);
                    filePath = "/sys/fs/cgroup/memory/docker/" + shortId + "/memory.max_usage_in_bytes";
                    path = Paths.get(filePath);
                }
            }

            if (Files.exists(path)) {
                // 写入 0 可以重置峰值计数器
                Files.writeString(path, "0");
                log.debug("已重置容器 {} 的峰值内存计数器", containerId);
                return true;
            }
        } catch (IOException e) {
            log.warn("重置峰值内存失败: {}", e.getMessage());
        }

        return false;
    }

    /**
     * 检测系统使用的 cgroups 版本
     *
     * @return 1 表示 cgroups v1, 2 表示 cgroups v2, 0 表示无法检测
     */
    public static int detectCgroupsVersion() {
        // 检查 cgroups v2
        if (Files.exists(Paths.get("/sys/fs/cgroup/cgroup.controllers"))) {
            return 2;
        }

        // 检查 cgroups v1
        if (Files.exists(Paths.get("/sys/fs/cgroup/memory"))) {
            return 1;
        }

        return 0;
    }

    /**
     * 获取容器当前内存使用量（非峰值）
     *
     * @param containerId Docker 容器 ID
     * @return 当前内存使用量（字节），失败返回 -1
     */
    public static long getCurrentMemoryUsage(String containerId) {
        // cgroups v1
        long memory = tryReadFile("/sys/fs/cgroup/memory/docker/" + containerId + "/memory.usage_in_bytes");
        if (memory > 0) {
            return memory;
        }

        // cgroups v2
        memory = tryReadFile("/sys/fs/cgroup/docker/" + containerId + "/memory.current");
        if (memory > 0) {
            return memory;
        }

        return -1;
    }
}
