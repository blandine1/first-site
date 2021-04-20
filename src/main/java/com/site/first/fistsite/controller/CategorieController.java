
package com.site.first.fistsite.controller;

import com.site.first.fistsite.DAO.CategorieRepository;
import com.site.first.fistsite.entities.Categorie;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @Value("${dir.image}")
    private String imageDir;

    @GetMapping(value = "/categorie/form")
    public String showCategoriePage(Model model){
        model.addAttribute("categorie", new Categorie());

        return "categorie_form";
    }

    @PostMapping(value = "/categorie/save")
    public String saveCat(@RequestParam("picture")MultipartFile file, Categorie categorie) throws IOException {

        if(!(file.isEmpty())){
            categorie.setPhoto(file.getOriginalFilename());
            categorieRepository.save(categorie);
        }
        if(!(file.isEmpty())){
            categorie.setPhoto(file.getOriginalFilename());
            file.transferTo(new File(imageDir+categorie.getId()));
        }

        return "redirect:/categorie/list";
    }

    @GetMapping(value = "/getPhoto/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(@PathVariable("id") Long id) throws IOException {

            File f= new File(imageDir+id);
        return IOUtils.toByteArray(new FileInputStream(f));
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

        return "categorie_form";
    }

    @GetMapping(value = "")
    public String getProduitsSite(Model model){
        List<Categorie> allCAtegories =categorieRepository.findAll();
        model.addAttribute("allCAtegories", allCAtegories);

        return "categoriesSite";
    }
}
