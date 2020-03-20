package com.gabe.blog.web.admin;

import com.gabe.blog.form.SettingForm;
import com.gabe.blog.po.User;
import com.gabe.blog.service.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class SettingController {

    private String avatar_name;
    @Autowired
    private UserService userService;
    private String UPLOADED_FOLDER =  "E:/spring-boot/blog/src/main/resources/static/upload/";

    @GetMapping("/setting")
    public String settingPage(HttpSession session, Model model) {
        //User sessionUser = (User) session.getAttribute("user");
        //User user2 = userService.getUser(sessionUser.getId());
        //User user = (User)session.getAttribute("user");
        model.addAttribute("user", session.getAttribute("user"));
        return "/admin/setting";
    }

    @PostMapping("/setting")
    public String settingPost(@Valid SettingForm settingForm, BindingResult result, HttpSession session, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for(FieldError error: fieldErrors) {
                //取出是哪個屬性值報錯、提示、每種錯誤都有給code(類型)
                System.out.println(error.getField() + " : " + error.getDefaultMessage() + "  "+error.getCode());
            }
            return "redirect:/admin/setting";
        }
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.checkUser(sessionUser.getEmail(), settingForm.getOldPassword());
        if (user == null) {
            attributes.addFlashAttribute("wrongPasswordMessage", "Please enter correct old password.");
            return "redirect:/admin/setting";
        }
        BeanUtils.copyProperties(settingForm, user);
        user.setUpdateTime(new Date());
        User u= userService.saveUser(user);
        if (u == null) {
            attributes.addFlashAttribute("message", "Update Failure");
        } else {
            attributes.addFlashAttribute("message", "Update Successfully");
        }
        session.setAttribute("user", user);
        return "redirect:/admin/setting";
    }

    @PostMapping("/setting/avatar")
    @ResponseBody
    public ResponseEntity<?> uploadFileMulti(
    //public String uploadFileMulti(
            @RequestParam("avatar") MultipartFile[] uploadfiles, HttpSession session) {


        // 取得檔案名稱
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            //return new ResponseEntity("請選擇檔案!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = (User) session.getAttribute("user");
        User user2 = userService.getUser(user.getId());
        user2.setAvatar("/upload/"+avatar_name);
        user.setAvatar("/upload/"+avatar_name); //給session

        userService.updateUser(user2.getId(), user2);
        session.setAttribute("user", user);
        //return "redirect:/admin/setting";
        return new ResponseEntity("成功上傳 - "+ uploadedFileName, HttpStatus.OK);
    }

    //將檔案儲存
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //繼續下一個檔案
            }
            avatar_name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }
}
