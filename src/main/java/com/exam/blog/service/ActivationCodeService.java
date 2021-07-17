package com.exam.blog.service;

import com.exam.blog.models.ActivationCode;
import com.exam.blog.models.User;
import com.exam.blog.repository.ActivationCodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.RowSet;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 @author Zhurenko Evgeniy
 */

@Service
@Transactional
public class ActivationCodeService {

    private ActivationCodeRepo activationCodeRepo;
    private UserRepoImpl userService;

    @Autowired
    public void setActivationCodeRepo(ActivationCodeRepo activationCodeRepo) {
        this.activationCodeRepo = activationCodeRepo;
    }

    @Autowired
    public void setUserService(UserRepoImpl userService) {
        this.userService = userService;
    }

    public Long saveActivationCodeUser(User user){
        ActivationCode activationCode = new ActivationCode();
        activationCode.setCode(UUID.randomUUID().toString());
        activationCode.setUsername(user.getUsername());
        activationCode.setPassword(user.getPassword());
        activationCode.setEmail(user.getEmail());
        activationCode.setPhone(user.getPhone());
        activationCodeRepo.save(activationCode);
        return activationCode.getId();
    }

    public ActivationCode getActivationCodeUser(Long idCode){
        return activationCodeRepo.getActivationCodeById(idCode);
    }

    public Long activateUser(String code){
        ActivationCode activationCodeDB = activationCodeRepo.getActivationCodeByCode(code);
        if(activationCodeDB != null){
            User user = new User();
            user.setUsername(activationCodeDB.getUsername());
            user.setPassword(activationCodeDB.getPassword());
            user.setEmail(activationCodeDB.getEmail());
            user.setPhone(activationCodeDB.getPhone());
            userService.saveBoolean(user);
            Long id = userService.getUserByUserName(user.getUsername()).getId();
            activationCodeRepo.deleteActivationCodeById(activationCodeDB.getId());
            return id;
        } else {
            return 0L;
        }
    }

    public User getUser(String token) {
        String username = activationCodeRepo.findAll().stream().filter(code -> code.getCode().equals(token))
                .collect(Collectors.toList()).get(0).getUsername();
        if(username != null){
            return userService.getUserByUserName(username);
        } else
            return null;
    }
}
