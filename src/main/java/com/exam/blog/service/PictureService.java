package com.exam.blog.service;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Picture;
import com.exam.blog.repository.PictureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * author Zhurenko Evgeniy
 */

@Service
public class PictureService  {

    private PictureRepo pictureRepo;
    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setPictureRepo(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    public void save(Picture picture, Long idBlog) {
        pictureRepo.save(picture);

        Blog blog = blogService.getById(idBlog);
        blog.getPictures().add(picture);
        blogService.update(blog);
    }

    public boolean update(Picture picture) {
        Picture pictureDB = pictureRepo.getPictureById(picture.getId());
        if(pictureDB != null){
            pictureRepo.saveAndFlush(picture);
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        pictureRepo.deleteById(id);
    }

    public Picture getById(Long id) {
        return pictureRepo.getPictureById(id);
    }
}
