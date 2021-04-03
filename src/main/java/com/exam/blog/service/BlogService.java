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


    public void save(Blog blog, Picture picture) {

        blogRepo.save(blog);

        Blog blogDB = blogRepo.getBlogByTitle(blog.getTitle());

        Picture pictureDB = pictureService.getByName(picture.getName());

        System.out.println(blogDB.toString());

        pictureDB.setBlog(blogDB);

        pictureService.update(pictureDB);

        System.out.println(picture.toString());
    }

    public void saveWithoutPicture(Blog blog){
        blogRepo.save(blog);
    }

    public boolean update(Blog blog) {
        Blog blogDB = blogRepo.getBlogById(blog.getId());
        if(blogDB != null){
            blogRepo.save(blog);
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
        return userRepo.getById(idUser).getBlogs().stream()
                        .sorted((blog1, blog2) -> (int) (blog2.getRating()- blog1.getRating()))
                        .collect(Collectors.toList());
    }

    public void addPictureBlog(Blog blog, Picture picture){

        if(blog.getPictures() != null){
            blog.getPictures().add(picture);
        } else{
            Set<Picture> pictureSet = new HashSet<>();
            pictureSet.add(picture);
            blog.setPictures(pictureSet);
        }
    }

    public void addDateUserId(Blog blog, Long idUser){
        blog.setDate_create_blog(LocalDate.now());
        blog.setUser(userRepo.getById(idUser));
    }

}
