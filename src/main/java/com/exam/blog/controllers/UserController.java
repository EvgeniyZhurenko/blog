package com.exam.blog.controllers;

import com.exam.blog.models.Blog;
import com.exam.blog.models.User;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserRepoImpl userRepo;
    private final BlogService blogService;

//    @Value("${upload.path}")
//    private String uploadPath;

    @Autowired
    public UserController(UserRepoImpl userRepo, BlogService blogService) {
        this.userRepo = userRepo;
        this.blogService = blogService;
    }

    @GetMapping("blog/{id_user}/{id_blog}")
    public String blogUser(@PathVariable(value = "id_user", required = false) Long id_user,
                           @PathVariable(value = "id_blog", required = false) Long id_blog,
                           Model model){
        User user = userRepo.getById(id_user);
        Blog blog = blogService.getById(id_blog);
        model.addAttribute("boolean", true);
        model.addAttribute("title", blog.getTitle());
        model.addAttribute("blog", blog);
        model.addAttribute("user", user);
        return "blog-list";
    }

    @GetMapping("{id}")
    public String userInfo(@PathVariable(name = "id", required = false) Long idUser,
                           Model model) {

        User user = userRepo.getById(idUser);
        model.addAttribute("title", "Страница пользователя");
        model.addAttribute("user", user);

        return "user/user_page";
    }

    @GetMapping("update/{id}")
    public String userUpdateGet(@PathVariable(name = "id", required = false) Long idUser,
                           Model model){

        User user = userRepo.getById(idUser);
        model.addAttribute("title", "Страница редактирования данных пользователя");
        model.addAttribute("user", user);
        model.addAttribute("rating", userRepo.countRating(user));

        return "user/user_update_page";
    }

    @PostMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String userUpdatePost(@ModelAttribute User user,
                                 @RequestParam(name = "foto", required = false) MultipartFile foto, HttpServletRequest req)
                                {

//        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile");
//        File folder = new File(realPath);
//        if (!folder.isDirectory()){ // Если текущий каталог не существует
//            folder.mkdirs(); // Создать новый каталог
//        }
//        String filePath = "";
//        String oldName = foto.getOriginalFilename(); // Старое имя
//        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length()); //Новое имя
//        try {
//            foto.transferTo(new File(folder,newName)); // сохранить документ
//            filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploadFile/" + newName;
////            return filePath; // Возвращаемся к пути доступа сгенерированного загруженного файла
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        user.setFoto(filePath);
        User userDB = userRepo.getById(user.getId()) ;
        if(userDB != null) {
            userRepo.update(user);
            return "redirect: "+ user.getId();
        } else {
            return "redirect: update/" + user.getId();
        }
    }
}
