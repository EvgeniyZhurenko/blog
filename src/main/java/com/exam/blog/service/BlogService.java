package com.exam.blog.service;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Picture;
import com.exam.blog.models.User;
import com.exam.blog.repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 @author Zhurenko Evgeniy
 */

@Service
public class BlogService {

    private BlogRepo blogRepo;
    private UserRepoImpl userRepo;
    private PictureService pictureService;

    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Autowired
    public void setUserRepo(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setBlogRepo(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }


    public void save(Blog blog) {
        blogRepo.save(blog);
    }

    public boolean update(Blog blogDB) {
        if(blogDB != null){
            blogRepo.save(blogDB);
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        blogRepo.deleteById(id);
    }

    public Blog getById(Long id) {
        return blogRepo.getBlogById(id);
    }


    public List<Blog> getAllBlog(){
        return blogRepo.findAll();
    }

    public List<Blog> getSortListBlogByRating(){
        return getAllBlog().stream().sorted((blog1,blog2) -> Float.compare(blog2.getRating(), blog1.getRating()))
                .collect(Collectors.toList());
    }

    public List<Blog> getUserSortListBlogByRating(Long idUser){

        return userRepo.getById(idUser).getBlogs().stream()
                .sorted((blog1, blog2) -> (int) (blog2.getRating()- blog1.getRating()))
                .collect(Collectors.toList());
    }

    public void addPropertiesBlog(Blog blog, Picture picture, Long idUser, MultipartFile image) throws IOException {

        if(picture != null){

            picture.setId(null);

           addProperties(blog, idUser);

            pictureService.uploadPictureImage(idUser, blog, image, picture);

            picture.setBlog(blog);

            pictureService.save(picture);

        } else {

            addProperties(blog, idUser);
        }
    }

    public void addProperties(Blog blog, Long idUser){

        blog.setId(null);

        User userDB = userRepo.getById(idUser);

        blog.setUser(userDB);

        blog.setDate_create_blog(LocalDate.now());

        blog.setRating(0F);

        save(blog);
    }

    public void updatePropertiesBlog(Blog blog, Picture picture, Long idUser, MultipartFile image) throws IOException{
        if(picture != null){
           pictureService.uploadPictureImage(idUser, blog, image, picture);

        }
    }

    public String updatePropertiesExsistingBlog(Blog blog, Picture picture, MultipartFile image, Long idUser, String uploadPicturePath) throws IOException {

        Blog blogDB = getById(blog.getId());
        if(blogDB != null) {

            blog.setRating(blogDB.getRating());
            blog.setDate_create_blog(blogDB.getDate_create_blog());
            blog.setUser(blogDB.getUser());
            blog.setBan_blog(blogDB.getBan_blog());
            blog.setComments(blogDB.getComments());
            blog.setPictures(blogDB.getPictures());

            if(picture.getName() != null){

                blog.getPictures().toArray(Picture[]::new)[0].setName(picture.getName());
            }
            if(image != null){
                File folder = new File(uploadPicturePath + "/" + idUser + "/" + blog.getId());

                File[] files = folder.listFiles();

                if(files.length != 0){
                    for(File file : files){
                        file.delete();
                    }
                    image.transferTo(new File(folder, Objects.requireNonNull(image.getOriginalFilename())));
                }
            }

//            updatePropertiesBlog(blog, picture, idUser, image);

            update(blog);
            return "redirect:/user/blog/" + idUser + "/" + blog.getId() + "/" + blog.getUser().getId();

        } else {

            return "redirect:/user/update-blog/" +idUser + "/" + blog.getId();

        }
    }

}
