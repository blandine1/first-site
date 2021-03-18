package com.site.first.fistsite.controller;


import com.site.first.fistsite.DAO.RoleRepository;
import com.site.first.fistsite.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/role/form")
    public String showsRoleForm(Model model){
        model.addAttribute("role", new Role());

        return "role_form";
    }

    @PostMapping(value = "/role/save")
    public String saveRole(Role role){
        roleRepository.save(role);

        return "redirect:/role/list";
    }

    @GetMapping(value = "/role/list")
    public String GetAllRole(Model model){
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("roleList",roleList);

        return "roles";
    }

    @GetMapping(value = "/role/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Role role = roleRepository.findById(id).get();
        model.addAttribute("role",role);

        return "role_form";
    }

    @GetMapping(value = "/role/delete/{id}")
    public String delete(@PathVariable("id")Long id){

       roleRepository.deleteById(id);

        return "redirect:/role/list";
    }
}
