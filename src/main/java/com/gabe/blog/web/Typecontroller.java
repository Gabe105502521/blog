package com.gabe.blog.web;

import com.gabe.blog.service.BlogService;
import com.gabe.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Typecontroller {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    /*
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                        Model model) {

        model.addAttribute("page", blogService.)
        return "types";
    }*/
}
