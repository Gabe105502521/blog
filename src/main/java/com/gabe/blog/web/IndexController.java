package com.gabe.blog.web;

import com.gabe.blog.service.BlogService;
import com.gabe.blog.service.TypeService;
import com.gabe.blog.vo.BlogQuery2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/")
    public String IndexPage(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        model.addAttribute("types", typeService.ListType());
        model.addAttribute("page", blogService.ListBlog(pageable));
        return "/index";
    }

    @PostMapping("/index/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery2 blog, Model model) {
        model.addAttribute("page", blogService.ListBlog(pageable, blog));
        return "/index :: blogList";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }
}
