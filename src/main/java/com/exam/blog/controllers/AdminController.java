package com.exam.blog.controllers;

import com.exam.blog.models.User;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(value = {"/admin/"})
public class AdminController {

    private final UserRepoImpl userRepo;
    private final BlogService blogService;
    private final MainController mainController;

    @Autowired
    public AdminController(UserRepoImpl userRepo, BlogService blogService, MainController mainController) {
        this.userRepo = userRepo;
        this.blogService = blogService;
        this.mainController = mainController;
    }

    @GetMapping(value = "account/{id}")
    public String accountAdminInfo(@PathVariable(value = "id", required = false) Long idUser,
                              Model model) {
        mainController.account(idUser,model);

        return "admin/admin-account";
    }

    @GetMapping("update/{id}")
    public String adminUpdateGet(@PathVariable(name = "id", required = false) Long idUser,
                                Model model){

        User userDB = userRepo.getById(idUser);

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , idUser);
        model.addAttribute("title", "Страница редактирования данных АДМИНА.");
        model.addAttribute("user", userDB);

        return "admin/admin-update_page";
    }

    @PostMapping("update")
    public String userUpdatePost(@ModelAttribute User user, Model model,
                                 @RequestParam(name = "image", required = false) MultipartFile foto)
            throws IOException {

        boolean bool = userRepo.uploadFotoImage(user, foto);

        User userDB = userRepo.getById(user.getId());
        user.setUsername(userDB.getUsername());
        user.setFirst_name(userDB.getFirst_name());
        user.setLast_name(userDB.getLast_name());

        model.addAttribute("name", "Аккаутн " + " " + userDB.getFirst_name());
        model.addAttribute("idUser" , user.getId());

        if(userDB != null) {

            userRepo.update(user, bool);

            return "redirect:account/" + user.getId();
        } else {
            return "redirect:update";
        }
    }

    @GetMapping("blog/list/{id}")
    public String allBlogList(@PathVariable(value = "id", required = false) Long idAdmin,
                              Model model){
        User userDB = userRepo.getById(idAdmin);
        model.addAttribute("name", "Аккаунт " + userDB.getFirst_name());
        model.addAttribute("title", "Все блоги");
        model.addAttribute("idAdmin", idAdmin);
        model.addAttribute("blogList", blogService.getSortListBlogByRating());


        return "admin/admin-blog-list";
    }

    @GetMapping("all-accounts/{id}")
    public String allUsersList(@PathVariable(value = "id", required = false) Long idAdmin,
                              Model model){

        User userDB = userRepo.getById(idAdmin);
        model.addAttribute("name", "Аккаунт " + userDB.getFirst_name());
        model.addAttribute("title", "Все пользователи");
        model.addAttribute("idAdmin", idAdmin);
        model.addAttribute("userList", userRepo.sortUserListFirstName());

        return "admin/admin-all-users";
    }

    @GetMapping("user/all-blogs/{id_admin}/{id_user}")
    public String allBlogsUser(@PathVariable(value = "id_admin", required = false) Long idAdmin,
                               @PathVariable(value = "id_user", required = false) Long idUser,
                               Model model){

        User admin = userRepo.getById(idAdmin);
        User userDB = userRepo.getById(idUser);
        model.addAttribute("name", "Аккаунт " +admin.getFirst_name());
        model.addAttribute("title", "Все блоги пользователя :" + userDB.getUsername());
        model.addAttribute("user", userDB);
        model.addAttribute("idAdmin", idAdmin);
        model.addAttribute("blogList", blogService.getUserSortListBlogByRating(idUser));

        return "admin/admin-user-blogs";
    }

    @GetMapping("user/blog/{id_admin}/{id_user}/{id_blog}")
    public String userBlog(@PathVariable(value = "id_admin", required = false) Long idAdmin,
                           @PathVariable(value = "id_user", required = false) Long idUser,
                           @PathVariable(value = "id_blog", required = false) Long idBlog,
                           Model model){

        User adminDB = userRepo.getById(idAdmin);
        User userDB = userRepo.getById(idUser);

        model.addAttribute("name", "Аккаунт " + adminDB.getFirst_name());
        model.addAttribute("title", "Блог пользователя :" + userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("user", userDB);
        model.addAttribute("idAdmin", idAdmin);
        model.addAttribute("blog", blogService.getById(idBlog));

        return "admin/admin-blog";
    }
}
