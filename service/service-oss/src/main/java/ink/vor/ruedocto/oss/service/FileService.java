package ink.vor.ruedocto.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author muquanrui
 * @date 2022/6/22 21:54
 */
public interface FileService {
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
