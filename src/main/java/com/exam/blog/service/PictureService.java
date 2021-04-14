package com.exam.blog.service;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Picture;
import com.exam.blog.repository.PictureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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

    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    @Autowired
    public void setPictureRepo(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    public void save(Picture picture) {
        pictureRepo.save(picture);
    }

    public boolean update(Picture picture) {

        if(pictureRepo.existsById(picture.getId())){
            Picture existPicture = pictureRepo.getPictureById(picture.getId());
            existPicture.setUrl_image(picture.getUrl_image());
            existPicture.setName(picture.getName());
            pictureRepo.save(existPicture);
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

    public List<Picture> findAllPicture (){
        return pictureRepo.findAll();
    }

    public List<Picture> findAllPictureByUserId(Long idUser) {
        return pictureRepo.findAll().stream()
                .filter(pic -> pic.getBlog().getUser()
                .getId()==(idUser)).collect(Collectors.toList());
    }

    public boolean updateBlogLoadPictureImage(Long idUser, Blog blog, MultipartFile image, Picture picture) throws IOException {

        if(!image.isEmpty()) {

            File folder = new File(uploadPicturePath + "/" + idUser + "/" + blog.getId());

            if (!folder.isDirectory()) { // Если текущий каталог не существует
                folder.mkdirs(); // Создать новый каталог
            }

            File[] files = folder.listFiles();

            if(files.length != 0) {

                File[] fotos = Arrays.stream(files).filter(pic -> pic.getName().equals(image.getOriginalFilename())).toArray(File[]::new);

                if (fotos.length == 0) {

                    for (File file : files) {
                        file.delete();
                    }

                    updateLoadPicture(idUser, blog, image, folder, picture);

                } else {

                    updateLoadPicture(idUser, blog, image, folder, picture);

                }
            } else {

                updateLoadPicture(idUser, blog, image, folder, picture);

            }
            return true;

        } else
            return false;
    }

    private void updateLoadPicture(Long idUser, Blog blog, MultipartFile image, File folder, Picture picture) throws IOException {


        if(blog.getPictures().size() != 0) {
            Long idPicture = blog.getPictures().get(0).getId();

            picture.setId(idPicture);

            String filePath = "images/blogs_images/";

            image.transferTo(new File(folder, Objects.requireNonNull(image.getOriginalFilename())));

            String filePathFoto = filePath + idUser + "/" + blog.getId() + "/" + image.getOriginalFilename();

            picture.setUrl_image(filePathFoto);

            update(picture);
        } else {

            picture.setId(null);

            String filePath = "images/blogs_images/";

            image.transferTo(new File(folder, Objects.requireNonNull(image.getOriginalFilename())));

            String filePathFoto = filePath + idUser + "/" + blog.getId() + "/" + image.getOriginalFilename();

            picture.setUrl_image(filePathFoto);

            picture.setBlog(blog);

            save(picture);
        }


    }

    public void deletePictureBlog(Blog blogDB) {

        for(Picture picture: findAllPictureByUserId(blogDB.getUser().getId())){
            if(picture.getId() == blogDB.getPictures().get(0).getId()){
                blogDB.getPictures().remove(picture);
                blogService.update(blogDB);
                delete(picture.getId());
                File folder = new File(uploadPicturePath + "/" + blogDB.getUser().getId() + "/" + blogDB.getId());
                File[] files = folder.listFiles();
                if(files.length != 0) {
                    for (File file : files) {
                        file.delete();
                    }
                    folder.delete();
                }
            }
        }

    }

    public void loadPictureImage(Long idUser, Blog blog, MultipartFile image, Picture picture) throws IOException {
        if(!image.isEmpty()) {

            File newFolder = new File(uploadPicturePath + "/" + idUser + "/" + blog.getId());

            if (!newFolder.isDirectory()) { // Если текущий каталог не существует
                newFolder.mkdirs(); // Создать новый каталог
            }

            loadPicture(idUser, blog, image, newFolder, picture);

        } else {

            File newFolder = new File(uploadPicturePath + "/" + idUser + "/" + blog.getId());

            if (!newFolder.isDirectory()) { // Если текущий каталог не существует
                newFolder.mkdirs(); // Создать новый каталог
            }
        }
    }

    private void loadPicture(Long idUser, Blog blog, MultipartFile image, File newFolder, Picture picture) throws IOException {

        String filePath =  "images/blogs_images/";

        image.transferTo(new File(newFolder, Objects.requireNonNull(image.getOriginalFilename())));

        String filePathFoto = filePath + idUser + "/" + blog.getId() + "/" + image.getOriginalFilename();

        picture.setUrl_image(filePathFoto);
    }

}
