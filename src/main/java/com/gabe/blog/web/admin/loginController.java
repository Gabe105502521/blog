package com.gabe.blog.web.admin;

import com.gabe.blog.form.UserForm;
import com.gabe.blog.po.User;
import com.gabe.blog.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class loginController {

    @Autowired
    private UserService userService;


    @GetMapping //默認訪問這邊
    public String loginPage () {
        return "/admin/login";
    }

    @PostMapping("/login")
    public String login (@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session,
                         RedirectAttributes attributes) {
        User user = userService.checkUser(email, password);
        if (user != null) {
            user.setPassword(null); //不把密碼傳到前面去
            session.setAttribute("user", user);
            return "redirect:/admin/blogs";
        } else {
            //用戶名不對需要給前端提示，redirect返回的需用這個，用model的方式會拿不到，入們那有提到
            attributes.addFlashAttribute("message", "email or password is invalid");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
    @GetMapping("/register")
    public String registerPage () {
        return "admin/register";
    }

    @PostMapping("/register")
    public String register (UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userService.saveUser(user);
        return "redirect:/admin";
    }


}
