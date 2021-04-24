package com.exam.blog.controllers;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.models.User;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.CommentService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/")
public class MainController {


    private final UserRepoImpl userRepo;
    private final BlogService blogService;
    private final CommentService commentService;

    @Autowired
    public MainController(UserRepoImpl userRepo, BlogService blogService, CommentService commentService) {
        this.userRepo = userRepo;
        this.blogService = blogService;
        this.commentService = commentService;
    }


    @GetMapping(value = {"user/main", "", "main","admin/main"})
    public String mainPage(Model model) {
        model.addAttribute("title", "Главная страница");

        String namePage = "";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //проверка на имя пользователя != anonymousUser
        if (!auth.getName().equalsIgnoreCase("anonymousUser")) {

            User userDB = userRepo.getUserByUserName(auth.getName());

            // проверка роли пользователя - ROLE_ADMIN
            if (userDB.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"))) {

                namePage = "user/main-user";

                //проверка на наличи блогов у пользователя
                if (userDB.getBlogs().toArray().length >= 1) {
                    model.addAttribute("blog", true);
                    model.addAttribute("user", userDB);
                    model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
                    model.addAttribute("idUser", userDB.getId());
                    model.addAttribute("blogList", blogService.getUserSortListBlogByRating(userDB.getId()));
                    model.addAttribute("anonymous", false);
                } else {
                    model.addAttribute("blog", false);
                    model.addAttribute("user", userDB);
                    model.addAttribute("name", "Аккаунт " + userDB.getFirst_name() + " " + userDB.getLast_name());
                    model.addAttribute("idUser", userDB.getId());
                    model.addAttribute("msg", "У вас пока что нет блогов!");
                    model.addAttribute("anonymous", false);
                }
            } else if(userDB.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {

                namePage = "admin/admin-menu";

                List<Blog> bloges = blogService.getSortListBlogByRating();

                if (bloges.size() != 0) {
                    model.addAttribute("menu", "Сделайте выбор :");
                    model.addAttribute("list", true);
                    model.addAttribute("blogList", bloges);
                    model.addAttribute("idAdmin", userDB.getId());
                    model.addAttribute("name", "Аккаунт " + userDB.getFirst_name());
                } else {
                    model.addAttribute("list", false);
                    model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
                    model.addAttribute("idAdmin", userDB.getId());
                    model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
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


    @GetMapping(value = {"metrics", "user/metrics", "admin/metrics"})
    public String aboutPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("title", "ТАБЛИЦА МЕР ПРОДУКТОВ");
        addMenu(auth, model);
        return "menu_bar_pages/metrics";
    }

    @GetMapping(value = {"about", "user/about", "admin/about"})
    public String supportPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("title", "О БЛОГЕ");
        addMenu(auth,model);
        return "menu_bar_pages/about";
    }

    @GetMapping(value = {"contacts", "user/contacts", "admin/contacts"})
    public String contactsPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("title", "Котнакты");
        addMenu(auth,model);
        return "menu_bar_pages/contacts";
    }

    @GetMapping(value = "account/{id}")
    public String accountInfo(@PathVariable(value = "id", required = false) Long idUser,
                              Model model) {

        account(idUser, model);
        return "account";
    }

    @GetMapping("account/all-blogs/{id}")
    public String blogListUser(@PathVariable(value = "id", required = false) Long id_user,
                               @RequestParam(name = "data", required = false) String data,
                               Model model) {

        User userDB = userRepo.getById(id_user);
        List<Blog> blogs = userDB.getBlogs();

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser", id_user);

        if (userDB.getBlogs().size() > 0) {
            model.addAttribute("boolean", true);
            model.addAttribute("title", "Блоги " + userDB.getFirst_name() + " " + userDB.getLast_name());
            model.addAttribute("blogList", blogs);
            model.addAttribute("user", userDB);
        } else {
            model.addAttribute("boolean", false);
            model.addAttribute("title", "Блоги " + userDB.getFirst_name() + " " + userDB.getLast_name());
            model.addAttribute("msg", "У вас пока что нет блогов!");
        }
        if(data.equals("rating")) {
            model.addAttribute("blogList", blogService.getSortUserListBlogByDate(blogs));
            model.addAttribute("sort", data);
        }
        if(data.equals("alphabet")){
            model.addAttribute("blogList", blogService.getSortUserListBlogByAlphabet(blogs));
            model.addAttribute("sort", data);
        }
        if(data.equals("date")){
            model.addAttribute("blogList", blogService.getSortUserListBlogByDate(blogs));
            model.addAttribute("sort", data);
        }
        return "account-all-blogs";
    }

    @GetMapping("blog/{id_user}/{id_blog}/{bool}")
    public String blogUser(@PathVariable(value = "id_user", required = false) Long idUser,
                           @PathVariable(value = "id_blog", required = false) Long idBLog,
                           @PathVariable(value = "bool", required = false) Boolean bool,
                           Model model){

        Blog blogDB = blogService.getById(idBLog);
        Comment comment = new Comment();
        model.addAttribute("blog", blogDB );
        model.addAttribute("state", bool);
        model.addAttribute("comment", comment);

        return "blog";
    }

    @PostMapping("search")
    public String search(@RequestParam(name = "search",required = false) String search,
                         Model model){
        List<User> users = userRepo.findUserBySearch(search);
        List<Blog> bloges = blogService.findBlogBySearch(search);
        List<Comment> comments = commentService.findCommentBySearch(search);
        model.addAttribute("title", "Страница поиска");
        model.addAttribute("users", users);
        model.addAttribute("bloges", bloges);
        model.addAttribute("comments", comments);
        if(users.size() == 0 && bloges.size()== 0 && comments.size() == 0)
            model.addAttribute("msg", "По вашему запросу ничего не найдено");
        return "search-page";
    }

    @GetMapping("accessDenied")
    public String accessDenied(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getName().equalsIgnoreCase("anonymousUser")) {
            model.addAttribute("title", "Access denied");
            model.addAttribute("access", "anonymous");
        } else {
            User userDB = userRepo.getUserByUserName(auth.getName());
            if (userDB.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("ROLE_USER"))) {
                model.addAttribute("title", "Access denied");
                model.addAttribute("access", "user");
                model.addAttribute("user", userDB);
                model.addAttribute("name", "Аккаунт " + userDB.getFirst_name() + " " + userDB.getLast_name());
                model.addAttribute("idUser", userDB.getId());
            } else if(userDB.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
                model.addAttribute("title", "Access denied");
                model.addAttribute("access", "admin");
                model.addAttribute("idAdmin", userDB.getId());
                model.addAttribute("name", "Аккаунт " + userDB.getFirst_name());
            }
        }

        return "status/access_denied";
    }

    public void addMenu (Authentication auth, Model model){
        if (auth.getName().equalsIgnoreCase("anonymousUser"))
            model.addAttribute("anonymous", true);
        else {
            User userDB = userRepo.getUserByUserName(auth.getName());
            if(userDB.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"))) {
                model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
                model.addAttribute("idUser", userDB.getId());
                model.addAttribute("User", true);
                model.addAttribute("user", userDB);
            } else if(userDB.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))){
                model.addAttribute("name", "Аккаунт " + userDB.getFirst_name());
                model.addAttribute("idAdmin", userDB.getId());
                model.addAttribute("admin", true);
            }
        }
    }

    public void account(Long idUser, Model model){
        User userDB = userRepo.getById(idUser);
        model.addAttribute("name", "Аккаунт " + " " + userDB.getLast_name());
        model.addAttribute("idUser", idUser);

        model.addAttribute("title", "Аккаунт " + userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("user", userDB);

    }
}
