package com.exam.blog.service;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.models.User;
import com.exam.blog.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * author Zhurenko Evgeniy
 */

@Service
public class CommentService {

    private CommentRepo commentRepo;
    private BlogService blogService;
    private UserRepoImpl userRepo;

    @Autowired
    public void setUserRepo(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public CommentService() {
    }

    @Autowired
    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment) {

        comment.setBanComment(false);
        commentRepo.save(comment);

    }

    public boolean update(Comment comment) {
        Comment commentDB = commentRepo.getCommentById(comment.getId());
        if(commentDB != null) {
            commentRepo.saveAndFlush(comment);
            return true;
        }
        return false;
    }

    public void delete(Long id) {

        Comment commentDB = commentRepo.getCommentById(id);
        Blog blogDB = blogService.getById(commentDB.getBlog().getId());
        User userDB = userRepo.getById(commentDB.getUser().getId());
        blogDB.getComments().remove(commentDB);
        userDB.getComments().remove(commentDB);
        commentRepo.deleteById(id);
    }

    public Comment getById(Long id) {
        return commentRepo.getCommentById(id);
    }

    public List<Comment> findAllCommentsBlog(Long idBlog){
        return commentRepo.findAll().stream()
                          .filter(comment -> comment.getBlog().getId()==(idBlog))
                          .collect(Collectors.toList());
    }

    public List<Comment> findCommentBySearch(String search){
        return commentRepo.findAll().stream().filter(comment -> findPropsComment(comment,search))
                .collect(Collectors.toList());
    }

    public boolean findPropsComment(Comment comment, String search){
        Field[] fields = comment.getClass().getDeclaredFields();
        boolean bool = false;
        for(Field field : fields){
            try{
                field.setAccessible(true);
                if(field.get(comment) == null){
                    continue;
                }
                if(field.get(comment).getClass() == Long.class){
                    continue;
                }
                if(field.get(comment).getClass() == Boolean.class){
                    continue;
                }
                if(field.getName().toLowerCase().equals(search.toLowerCase()))
                    continue;
                if(field.getName() == "dateCreateComment"){
                    LocalDateTime get = (LocalDateTime) field.get(comment);
                    if(get.toString().contains(search)){
                        bool = true;
                        break;
                    }
                }
                if(field.get(comment).getClass() == String.class) {
                    String get = (String) field.get(comment);
                    bool = get.toLowerCase().contains(search.toLowerCase());
                    if (bool) break;
                }
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return bool;
    }
}
