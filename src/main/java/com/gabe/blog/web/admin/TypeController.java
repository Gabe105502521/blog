package com.gabe.blog.web.admin;

import com.gabe.blog.po.Type;
import com.gabe.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String List(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
                       Model model) {
        model.addAttribute("page", typeService.ListType(pageable));
        return "/admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "/admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "/admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "duplicated types");
        }
        if (result.hasErrors()) {
            return "/admin/types-input";
        }
        Type type1 = typeService.saveType(type);
        if (type == null) {
            attributes.addFlashAttribute("message", "Add failure");
        } else {
            attributes.addFlashAttribute("message", "Add successfully");

        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if(t != null) {
            result.rejectValue("name", "nameError", "duplicated types");
        }
        if(result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id, type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "Update failure");
        } else {
            attributes.addFlashAttribute("message", "Update successfully");

        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deletePost (@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "Delete successfully");
        return "redirect:/admin/types";
    }
}