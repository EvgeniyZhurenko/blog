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



/**
 * author Zhurenko Evgeniy
 */

@Service
public class PictureService  {

    private PictureRepo pictureRepo;

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
            Picture existPicture = pictureRepo.getOne(picture.getId());
            existPicture.setBlog(picture.getBlog());
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

    public boolean uploadPictureImage(Long idUser, Blog blog, MultipartFile image, Picture picture) throws IOException {

        if(!image.isEmpty()) {

            File folder = new File(uploadPicturePath + "/" + idUser);

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

                    loadPicture(idUser, blog, image, folder, picture);

                } else {

                    loadPicture(idUser, blog, image, folder, picture);

                }
            } else {

                loadPicture(idUser, blog, image, folder, picture);

            }
            return true;

        } else
            return false;
    }

    private void loadPicture(Long idUser, Blog blog, MultipartFile image, File folder, Picture picture) throws IOException {


        String filePath =  "images/blogs_images/";

        image.transferTo(new File(folder, Objects.requireNonNull(image.getOriginalFilename())));

        String filePathFoto = filePath + idUser + "/" + blog.getId() + "/" + image.getOriginalFilename();

        picture.setUrl_image(filePathFoto);
    }
}
