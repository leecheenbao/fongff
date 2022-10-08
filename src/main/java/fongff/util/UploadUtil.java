package fongff.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@Configuration
public class UploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);

    @Value("${file.path}")
    private String path;

    @Value("${file.realPath}")
    private String realPath;

    public String uploadFile(Integer indexR, String module, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            path = "D:\\WorkSpace\\fongffFile";
        }
        String fileName = indexR + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        String filePath = path + File.separator + module + File.separator + fileName;

        File destFile = new File(filePath);
        destFile.getParentFile().mkdirs();
        //把瀏覽器上傳的檔案複製到希望的位置
        file.transferTo(destFile);
        String finallPath = realPath + File.separator + module + File.separator + fileName;
        return finallPath;
    }

    public void deleteFile(Integer indexR,String module) {
        String fileName = String.valueOf(indexR);
        String filePath = path + File.separator + module + File.separator + fileName;
        File file = new File(filePath + File.separator);
        file.delete();
        logger.info("===== " + filePath + " ===== is delete");
    }

}
