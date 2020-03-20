package com.gabe.blog.web.admin;

import com.gabe.blog.form.BlogFrom;
import com.gabe.blog.po.Blog;
import com.gabe.blog.po.User;
import com.gabe.blog.service.BlogService;
import com.gabe.blog.service.TagService;
import com.gabe.blog.service.TypeService;
import com.gabe.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /*
    //不分登入是哪個user的
    @GetMapping("/blogs")
    public String List(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.ListType());
        model.addAttribute("page", blogService.ListBlog(pageable, blog));
        return "/admin/blogs";
    }*/

    //分登入是哪個user的
    @GetMapping("/blogs")
    public String ListByUser(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             BlogQuery blog, HttpSession session, Model model) {
        model.addAttribute("types", typeService.ListType());
        model.addAttribute("page", blogService.ListBlogByUser(pageable, blog, (User) session.getAttribute("user")));
        return "/admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, HttpSession session, Model model) {
        model.addAttribute("page", blogService.ListBlogByUser(pageable, blog, (User) session.getAttribute("user")));
        return "/admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "/admin/blogs-input";
    }


    @GetMapping("/blogs/{id}/input")
    public String input(@PathVariable Long id,  Model model) {
        setTypeAndTag(model);
        Blog b = blogService.getBlog(id);
        b.init();
        model.addAttribute("blog",b);
        return "/admin/blogs-input";
    }


    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.ListType());
        model.addAttribute("tags", tagService.ListTag());
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setViews(0);
        blog.setType(typeService.getType(blog.getTypeId()));
        blog.setTags(tagService.ListTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            b = blogService.saveBlog(blog);
        } else {
            blog.setUpdateTime(new Date());
            b = blogService.updataBlog(blog.getId(),blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "Operation Failed");
        } else {
            attributes.addFlashAttribute("message", "Operation Successfully");
        }
        return "redirect:/admin/blogs";
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "Delete Successfully");
        return "redirect:/admin/blogs";
    }

}


