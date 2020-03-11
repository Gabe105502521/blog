package com.gabe.blog.web.admin;

import com.gabe.blog.po.Tag;
import com.gabe.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String List(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable,
                       Model model) {
        model.addAttribute("page",tagService.ListTag(pageable));
        return "/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "/admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "Duplicated Tags");
        }
        if (result.hasErrors()) {
            return "/admin/tags-input";
        }
        Tag tag1 = tagService.saveTag(tag);
        if (tag == null) {
            attributes.addFlashAttribute("message", "Add Failure");
        } else {
            attributes.addFlashAttribute("message", "Add Successfully");

        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if(t != null) {
            result.rejectValue("name", "nameError", "duplicated tags");
        }
        if(result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tag1 = tagService.updateTag(id, tag);
        if (tag1 == null) {
            attributes.addFlashAttribute("message", "Update failure");
        } else {
            attributes.addFlashAttribute("message", "Update successfully");

        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deletePost (@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "Delete successfully");
        return "redirect:/admin/tags";
    }

}
