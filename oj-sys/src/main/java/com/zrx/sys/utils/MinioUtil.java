package com.zrx.sys.utils;

import cn.hutool.core.date.DateUtil;
import com.zrx.exception.BusinessException;
import com.zrx.minio.MinioConfig;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.sys.model.entity.SysUser;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class MinioUtil {

	@Resource
	private MinioConfig prop;

	@Resource
	private MinioClient minioClient;

	/**
	 * 查看存储 bucket 是否存在
	 * @return boolean
	 */
	public Boolean bucketExists(String bucketName) {
		boolean found;
		try {
			found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		}
		catch (Exception e) {
			log.info("查看存储 bucket 是否存在 报错: {}", e.getMessage());
			return false;
		}
		return found;
	}

	/**
	 * 创建存储bucket
	 * @return Boolean
	 */
	public Boolean makeBucket(String bucketName) {
		try {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
		}
		catch (Exception e) {
			log.info("创建存储bucket报错: {}", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 删除存储bucket
	 * @return Boolean
	 */
	public Boolean removeBucket(String bucketName) {
		try {
			minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
		}
		catch (Exception e) {
			log.info("删除存储bucket报错: {}", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 获取全部bucket
	 */
	public List<Bucket> getAllBuckets() {
		try {
			List<Bucket> buckets = minioClient.listBuckets();
			return buckets;
		}
		catch (Exception e) {
			log.info("获取全部bucket报错: {}", e.getMessage());
		}
		return null;
	}

	/**
	 * 文件上传
	 * @param file 文件
	 * @return Boolean
	 */
	public String upload(MultipartFile file, String fileName) {
		String originalFilename = file.getOriginalFilename();
		if (StringUtils.isBlank(originalFilename)) {
			throw new BusinessException("文件名不能为空");
		}
		SysUser user = SecurityHelper.getUser();
		String userInfo = user.getUsername() + "-" + user.getId();
		String formatDate = DateUtil.format(new Date(), "yyyy-MM-dd");
		String objectName = userInfo + "/" + formatDate + "/" + fileName;
		try {
			PutObjectArgs objectArgs = PutObjectArgs.builder()
				.bucket(prop.getBucketName())
				.object(objectName)
				.stream(file.getInputStream(), file.getSize(), -1)
				.contentType(file.getContentType())
				.build();
			// 文件名称相同会覆盖
			minioClient.putObject(objectArgs);
		}
		catch (Exception e) {
			log.info("文件上传报错: {}", e.getMessage());
			return null;
		}
		return objectName;
	}

	/**
	 * 预览图片
	 * @param fileName 文件名
	 */
	public String preview(String fileName) {
		// 查看文件地址
		GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs().builder()
			.bucket(prop.getBucketName())
			.object(fileName)
			.method(Method.GET)
			.build();
		try {
			return minioClient.getPresignedObjectUrl(build);
		}
		catch (Exception e) {
			log.info("预览图片报错: {}", e.getMessage());
		}
		return null;
	}

	/**
	 * 文件下载
	 * @param fileName 文件名称
	 * @param resp response
	 */
	public void download(String fileName, HttpServletResponse resp) {
		GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(prop.getBucketName()).object(fileName).build();
		try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
			byte[] buf = new byte[1024];
			int len;
			try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
				while ((len = response.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				os.flush();
				byte[] bytes = os.toByteArray();
				resp.setCharacterEncoding("utf-8");
				// 设置强制下载不打开
				// res.setContentType("application/force-download");
				resp.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
				try (ServletOutputStream stream = resp.getOutputStream()) {
					stream.write(bytes);
					stream.flush();
				}
			}
		}
		catch (Exception e) {
			log.info("文件下载报错: {}", e.getMessage());
		}
	}

	/**
	 * 查看文件对象
	 * @return 存储 bucket 内文件对象信息
	 */
	public List<Item> listObjects() {
		Iterable<Result<Item>> results = minioClient
			.listObjects(ListObjectsArgs.builder().bucket(prop.getBucketName()).build());
		List<Item> items = new ArrayList<>();
		try {
			for (Result<Item> result : results) {
				items.add(result.get());
			}
		}
		catch (Exception e) {
			log.info("查看文件对象报错: {}", e.getMessage());
			return null;
		}
		return items;
	}

	/**
	 * 删除
	 * @param fileName 文件名
	 */
	public boolean remove(String fileName) {
		try {
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(prop.getBucketName()).object(fileName).build());
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

}
