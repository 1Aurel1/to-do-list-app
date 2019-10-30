package com.todolistatis.todolist.controller;

import javax.validation.Valid;

import com.todolistatis.todolist.model.ConfirmationToken;
import com.todolistatis.todolist.model.User;
import com.todolistatis.todolist.repository.ConfTokenRepo;
import com.todolistatis.todolist.service.IUserService;
import com.todolistatis.todolist.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/users")
public class UsersController {

    private final static String PREFIX = "users/";

    IUserService userService;
    ConfTokenRepo tokenDao;
    MailService mailSender;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersController(IUserService theUserService, MailService theMailService, ConfTokenRepo theConfTokenRepo, BCryptPasswordEncoder theBCryptPasswordEncoder) {

        userService = theUserService;
        tokenDao = theConfTokenRepo;
        bCryptPasswordEncoder = theBCryptPasswordEncoder;
        mailSender = theMailService;

    }

    @GetMapping("")
    public String user(Model theModel) {

        final Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (user instanceof UserDetails) {
            username = ((UserDetails) user).getUsername();
        } else {
            username = user.toString();
        }

        theModel.addAttribute("title", "Welcome " + username);
        theModel.addAttribute("user", userService.getOne(username));

        return "users/user";
    }

    @GetMapping("/login")
    public String login(Model theModel) {

        theModel.addAttribute("title", "Welcome");
        return PREFIX + "login";
    }

    @GetMapping("/register")
    public String newUser(Model theModel) {

        theModel.addAttribute("title", "Register");
        theModel.addAttribute("user", new User());

        return PREFIX + "register";

    }

    @PostMapping("/save")
    public String newUser(@Valid @ModelAttribute("user") User tempUser, BindingResult result, Model theModel) {

        System.err.println(result);

        if (result.hasErrors())
            return "/users/register";

        User existingUser = userService.findByEmailIgnoreCase(tempUser.getEmail());

        if (existingUser != null) {

            return "/users/login";
        }


        String encodedPassword = bCryptPasswordEncoder.encode(tempUser.getPassword());
        tempUser.setPassword(encodedPassword);

        System.out.println("\n\n\n " + tempUser + "\n\n\n");

        userService.save(tempUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(tempUser);

        tokenDao.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(tempUser.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("aurel.marishta@atis.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/users/confirm-account?token=" + confirmationToken.getConfirmationToken());

        System.out.println(mailMessage);

        mailSender.sendEmail(mailMessage);

        theModel.addAttribute("user", tempUser);


        return PREFIX + "successfulRegisteration";


    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model theModel, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = tokenDao.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userService.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userService.save(user);
            return "redirect:/login";
        } else {
            return "";
        }


    }

}
