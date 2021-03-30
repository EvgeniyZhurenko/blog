package com.exam.blog.controllers;

import com.exam.blog.models.Blog;
import com.exam.blog.models.User;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/")
public class MainController {


    private final UserRepoImpl userRepo;
    private final BlogService blogService;

    @Autowired
    public MainController(UserRepoImpl userRepo, BlogService blogService) {
        this.userRepo = userRepo;
        this.blogService = blogService;
    }


    @GetMapping(value = {"user/main","","main"})
    public String mainPage( Model model) {
        model.addAttribute("title", "Главная страница");

        String namePage = "";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //проверка на имя пользователя != anonymousUser
        if (!auth.getName().equalsIgnoreCase("anonymousUser")) {

            User userDB = userRepo.getUserByUserName(auth.getName());
            model.addAttribute("anonymous", false);

            // проверка роли пользователя - ROLE_ADMIN
            if (userDB.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"))) {

                namePage = "main-user";

                //проверка на наличи блогов у пользователя
                if (userDB.getBlogs().toArray().length >= 1) {
                    model.addAttribute("blog", true);
                    model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
                    model.addAttribute("idUser", userDB.getId());
                    model.addAttribute("blogList", blogService.getUserSortListBlogByRating(userDB.getId()));
                    model.addAttribute("anonymous", false);
                } else {
                    model.addAttribute("blog", false);
                    model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
                    model.addAttribute("idUser", userDB.getId());
                    model.addAttribute("msg", "У вас пока что нет блогов!");
                    model.addAttribute("anonymous", false);
                }
            }
        } else {

            namePage = "main";

            model.addAttribute("anonymous", true);

            List<Blog> bloges = blogService.getSortListBlogByRating();

            // проверка на наличие блогов вообще на сайте
            if (bloges.size() != 0) {
                model.addAttribute("list", true);
                model.addAttribute("blogList", bloges);
            } else {
                model.addAttribute("list", false);
                model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
            }

        }

        return namePage;
    }






    @GetMapping(value = "metrics")
    public String aboutPage( Model model) {
        model.addAttribute("title", "ТАБЛИЦА МЕР ПРОДУКТОВ");
        model.addAttribute("anonymous", true);
        model.addAttribute("blog", false);
        return "metrics";
    }

    @GetMapping(value = "about")
    public String supportPage( Model model) {
        model.addAttribute("title", "О БЛОГЕ");
        model.addAttribute("anonymous", true);
        model.addAttribute("blog", false);
        return "about";
    }

    @GetMapping("contacts")
    public String contactsPage( Model model) {
        model.addAttribute("title", "Котнакты");
        model.addAttribute("anonymous", true);
        model.addAttribute("blog", false);
        return "contacts";
    }

}
