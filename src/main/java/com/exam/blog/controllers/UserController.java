package com.exam.blog.controllers;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.models.Picture;
import com.exam.blog.models.User;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.PictureService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserRepoImpl userRepo;
    private final BlogService blogService;
    private final PictureService pictureService;
    private final MainController mainController;

    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    @Autowired
    public UserController(UserRepoImpl userRepo, BlogService blogService, PictureService pictureService, MainController mainController) {
        this.userRepo = userRepo;
        this.blogService = blogService;
        this.pictureService = pictureService;
        this.mainController = mainController;
    }


    @GetMapping("all-blogs/{id}")
    public String blogListUser(@PathVariable(value = "id", required = false) Long id_user,
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
        } else {
            model.addAttribute("boolean", false);
            model.addAttribute("title", "Блоги " + userDB.getFirst_name() + " " + userDB.getLast_name());
            model.addAttribute("msg", "У вас пока что нет блогов!");
        }
        return "user/user-all-blogs";
    }

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

    @PostMapping("creat-blog/{id}")
    public String creatBlogUserPost(@ModelAttribute Picture picture,@ModelAttribute Blog blog,
                                    @PathVariable(value = "id", required = false) Long idUser,
                                    @RequestParam(value = "image",required = false) MultipartFile image) throws IOException {

        if(blog != null) {

            blogService.addPropertiesBlog(blog, picture, idUser, image);

            return "redirect:/user/all-blogs/" + idUser;

        } else {

            return "redirect: /user/creat-blog/" +idUser;

        }

    }


    @GetMapping("{id}")
    public String userInfo(@PathVariable(name = "id", required = false) Long idUser,
                           Model model) {

       mainController.account(idUser,model);

        return "user/user_page";
    }

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

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable(value = "id", required = false) Long idUser,
                             Model model){

        model.addAttribute("name", userRepo.getById(idUser).getFirst_name() + " " + userRepo.getById(idUser).getLast_name());
        model.addAttribute("idUser" , idUser);

        model.addAttribute("msg", "Аккаунт " + userRepo.getById(idUser).getUsername() + " удален и больше не доступен!");
        userRepo.delete(idUser);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);

        return "main";
    }

    @GetMapping("blog/{id_user}/{id_blog}/{id_user_blog}")
    public String userBlogShow(@PathVariable(value = "id_user", required = false) Long id_user,
                               @PathVariable(value = "id_blog", required = false) Long id_blog,
                               @PathVariable(value = "id_user_blog", required = false) Long id_user_blog,
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
        model.addAttribute("comment", comment);

        return "user/user-blog";
    }

    @GetMapping(value = {"blog/list/{id}"})
    public String userBlogMain(@PathVariable(value = "id", required = false) Long id_user,
                           Model model) {

        User userDB = userRepo.getById(id_user);
        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , id_user);

        List<Blog> blogs = blogService.getSortListBlogByRating();
        if (blogs.size() == 0) {
            model.addAttribute("list", false);
            model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
        }
        model.addAttribute("user", userRepo.getById(id_user));
        model.addAttribute("blogList", blogs);
        model.addAttribute("boolean", true);
        model.addAttribute("anonymous", false);
        model.addAttribute("blog", false);
        return "blog-list";
    }

//    @GetMapping("blog/range/{id_user}/{id_blog}/{raiting}")
//    public String userBlogShow(@PathVariable(value = "id_blog", required = false) Long id_blog,
//                               @PathVariable(value = "id_user", required = false) Long id_user,
//                                @PathVariable(value = "raiting", required = false) Long raiting){
//        Blog blogDB = blogService.getById(id_blog);
//        if(blogDB.getRating() == 0F){
//            blogDB.setRating(blogDB.getRating() + raiting);
//        } else {
//            blogDB.setRating((blogDB.getRating() + raiting) / 2);
//        }
//        blogService.update(blogDB);
//
//        return "redirect:/user/blog/" + id_user + "/" + id_blog + "/" + blogDB.getUser().getId();
//    }


    @GetMapping("update-blog/{id_user}/{id_blog}")
    public String updateUserBlogGet(@PathVariable(name = "id_user", required = false) Long idUser,
                                 @PathVariable(name = "id_blog", required = false) Long idBlog,
                                 Model model){

        User userDB = userRepo.getById(idUser);
        Blog blogDB = blogService.getById(idBlog);
        Picture picture = new Picture();

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , idUser);

        model.addAttribute("boolean", true);
        model.addAttribute("blog", blogDB);
        model.addAttribute("picture", picture);

        return "user/user-update-blog";
    }

    @PostMapping("update-blog/{id}")
    public String updateUserBlogPost(@ModelAttribute Picture picture,@ModelAttribute Blog blog,
                                     @PathVariable(value = "id", required = false) Long idUser,
                                     @RequestParam(value = "image",required = false) MultipartFile image) throws IOException {

        return blogService.updatePropertiesExsistingBlog(blog,picture,image,idUser,uploadPicturePath);

    }
}
