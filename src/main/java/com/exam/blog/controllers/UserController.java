package com.exam.blog.controllers;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Picture;
import com.exam.blog.models.User;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.PictureService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserRepoImpl userRepo;
    private final BlogService blogService;
    private final PictureService pictureService;

    @Autowired
    public UserController(UserRepoImpl userRepo, BlogService blogService, PictureService pictureService) {
        this.userRepo = userRepo;
        this.blogService = blogService;
        this.pictureService = pictureService;
    }

    @GetMapping("all-blogs/{id}")
    public String blogListUser(@PathVariable(value = "id", required = false) Long id_user,
                           Model model){
        User userDB = userRepo.getById(id_user);

        List<Blog> userListBlog =blogService.getUserSortListBlogByRating(id_user);

        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , id_user);

        if(userListBlog.size() > 0) {
            model.addAttribute("boolean", true);
            model.addAttribute("title", "Блоги " + userDB.getFirst_name() + " " + userDB.getLast_name());
            model.addAttribute("blogList", userListBlog);
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
    public String creatBlogUserPost(@ModelAttribute Blog blog,
                                    @ModelAttribute Picture picture,
                                    @PathVariable(value = "id", required = false) Long idUser,
                                    @RequestParam(value = "image",required = false) MultipartFile image) throws IOException {

        if(blog != null) {

            pictureService.uploadPictureImage(idUser, blog, image, picture);

            if (picture != null) {
                pictureService.save(picture);

                blogService.addPictureBlog(blog, picture);

                blogService.addDateUserId(blog, idUser);

                blogService.save(blog, picture);

                System.out.println(blog.getPictures().toArray(Blog[]::new)[0].getId());
            } else {

                blogService.addDateUserId(blog, idUser);

                blogService.saveWithoutPicture(blog);
            }

            System.out.println(picture.getBlog().getId());

            return "user/all-blogs/" + idUser;

        } else {

            return "redirect: creat-blog/" +idUser;

        }

    }

    @GetMapping("{id}")
    public String userInfo(@PathVariable(name = "id", required = false) Long idUser,
                           Model model) {

        User userDB = userRepo.getById(idUser);
        model.addAttribute("name", userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("idUser" , idUser);

        model.addAttribute("title", "Аккаунт " + userDB.getFirst_name() + " " + userDB.getLast_name());
        model.addAttribute("user", userDB);
        model.addAttribute("idUser" , idUser);
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
}
