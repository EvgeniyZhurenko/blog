package com.exam.blog.service;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.models.Picture;
import com.exam.blog.models.User;
import com.exam.blog.repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 @author Zhurenko Evgeniy
 */

@Service
public class BlogService {

    private BlogRepo blogRepo;
    private UserRepoImpl userRepo;
    private PictureService pictureService;
    private CommentService commentService;

    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

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

    public List<Blog> getSortUserListBlogByRating(List<Blog> bloges){
        return bloges.stream().sorted((blog1,blog2) -> Float.compare(blog2.getRating(), blog1.getRating()))
                .collect(Collectors.toList());
    }

    public List<Blog> getSortListBlogByAlphabet(){
        return getAllBlog().stream().sorted((blog1,blog2) -> blog1.getTitle().compareToIgnoreCase(blog2.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Blog> getSortUserListBlogByAlphabet(List<Blog> bloges){
        return bloges.stream().sorted((blog1,blog2) -> blog1.getTitle().compareToIgnoreCase(blog2.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Blog> getSortListBlogByDate(){
        return getAllBlog().stream()
                .sorted(Comparator.comparing(Blog::getDate_create_blog))
                .collect(Collectors.toList());
    }

    public List<Blog> getSortUserListBlogByDate(List<Blog> bloges){
        return bloges.stream()
                .sorted(Comparator.comparing(Blog::getDate_create_blog))
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

            pictureService.loadPictureImage(idUser, blog, image, picture);

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

        blog.setBan_blog(false);

        save(blog);
    }

    public void updatePropertiesBlog(Blog blog, Picture picture, Long idUser, MultipartFile image) throws IOException{
        if(picture != null){
           pictureService.updateBlogLoadPictureImage(idUser, blog, image, picture);

        }
    }

    public String updatePropertiesExsistingBlog(Blog blog, Picture picture, MultipartFile image, Long idUser, String uploadPicturePath) throws IOException {

        Blog blogDB = getById(blog.getId());
        if(blogDB != null) {

            blogDB.setAnnouncement(blog.getAnnouncement());
            blogDB.setTitle(blog.getTitle());
            blogDB.setFull_text(blog.getFull_text());

            if(image != null){
                File folder = new File(uploadPicturePath + "/" + idUser + "/" + blogDB.getId());

                File[] files = folder.listFiles();

                if(files != null) {
                    if (files.length != 0) {
                        for (File file : files) {
                            file.delete();
                        }
                    }
                }
            }

            updatePropertiesBlog(blogDB, picture, idUser, image);

            Picture pictureDB = pictureService.getById(picture.getId());

            if(blogDB.getPictures().size() != 0) {

                blogDB.getPictures().remove(0);
            }

            blogDB.getPictures().add(pictureDB);

            update(blogDB);

            return "redirect:/user/blog/" + idUser + "/" + blogDB.getId() + "/" + blogDB.getUser().getId() + "/true";

        } else {

            return "redirect:/user/update-blog/" +idUser + "/" + blogDB.getId();

        }
    }

    public void deleteBlog(User userDB, Blog blogDB){

        if(commentService.findAllCommentsBlog(blogDB.getId()).size() != 0 || commentService.findAllCommentsBlog(blogDB.getId()) != null) {
            for (Comment comment : commentService.findAllCommentsBlog(blogDB.getId())) {
                blogDB.getComments().remove(comment);
                userDB.getComments().remove(comment);
                userRepo.update(userDB, true);
                commentService.delete(comment.getId());
            }
        }
        deletePicture(blogDB);

        if(blogDB.getId() == userRepo.findBlogById(userDB, blogDB).getId()){

            userDB.getBlogs().remove(blogDB);
        }

        delete(blogDB.getId());
    }

    public void deleteAdminBlog(User userDB, Blog blogDB){

        if(commentService.findAllCommentsBlog(blogDB.getId()).size() != 0 || commentService.findAllCommentsBlog(blogDB.getId()) != null) {
            for (Comment comment : commentService.findAllCommentsBlog(blogDB.getId())) {
                blogDB.getComments().remove(comment);
                userDB.getComments().remove(comment);
                commentService.delete(comment.getId());
            }
        }
        deletePicture(blogDB);

        delete(blogDB.getId());
    }

    public void deletePicture(Blog blogDB){
        if(pictureService.findAllPictureByUserId(blogDB.getUser().getId()).size()!= 0 ||
                pictureService.findAllPictureByUserId(blogDB.getUser().getId()) != null) {
            for (Picture picture : pictureService.findAllPictureByUserId(blogDB.getUser().getId())) {
                if (picture.getId() == blogDB.getPictures().get(0).getId()) {
                    pictureService.delete(picture.getId());
                    pictureService.deleteFolder(blogDB);
                }
            }
        }}

    public List<Blog> findBlogBySearch(String search){
        List<Blog> blogs = blogRepo.findAll();
        return blogs.stream().filter(blog -> findPropsBlog(blog,search)).collect(Collectors.toList());
    }

    public Boolean findPropsBlog(Blog blog, String search){
        Field[] fields = blog.getClass().getDeclaredFields();
        boolean bool = false;
        for(Field field : fields){
            try {
                field.setAccessible(true);
                if(field.get(blog) == null){
                    continue;
                }
                if(field.get(blog).getClass() == Long.class){
                    continue;
                }
                if(field.get(blog).getClass() == Float.class){
                    continue;
                }
                if(field.get(blog).getClass() == Boolean.class){
                    continue;
                }
                if(field.getName().toLowerCase().equals(search.toLowerCase()))
                    continue;
                if(field.getName() == "date_create_blog"){
                    LocalDate get = (LocalDate) field.get(blog);
                    if(get.toString().contains(search)){
                        bool = true;
                        break;
                    }
                }
                if(field.get(blog).getClass() == String.class) {
                    String get = (String) field.get(blog);
                    bool = get.toLowerCase().contains(search.toLowerCase());
                    if (bool) break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bool;
    }
}
