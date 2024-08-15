package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("9090")
    private String port;
    private static final String ip="http://localhost";

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename=file.getOriginalFilename();//用文件名称存储，先获取
        //定义文件唯一标识,前缀
        String flag= IdUtil.fastSimpleUUID();
        String rootFilePath=System.getProperty("user.dir")+"/springboot/src/main/resources/files/"+flag+"_"+originalFilename;
        //前面是获取文件路径，中间加的是怎么定位到要存入的files文件夹，最后是文件名
        FileUtil.writeBytes(file.getBytes(),rootFilePath);//使用java的io存到文件里,用封装了工具类fileutil的hutool,写入磁盘
        return Result.success(ip+":"+port+"/files/"+flag);//返回结果url
    }

    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response){
        OutputStream os;
        String basePath=System.getProperty("user.dir")+"/springboot/src/main/resources/files/";
        List<String> fileNames=FileUtil.listFileNames(basePath);
        String fileName=fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes=FileUtil.readBytes(basePath+fileName);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){
            System.out.println("文件下载失败");
        }
    }

}
