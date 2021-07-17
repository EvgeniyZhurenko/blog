package com.exam.blog.controllers;

import com.exam.blog.models.*;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.CommentService;
import com.exam.blog.service.PictureService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 @author Zhurenko Evgeniy
 */

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserRepoImpl userRepo;
    private final BlogService blogService;
    private final PictureService pictureService;
    private final CommentService commentService;

    private final MainController mainController;

    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    @Autowired
    public UserController(UserRepoImpl userRepo, BlogService blogService, PictureService pictureService, CommentService commentService, MainController mainController) {
        this.userRepo = userRepo;
        this.blogService = blogService;
        this.pictureService = pictureService;
        this.commentService = commentService;
        this.mainController = mainController;
    }


    // access to all blogs of site
    @GetMapping("all-blogs/{id}")
    public String blogListUser(@PathVariable(value = "id", required = false) Long id_user,
                                @RequestParam(name = "data", required = false) String data,
                                Model model){
        User userDB = userRepo.getById(id_user);

        List<Blog> blogs = userDB.getBlogs();

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , id_user);

        if(userDB.getBlogs().size() > 0) {
            model.addAttribute("boolean", true);
            model.addAttribute("title", "Блоги " + userDB.getFirst_name() + " " + userDB.getLast_name());
            model.addAttribute("blogList", blogs);
            model.addAttribute("user", userDB);
            if(data.equals("rating")) {
                model.addAttribute("blogList", blogService.getSortUserListBlogByRating(blogs));
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
        } else {
            model.addAttribute("boolean", false);
            model.addAttribute("title", "Блоги " + userDB.getFirst_name() + " " + userDB.getLast_name());
            model.addAttribute("user", userDB);
            model.addAttribute("msg", "У вас пока что нет блогов!");
        }
        return "user/user-all-blogs";
    }

    // access to the page for creat blog
    @GetMapping("creat-blog/{id}")
    public String creatBlogUserGet(@PathVariable(value = "id", required = false) Long id_user,
                           Model model){

        User userDB = userRepo.getById(id_user);
        Blog blog = new Blog();
        Picture picture = new Picture();

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , id_user);
        model.addAttribute("boolean", true);
        model.addAttribute("title", "Создание блога");
        model.addAttribute("blog", blog);
        model.addAttribute("picture", picture);
        model.addAttribute("user", userDB);
        return "user/creat-blog";
    }

    // create blog
    @PostMapping("creat-blog/{id}")
    public String creatBlogUserPost(@ModelAttribute Picture picture,@ModelAttribute Blog blog,
                                    @PathVariable(value = "id", required = false) Long idUser,
                                    @RequestParam(value = "image",required = false) MultipartFile image,
                                    @RequestParam String[] ingredient) throws IOException {

        if(blog != null) {

            blogService.addPropertiesBlog(blog, picture, idUser, image, ingredient);

            return "redirect:/user/all-blogs/" + idUser + "?data=rating";

        } else {

            return "redirect: /user/creat-blog/" +idUser;

        }

    }

    // access to the user`s page
    @GetMapping("{id}")
    public String userInfo(@PathVariable(name = "id", required = false) Long idUser,
                           Model model) {

       mainController.account(idUser,model);

        return "user/user_page";
    }

    // access to the update page of user
    @GetMapping("update/{id}")
    public String userUpdateGet(@PathVariable(name = "id", required = false) Long idUser,
                           Model model){

        User userDB = userRepo.getById(idUser);

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , idUser);

        model.addAttribute("title", "Страница редактирования данных пользователя");
        model.addAttribute("user", userDB);

        Float rayting = userRepo.countRating(userDB);
        if (rayting > 0)
            model.addAttribute("rating", rayting);

        return "user/user_update_page";
    }

    // update information of user
    @PostMapping("update")
    public String userUpdatePost(@ModelAttribute User user, Model model,
                                 @RequestParam(name = "image", required = false) MultipartFile foto)
            throws IOException {

        boolean bool = userRepo.uploadFotoImage(user, foto);

        User userDB = userRepo.getById(user.getId()) ;

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , user.getId());

            if(userDB != null) {

                userRepo.update(user, bool);

                return "redirect: " + user.getId();
            } else {
                return "redirect: update";
            }
        }

    // delete user`s account
    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable(value = "id", required = false) Long idUser,
                             Model model){

        model.addAttribute("name", userRepo.getById(idUser).getFirst_name() + " " + userRepo.getById(idUser).getLast_name());
        model.addAttribute("idUser" , idUser);
        model.addAttribute("title", "Удаление пользователя" + userRepo.getById(idUser).getUsername());

        model.addAttribute("msg", "Аккаунт " + userRepo.getById(idUser).getUsername() + " удален и больше не доступен!");
        userRepo.delete(idUser);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);

        return  "redirect:/main";
    }

    // access to the blog details
    @GetMapping("blog/{id_user}/{id_blog}/{id_user_blog}/{bool}")
    public String userBlogShow(@PathVariable(value = "id_user", required = false) Long id_user,
                               @PathVariable(value = "id_blog", required = false) Long id_blog,
                               @PathVariable(value = "id_user_blog", required = false) Long id_user_blog,
                               @PathVariable(value = "bool", required = false) Boolean bool,
                               Model model){

        User userDB = userRepo.getById(id_user);
        Blog blogDB = blogService.getById(id_blog);
        Comment comment = new Comment();
        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , id_user);

        model.addAttribute("user", userRepo.getById(id_user));
        model.addAttribute("idUserBlog", id_user_blog);
        model.addAttribute("title", "Блог " + blogDB.getTitle());
        model.addAttribute("blog", blogDB);
        model.addAttribute("state", bool);
        model.addAttribute("comment", comment);

        return "user/user-blog";
    }

    // access to the all blogs of user
    @GetMapping(value = {"blog/list/{id}"})
    public String userBlogMain(@PathVariable(value = "id", required = false) Long id_user,
                               @RequestParam(name = "data", required = false) String data,
                               Model model) {

        User userDB = userRepo.getById(id_user);
        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , id_user);
        model.addAttribute("title", "Все блоги");

        List<Blog> blogs = blogService.getSortListBlogByRating();
        if (blogs.size() == 0) {
            model.addAttribute("list", false);
            model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
        }
        model.addAttribute("user", userRepo.getById(id_user));
        model.addAttribute("blogList", blogs);
        model.addAttribute("boolean", true);
        model.addAttribute("blog", false);
        if(data.equals("rating")) {
            model.addAttribute("blogList", blogService.getSortListBlogByRating());
            model.addAttribute("sort", data);
        }
        if(data.equals("alphabet")){
            model.addAttribute("blogList", blogService.getSortListBlogByAlphabet());
            model.addAttribute("sort", data);
        }
        if(data.equals("date")){
            model.addAttribute("blogList", blogService.getSortListBlogByDate());
            model.addAttribute("sort", data);
        }
        return "user/user-account-all-blog-list";
    }

    // access to the page of update blog
    @GetMapping("update-blog/{id_user}/{id_blog}")
    public String updateUserBlogGet(@PathVariable(name = "id_user", required = false) Long idUser,
                                    @PathVariable(name = "id_blog", required = false) Long idBlog,
                                    Model model){

        User userDB = userRepo.getById(idUser);
        Blog blogDB = blogService.getById(idBlog);
        Picture picture = new Picture();

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , idUser);
        model.addAttribute("title", "Редактирование блога ");

        model.addAttribute("boolean", true);
        model.addAttribute("user", userDB);
        model.addAttribute("blog", blogDB);
        model.addAttribute("ingredients", blogDB.getIngredients());
        model.addAttribute("picture", picture);

        return "user/user-update-blog";
    }
    // update blog`s information
    @PostMapping("update-blog/{id}")
    public String updateUserBlogPost(@ModelAttribute Picture picture,@ModelAttribute Blog blog,
                                     @PathVariable(value = "id", required = false) Long idUser,
                                     @RequestParam(value = "image",required = false) MultipartFile image,
                                     @RequestParam(name = "ingredient", required = false) String[] ingredient) throws IOException {

        return blogService.updatePropertiesExsistingBlog(blog,picture,image,idUser,uploadPicturePath, ingredient);

    }

    // delete blog of user
    @GetMapping("delete-blog/{idUser}/{idBlog}")
    public String deleteBlog(@PathVariable(value = "idUser", required = false) Long idUser,
                             @PathVariable(value = "idBlog", required = false) Long idBlog){

        User userDB = userRepo.getById(idUser);
        Blog blogDB = blogService.getById(idBlog);
        blogService.deleteBlog(userDB, blogDB);

        return "redirect:/user/blog/list/" + idUser + "?data=rating";
    }

    // delete picture of blog
    @GetMapping("delete-picture-blog/{idUser}/{idBlog}")
    public String deletePicture(@PathVariable(value = "idUser", required = false) Long idUser,
                                @PathVariable(value = "idBlog", required = false) Long idBlog){

        Blog blogDB = blogService.getById(idBlog);
        pictureService.deletePictureBlog(blogDB);

        return "redirect:/user/blog/" + idUser + "/" + idBlog + "/" + blogDB.getUser().getId() + "/true";
    }

    // site search for user account
    @PostMapping("search/{id}")
    public String search(@RequestParam(name = "search",required = false) String search,
                         @PathVariable(value = "id", required = false) Long idUser,
                         Model model){
        String[] strings = search.split("[ \\,\\.\\;\\:\\-?!\\\"]+");
        List<User> users = new ArrayList<>();
        List<Blog> bloges = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        for(String string : strings) {
            if(users.isEmpty())
                users = userRepo.findUserBySearch(string);
            else
                users.addAll(userRepo.findUserBySearch(string));
            if(bloges.isEmpty())
                bloges = blogService.findBlogBySearch(string);
            else
                bloges.addAll(blogService.findBlogBySearch(string));
            if(comments.isEmpty())
                comments = commentService.findCommentBySearch(string);
            else
                comments.addAll(commentService.findCommentBySearch(search));
        }
        User userDB = userRepo.getById(idUser);

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("user",userDB);

        model.addAttribute("title", "Страница поиска");
        model.addAttribute("idUser", idUser);
        model.addAttribute("users", users);
        model.addAttribute("bloges", bloges);
        model.addAttribute("comments", comments);
        if(users.size() == 0 && bloges.size()== 0 && comments.size() == 0)
            model.addAttribute("msg", "По вашему запросу ничего не найдено");
        return "user/user-search-page";
    }

    // view information about other`s account
    @GetMapping("account/{idUser}/{idAccount}")
    public String accountUser(@PathVariable(value = "idUser", required = false) Long idUser,
                              @PathVariable(value = "idAccount", required = false) Long idAccount,
                              Model model){

        User userDB = userRepo.getById(idUser);
        User account = userRepo.getById(idAccount);

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("user",userDB);
        model.addAttribute("account", account);

        model.addAttribute("title", "Страница поиска");
        return "user/user-account";
    }

    // view all blog of other`s account
    @GetMapping("all-account-blogs/{idUser}/{idAccount}")
    public String listBLogAccount(@PathVariable(value = "idUser", required = false) Long idUser,
                              @PathVariable(value = "idAccount", required = false) Long idAccount,
                              Model model){
        User userDB = userRepo.getById(idUser);
        User account = userRepo.getById(idAccount);

        model.addAttribute("title", "Блоги " + account.getFirst_name() + " " + account.getLast_name());
        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser",idUser);
        model.addAttribute("user", userDB);
        List<Blog> blogs = account.getBlogs();
        if (blogs.size() == 0) {
            model.addAttribute("list", false);
            model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
        }
        model.addAttribute("account", account);
        model.addAttribute("blogList", blogs);
        model.addAttribute("boolean", true);
        model.addAttribute("blog", false);

        return "user/user-account-blog-list";
    }
}
