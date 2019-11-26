package com.bj.service.impl;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bj.service.UploadService;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    private static final List<String> CONTEXT_TYPES = Arrays.asList("image/gif","image/jpeg","image/png");
    private static final String Url = "http://image.shop.com/";

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String uploadImage(MultipartFile file) {
       try {
           String originalName = file.getOriginalFilename();

           if (!CONTEXT_TYPES.contains(file.getContentType())) {
               return null;
           }

           BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
           if (bufferedImage == null) {
               return null;
           }

//           File path = new File("F:\\office\\img");
//           if(!path.exists()&&!path.isDirectory()){
//               System.out.println("目录或文件不存在！");
//               path.mkdirs();
//           }
//
//           file.transferTo(new File(path+File.separator+originalName));
//
//           return Url+originalName;
             String extension =  FilenameUtils.getExtension(file.getOriginalFilename());
            StorePath stor = this.storageClient.uploadFile(
                    file.getInputStream(), file.getSize(),extension, null);
           return Url+stor.getFullPath();
       }catch(Exception e){
            e.printStackTrace();
       }
        return null;
    }
}
