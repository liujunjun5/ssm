package com.work.controller;

import com.sun.istack.internal.NotNull;
import com.work.entity.constants.Constants;
import com.work.entity.vo.ResponseVO;
import com.work.enums.ResponseCodeEnum;
import com.work.exception.BusinessException;
import com.work.utils.DateUtils;
import com.work.utils.StringTools;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;




@RestController
@RequestMapping("/file")
public class FileUploadController extends ABaseController{
    @RequestMapping("/uploadImage")
    public ResponseVO uploadImage(@NotNull MultipartFile file) throws IOException{
        //提示：该方法中注释代码为更改前版本，未保证长久稳定高效运行前请勿删除
        //获取当前日期
        String day = DateUtils.format(new Date(), Constants.DATE_PATTERN);
        //获取文件夹路径
        Path folderPath = Paths.get(Constants.PROJECT_FOLDER, Constants.FILE_FOLDER, Constants.FILE_CATEGORY1, day);
        //String folder = Constants.PROJECT_FOLDER + Constants.FILE_FOLDER + Constants.FILE_CATEGORY1 + day;

        //逐层检查文件路径对应文件夹是否为空（更改前的代码不能逐层检查）
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        //创建文件对象
        //File folderFile = new File(folder);
        // //判断文件夹是否为空
        //if (!folderFile.exists()) {
        //  folderFile.mkdirs() ;
        //}
        //获取文件名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String fileSuffix = StringTools.getFileSuffix(fileName);
        //文件名称转换为随机字符串加后缀
        String realFileName = StringTools.getRandomString(Constants.LENGTH_30) + fileSuffix;
        //获取文件路径，以该文件路径创建新文件对象
        //String filePath = folder + "/" + realFileName;
        Path filePath = folderPath.resolve(realFileName);
        //使用MultipartFile的transferTo方法将上传的文件保存到服务器上的指定位置
        file.transferTo(filePath.toFile());
        //file.transferTo(new File(filePath));
        return getSuccessResponseVO(Constants.FILE_CATEGORY1 + day + "/" + realFileName);
    }

    @RequestMapping("/getResource")
    public ResponseVO getResource(HttpServletResponse response, @NotNull String sourceName) throws IOException, BusinessException {
        //判断地址是否安全，不安全抛出异常
        if (!StringTools.pathIsOk(sourceName)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        //获取文件名后缀
        String suffix = StringTools.getFileSuffix(sourceName);
        //设置响应内容
        response.setContentType("image/" + suffix.replace(".", ""));
        //设置响应头中的缓存控制，max-age=2592000表示资源可以在本地缓存864000秒（即10天）
        response.setHeader("Cache-Control", "max-age=2592000");
        String filePathString = readFile(response, sourceName);
        return getSuccessResponseVO(filePathString);
    }

    protected String readFile(HttpServletResponse response, String relativePath) throws BusinessException{
        //获取文件路径
        Path filePath = Paths.get(Constants.PROJECT_FOLDER, Constants.FILE_FOLDER, relativePath);
        //获取文件对象
        File file = filePath.toFile();
        //File file = new File(Constants.PROJECT_FOLDER + Constants.FILE_FOLDER + filePath);
        //判断文件是否为空
        if (!file.exists()) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            byte[] byteData = new byte[1024];
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            throw new BusinessException("读取文件异常", e);
        }
        //获取文件路径
        return filePath.toString();
    }
}
