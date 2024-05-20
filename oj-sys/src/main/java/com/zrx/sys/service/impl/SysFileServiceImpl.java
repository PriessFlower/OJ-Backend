package com.zrx.sys.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.id.NanoId;
import com.alibaba.excel.util.StringUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.sys.entity.SysFile;
import com.zrx.sys.mapper.SysFileMapper;
import com.zrx.sys.service.SysFileService;
import com.zrx.sys.utils.MinioUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 云文件信息表 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

	@Resource
	private MinioUtil minioUtil;

	@Value("${minio.endpoint}")
	private String endpoint;

	@Value("${minio.bucketName}")
	private String bucketName;

	@Override
	public String upload(MultipartFile file) {
		// TODO: 处理大文件上传
		String fileName = file.getOriginalFilename();
		fileName = NanoId.randomNanoId(10) + "-" + FileNameUtil.getName(fileName);
		SysFile sysFile = new SysFile();
		sysFile.setFileName(fileName);
		String url = minioUtil.upload(file, fileName);
		if (StringUtils.isEmpty(url)) {
			throw new BusinessException("上传失败! ");
		}
		sysFile.setUrl(url);
		sysFile.setFileSize(file.getSize());
		boolean save = save(sysFile);
		if (!save) {
			throw new BusinessException("上传失败");
		}
		return getUrl(url);
	}

	private String getUrl(String url) {
		return endpoint + "/" + bucketName + "/" + url;
	}

}
