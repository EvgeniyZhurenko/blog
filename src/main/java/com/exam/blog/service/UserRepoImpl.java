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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 @author Zhurenko Evgeniy
 */


@Service
@Transactional
public class UserRepoImpl implements UserDetailsService {

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    private BlogService blogService;

    private PictureService pictureService;

    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.picture.path}")
    private String picturePath;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с логином - " + userName + " не удается найти");
        }
        return user;
    }

    public void saveBoolean(User user) {
        User userFromDB = userRepo.getUserByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(false);
            user.setBan_user(false);
            userRepo.save(user);
        } else {
            throw new EntityNotFoundException("Такого пользователя не существует.");
        }
    }

    public void update(User user, boolean bool) {
        User exist = userRepo.getUserById(user.getId());
        if (exist != null) {
            if(!bool) {
                setProps(exist,user);
                userRepo.save(user);
            } else {
                setProps(exist,user);
                userRepo.save(user);
            }
        } else
            throw new EntityNotFoundException("Такого пользователя не существует.");
    }

    public void updatePassword(User user){
        User exist = userRepo.getUserById(user.getId());
        if(exist != null){
            exist.setPassword(user.getPassword());
            userRepo.save(exist);
        } else
            throw new EntityNotFoundException("Такого пользователя не существует.");
    }

    public void delete(Long id) {
        User userDelete = userRepo.getUserById(id);
        List<Blog> bloges = new CopyOnWriteArrayList<>(userDelete.getBlogs());
        if (!bloges.isEmpty()) {

                for(Blog blog : bloges){
                    Blog blogDB = blogService.getById(blog.getId());
                    if(blogDB != null)
                        blogService.deleteBlog(userDelete, blogDB);
                }
            }
        userRepo.deleteUserById(userDelete.getId());
        pictureService.deleteFolderUser(userDelete);
    }


    public User getById(Long id) {
            User user = userRepo.getUserById(id);
            if (user != null)
                return user;
            else
                throw new EntityNotFoundException("Пользователя с id - " + id + " не существует");
    }

    public User getUserByUserName(String userName) {
        User user = userRepo.getUserByUsername(userName);
        return user;
    }

//    public String[] userRegistration(User user) {
//        String[] attr = new String[6];
//
//        if (user.getFirst_name().equals(""))
//            attr[0] = "Заполните поле Вашe имя!";
//        if (user.getLast_name().equals(""))
//            attr[1] = "Заполните поле Ваша фамилия!";
//        if (user.getEmail().equals(""))
//            attr[2]="Заполните поле Ваш e-mail!";
//        if (user.getPhone().equals(""))
//            attr[3] = "Заполните поле Ваш телефон!";
//        if (user.getUsername().equals(""))
//            attr[4]="Заполните поле Ваш логин!";
//        if (user.getPassword().equals(""))
//            attr[5] = "Заполните поле Ваш пароль!";
//
//        return attr;
//    }

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

    public void setProps(User exist, User user){
        user.setRoles(exist.getRoles());
        user.setPassword(exist.getPassword());
//        user.setBan_user(exist.getBan_user());
        if(user.getFoto() == null){
            user.setFoto(exist.getFoto());
        }
    }

    public List<User> sortUserListFirstName(){
        return userRepo.findAll().stream()
                .sorted((u1, u2) -> u1.getFirst_name().compareToIgnoreCase(u2.getFirst_name()))
                .collect(Collectors.toList());
    }

    public Blog findBlogById(User user, Blog blog){

        return user.getBlogs().stream().filter(b -> b.getId() == blog.getId()).collect(Collectors.toList()).get(0);
    }

    public List<User> findUserBySearch(String search){

        List<User> users = userRepo.findAll().stream()
                .filter(user -> user.getId()!= 1)
                .filter(user -> user.getId() != 4)
                .collect(Collectors.toList());
        return  users.stream().filter(user -> findByProps(user,search))
                .collect(Collectors.toList());

    }

    private static Boolean findByProps(User user, String search) {
        Field[] fields = user.getClass().getDeclaredFields();
        boolean bool = false;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if(field.get(user) == null){
                    continue;
                }
                if(field.getName() == "admin" ||  field.getName() == "guest")
                    continue;
                if(field.get(user).getClass() == Long.class){
                    continue;
                }
                if(field.get(user).getClass() == Boolean.class){
                    continue;
                }
                if(field.getName().toLowerCase().equals(search.toLowerCase()))
                    continue;
                if(field.getName() == "born"){
                    Date get = (Date)field.get(user);
                    if(get.toString().contains(search)){
                        bool = true;
                        break;
                    }
                }
                if(field.get(user).getClass() == String.class) {
                    String get = (String) field.get(user);
                    bool = get.toLowerCase().contains(search.toLowerCase());
                    if (bool) break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bool;
    }

    public boolean findUserByUsernameException(String username){
        return userRepo.findAll().stream().map(User::getUsername).noneMatch(name-> name.equals(username));
    }


    public User findUserByEmail(String email){
        return userRepo.findAll().stream().filter(user-> user.getEmail()!= null).collect(Collectors.toList())
                .stream().filter(user -> user.getEmail().equals(email)).toArray(User[]::new)[0];
    }

    public boolean chekEmailAndUsername(String username, String email) {
        User userDBByUsername = userRepo.getUserByUsername(username);
        if(userDBByUsername == null) {
            User userDBByEmail = findUserByEmail(email);
            return userDBByEmail == null;
        }
        else
            return false;
    }

}



