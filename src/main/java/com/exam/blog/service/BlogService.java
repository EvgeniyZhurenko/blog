package com.exam.blog.service;


import com.exam.blog.models.Blog;
import com.exam.blog.models.User;
import com.exam.blog.repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 @author Zhurenko Evgeniy
 */

@Service
public class BlogService {

    private BlogRepo blogRepo;
    private UserRepoImpl userRepo;

    @Autowired
    public void setUserRepo(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setBlogRepo(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }


    public void save(Blog blog, Long idUser) {
        blogRepo.save(blog);

        User user = userRepo.getById(idUser);
        user.getBlogs().add(blog);
        userRepo.update(user);
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

    public List<Blog> getAllBlog(){
        return blogRepo.findAll();
    }

    public List<Blog> getSortListBlogByRating(){
        return getAllBlog().stream().sorted((blog1,blog2) -> blog2.getRating() - blog1.getRating())
                .collect(Collectors.toList());
    }

    public List<Blog> getUserSortListBlogByRating(Long idUser){
        return userRepo.getById(idUser).getBlogs().stream()
                        .sorted((blog1, blog2) -> blog2.getRating()- blog1.getRating())
                        .collect(Collectors.toList());
    }
}
