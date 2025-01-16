package huy.module4course06.controller;

import huy.module4course06.model.Blog;
import huy.module4course06.model.Customer;
import huy.module4course06.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/blogs")
public class Exercise3Controller {
    @Autowired
    private IBlogService blogService;

    @GetMapping("")
    public String index(Model model) {
        List<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);
        return "/exercise3/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("blog", new Blog());
        return "/exercise3/create";
    }

    @PostMapping("/save")
    public String save(Blog blog) {
        blogService.save(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findById(id));
        return "/exercise3/update";
    }

    @PostMapping("/update")
    public String update(Blog blog) {
        blog.setPublishDate(LocalDateTime.now());
        blogService.save(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findById(id));
        return "/exercise3/delete";
    }

    @PostMapping("/delete")
    public String delete(Blog blog, RedirectAttributes redirect) {
        blogService.remove(blog.getId());
        redirect.addFlashAttribute("success", "Removed blog successfully!");
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findById(id));
        System.out.println(blogService.findById(id).getPublishDate());
        return "/exercise3/view";
    }
}
