package com.gabe.blog.web;

import com.gabe.blog.po.Tag;
import com.gabe.blog.service.BlogService;
import com.gabe.blog.service.TagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TagShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                       @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.ListTag();
        if(id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("id", id);
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.ListBlog(pageable, id));
        return "tags";
    }

    @PostMapping("/tags/{id}/search")
    public String tagSearch(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                       @PathVariable Long id, Model model) {
        model.addAttribute("page", blogService.ListBlog(pageable, id));
        return "tags :: blogList";
    }
}
