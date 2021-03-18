package com.site.first.fistsite.controller;

import com.site.first.fistsite.DAO.CategorieRepository;
import com.site.first.fistsite.entities.Categorie;
import com.site.first.fistsite.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping(value = "/categorie/form")
    public String showCategoriePage(Model model){
        model.addAttribute("categorie", new Categorie());

        return "categorie_form";
    }

    @PostMapping(value = "/categorie/save")
    public String saveCat(@RequestParam("file")MultipartFile file,
                          @RequestParam("pnom")String nom,
                          @RequestParam("desc")String description){
        Categorie categorie=new Categorie();
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("...")){
            System.out.println("format de fichier invalid!!!!!");
        }
        try {
            categorie.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        categorie.setNom(nom);
        categorie.setDescription(description);

        categorieRepository.save(categorie);

        return "redirect:/categorie/list";
    }



    @GetMapping(value = "/categorie/list")
    public String getProduits(Model model){
        List<Categorie> allCAtegories =categorieRepository.findAll();
        model.addAttribute("allCAtegories", allCAtegories);

        return "categories";
    }

    @GetMapping(value = "/categorie/delete/{id}")
    public String deleteCate(@PathVariable("id") Long id){
        categorieRepository.deleteById(id);

        return "redirect:/categorie/list";
    }

    @GetMapping(value = "/categorie/edit/{id}")
    public String updateCate(@PathVariable("id") Long id, Model model){
        Categorie categorie = categorieRepository.getOne(id);
        model.addAttribute("categorie", categorie);
       // System.out.println("//////////////////////////////        "+categorie);

        return "categorie_form";
    }

    @GetMapping(value = "")
    public String getProduitsSite(Model model){
        List<Categorie> allCAtegories =categorieRepository.findAll();
        model.addAttribute("allCAtegories", allCAtegories);

        return "categoriesSite";
    }



}
