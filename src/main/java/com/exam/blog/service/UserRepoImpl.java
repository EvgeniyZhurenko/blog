package com.exam.blog.service;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Role;
import com.exam.blog.models.User;
import com.exam.blog.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 @author Zhurenko Evgeniy
 */


@Service
public class UserRepoImpl implements UserDetailsService {

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveBoolean(User user) {
        User userFromDB = userRepo.getUserByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(User user, boolean bool) {
        if (userRepo.existsById(user.getId())) {
            if(!bool) {
                User userExists = userRepo.getUserById(user.getId());
                user.setRoles(userExists.getRoles());
                user.setPassword(userExists.getPassword());
                user.setFoto(userExists.getFoto());
                user.setComments(userExists.getComments());
                Blog blog1 = blogService.getById(7L);
                Blog blog2 = blogService.getById(8L);
                Blog blog = user.getBlogs().stream().toArray(value -> new Blog[value])[0];
                user.getBlogs().add(blog1);
                user.getBlogs().add(blog2);
                user.getBlogs().add(blog);
                userRepo.save(user);

            } else {
                User userExists = userRepo.getUserById(user.getId());
                user.setRoles(userExists.getRoles());
                user.setPassword(userExists.getPassword());
                user.setComments(userExists.getComments());
                Blog blog1 = blogService.getById(7L);
                Blog blog2 = blogService.getById(8L);
                Blog blog = user.getBlogs().stream().toArray(value -> new Blog[value])[0];
                user.getBlogs().add(blog1);
                user.getBlogs().add(blog2);
                user.getBlogs().add(blog);
                userRepo.save(user);
            }
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        User userDelete = userRepo.getUserById(id);
        userRepo.delete(userDelete);
        File userDirectory = new File(uploadPath + "/" + id);
        userDirectory.delete();
    }

    public User getById(Long id) {
        User user = userRepo.getUserById(id);
        return user;
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with " + userName + " not found");
        }
        return user;
    }

    public User getUserByUserName(String userName) {
        User user = userRepo.getUserByUsername(userName);
        return user;
    }

    public String[] userRegistration(User user) {
        String[] attr = new String[6];

        if (user.getFirst_name().equals(""))
            attr[0] = "Заполните поле Ваша имя!";
        if (user.getLast_name().equals(""))
            attr[1] = "Заполните поле Ваша фамилия!";
        if (user.getEmail().equals(""))
            attr[2] = "Заполните поле Ваш e-mail!";
        if (user.getPhone().equals(""))
            attr[3] = "Заполните поле Ваш телефон!";
        if (user.getUsername().equals(""))
            attr[4] = "Заполните поле Ваше логин!";
        if (user.getPassword().equals(""))
            attr[5] = "Заполните поле Ваш пароль!";

        return attr;
    }

    public Float countRating(User user){

        List<Float> ratingAllBlogsList = user.getBlogs().stream().map(Blog::getRating).collect(Collectors.toList());

        if(ratingAllBlogsList.size()== 0){
            return 0F;
        }
        Float avarageRating = ratingAllBlogsList.stream()
                    .reduce((n1, n2) -> Float.valueOf(Float.compare(n1,n2))).get() / ratingAllBlogsList.size();
        return avarageRating;
    }

    public boolean uploadFotoImage(User user, MultipartFile foto) throws IOException {

        if(!foto.isEmpty()) {

            File folder = new File(uploadPath + "/" + user.getId());

            if (!folder.isDirectory()) { // Если текущий каталог не существует
                folder.mkdirs(); // Создать новый каталог
            }

            File[] files = folder.listFiles();

            if(files.length != 0) {

                File[] fotos = Arrays.stream(files).filter(pic -> pic.getName().equals(foto.getOriginalFilename())).toArray(File[]::new);

                if (fotos.length == 0) {

                    for (File file : files) {
                        file.delete();
                    }

                    loadFoto(user, foto, folder);

                } else {

                    loadFoto(user, foto, folder);

                }
            } else {

                loadFoto(user, foto, folder);

            }
            return true;

        } else
            return false;
    }

    private void loadFoto(User user, MultipartFile foto, File folder) throws IOException {


        String filePath =  "images/profiles_images/";

        foto.transferTo(new File(folder, Objects.requireNonNull(foto.getOriginalFilename())));

        String filePathFoto = filePath + user.getId() + "/" + foto.getOriginalFilename();

        user.setFoto(filePathFoto);
    }
}


