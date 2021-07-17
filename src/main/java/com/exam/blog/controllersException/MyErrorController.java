package com.exam.blog.controllersException;

import com.exam.blog.models.User;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 @author Zhurenko Evgeniy
 */


@Controller
public class MyErrorController implements ErrorController {

    private UserRepoImpl userService;

    @Autowired
    public void setUserService(UserRepoImpl userService) {
        this.userService = userService;
    }

    @RequestMapping("/error")
    public String handleError( HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = "Непредвиденная ситуация";
        Exception ex = (Exception)request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if(ex != null)
            message = ex.toString().split(":")[ex.toString().split(":").length-1];

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        getGuestUserAdminProp(authentication,
                status,
                message,
                model);
        return "error";

    }

    private void getGuestUserAdminProp(Authentication authentication, Object status, Object message,
                                        Model model){
        Integer statusCode = Integer.valueOf(status.toString());
        if(authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            model.addAttribute("guest_person", true);
        }
        else if(authentication.getAuthorities().stream().anyMatch(role -> role.toString()
                .equalsIgnoreCase("ROLE_USER"))) {
            User user = userService.getUserByUserName(authentication.getName());
            model.addAttribute("user_person", true);
            model.addAttribute("user", user);
            model.addAttribute("idUser", user.getId());
        } else if(authentication.getAuthorities().stream().anyMatch(role -> role.toString()
                .equalsIgnoreCase("ROLE_ADMIN"))){
            model.addAttribute("admin_person", true);
            model.addAttribute("idAdmin", true);
        }
        if(statusCode == 500) {
            String msg = " INTERNAL_SERVER_ERROR";
            model.addAttribute("message", "Ошибка " + " " + statusCode + " " + msg + " " + message);
        } else if(statusCode == 404){
            String msg = " NOT FOUND";
            model.addAttribute("message", "Ошибка " + " " + statusCode + " " + msg + " " + message);
        } else if(statusCode == 403){
            String msg = " FORBIDDEN";
            model.addAttribute("message", "Ошибка " + " " + statusCode + " " + msg + " " + message);
        } else {
            model.addAttribute("message", "Ошибка " + " " + statusCode + " " + message);
        }
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
