package com.site.first.fistsite.controller;

import com.site.first.fistsite.DAO.RoleRepository;
import com.site.first.fistsite.DAO.UserRepository;
import com.site.first.fistsite.entities.Role;
import com.site.first.fistsite.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;



    @GetMapping(value = "/user/form")
    public String showUsqerForm(Model model){
        model.addAttribute("user",new User());
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("roleList", roleList);

        return "user_form";
    }

    @PostMapping(value = "/user/save")
    public String saveUser(User user){
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/user/list";
    }

    @GetMapping(value = "/user/list")
    public String getUsers(User user, Model model){
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);

        return "users";
    }

    @GetMapping(value = "/user/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("roleList", roleList);
        User user = userRepository.findById(id).get();
        model.addAttribute("user",user);

        return "user_form";
    }

    @GetMapping(value = "/user/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        userRepository.deleteById(id);

        return "redirect:/user/list";
    }
}
