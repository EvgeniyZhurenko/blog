package com.exam.blog.repository;

import com.exam.blog.models.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 @author Zhurenko Evgeniy
 */


public interface ActivationCodeRepo extends JpaRepository<ActivationCode, Long> {

    ActivationCode getActivationCodeById(Long id);

    void deleteActivationCodeById(Long id);

    ActivationCode getActivationCodeByCode(String code);
}
