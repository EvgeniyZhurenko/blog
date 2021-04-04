package com.exam.blog.service;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Picture;
import com.exam.blog.models.User;
import com.exam.blog.repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

//    public void saveWithoutPicture(Blog blog){
//        blogRepo.save(blog);
//    }

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

    public Blog getBlogByTitle(String title){
        return blogRepo.getBlogByTitle(title);
    }

    public List<Blog> getAllBlog(){
        return blogRepo.findAll();
    }

    public List<Blog> getSortListBlogByRating(){
        return getAllBlog().stream().sorted((blog1,blog2) -> Float.compare(blog2.getRating(), blog1.getRating()))
                .collect(Collectors.toList());
    }

    public List<Blog> getUserSortListBlogByRating(Long idUser){

        return blogRepo.findAll().stream().filter(blog -> blog.getUser().getId() == idUser)
                        .sorted((blog1, blog2) -> (blog2.getUser().getId().compareTo(blog1.getUser().getId())))
                        .collect(Collectors.toList());

//        return userRepo.getById(idUser).getBlogs().stream()
//                        .sorted((blog1, blog2) -> (int) (blog2.getRating()- blog1.getRating()))
//                        .collect(Collectors.toList());
    }

//    public void addPictureBlog(Blog blog, Picture picture){
//
//        if(blog.getPictures() != null){
//            blog.getPictures().add(picture);
//        } else{
//            Set<Picture> pictureSet = new HashSet<>();
//            pictureSet.add(picture);
//            blog.setPictures(pictureSet);
//        }
//    }
//
//    public void addDateUserId(Blog blog, Long idUser){
//        blog.setDate_create_blog(LocalDate.now());
//        blog.setUser(userRepo.getById(idUser));
//    }

}
