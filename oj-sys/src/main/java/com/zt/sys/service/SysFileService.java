package com.zt.sys.service;

import com.mybatisflex.core.service.IService;
import com.zt.sys.entity.SysFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 云文件信息表 服务层。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
public interface SysFileService extends IService<SysFile> {

	String upload(MultipartFile file,String folder);

    String upload(MultipartFile file);

}
