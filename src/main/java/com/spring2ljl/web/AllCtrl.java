package com.spring2ljl.web;

import com.spring2ljl.util.DateUtil;
import com.spring2ljl.util.Img;
import com.spring2ljl.util.IpUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class AllCtrl {
    @RequestMapping("")
    public String index(HttpServletRequest request, Model model) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath()+"/static/images";
        File file = new File(path);
        File[] files = file.listFiles();
        List imgs=new ArrayList<Img>();
        for (File file1 : files) {
            String[] split = file1.getName().split("-");
            Img img = new Img();
            img.setIp(split[0]);
            img.setTime(DateUtil.format(Long.valueOf(split[1])));
            img.setName(split[2]);
            img.setSize(file1.length()/1024);
            img.setRealName(file1.getName());
            imgs.add(img);
        }
        if(imgs.size()!=0){
            Collections.sort(imgs, new Comparator<Img>() {
                @Override
                public int compare(Img o1, Img o2) {
                    return o2.getTime().compareTo(o1.getTime());
                }
            });
        }
        model.addAttribute("images",imgs);
        return "index";
    }
    @RequestMapping("delete")
    @ResponseBody
    public String delete(@RequestParam(value = "names[]")String[] names) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath()+"/static/images";
        for (String name : names) {
            File file = new File(path + "/" + name);
            file.delete();
        }
        return "1";
    }
    @RequestMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file,HttpServletRequest request) throws IOException {
        String path = ResourceUtils.getURL("classpath:").getPath()+"/static/images";
        String ipAddr = IpUtil.getIpAddr(request);
        int i = file.getOriginalFilename().lastIndexOf(".");
        File file1 = new File(path + "/" + ipAddr+"-"+System.currentTimeMillis() + "-" +System.currentTimeMillis()+file.getOriginalFilename().substring(i) );
        file.transferTo(file1);
        return "1";
    }
}
