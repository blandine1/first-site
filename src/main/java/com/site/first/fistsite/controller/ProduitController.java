package com.site.first.fistsite.controller;

import com.site.first.fistsite.DAO.CategorieRepository;
import com.site.first.fistsite.DAO.ProduitRepository;
import com.site.first.fistsite.entities.Categorie;
import com.site.first.fistsite.entities.Produit;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class ProduitController {


    @Autowired
    private ProduitRepository produitRepository ;
    @Autowired
    private CategorieRepository categorieRepository ;

    @Value("${dir.imageProd}")
    private String imagePro;

    @GetMapping(value = "/produit/form")
    public  String showProduitForm(Model model){
        List<Categorie> categorieList=categorieRepository.findAll();
        model.addAttribute("produit", new Produit());
        model.addAttribute("categorieList", categorieList);

        return "produit_form";
    }

   /* @PostMapping(value = "/produit/save")
    public String saveP(@RequestParam("file")MultipartFile file,
                              @RequestParam("pnom")String nom,
                              @RequestParam("qte") Long qte,
                              @RequestParam("prix") Double prix,
                              @RequestParam("desc") String description,
                              @RequestParam("categorie") Categorie categorie){
        Produit produit=new Produit();

        String filName= StringUtils.cleanPath(file.getOriginalFilename());
        if (filName.contains("....")){
            System.out.println("invalid format file");
        }
        try {
            produit.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
        produit.setNom(nom);
        produit.setQte(qte);
        produit.setPrix(prix);
        produit.setDescription(description);
        produit.setCategorie(categorie);
        produitRepository.save(produit);
        return "rediret:/produit/list";
    }*/

    @GetMapping(value = "/produit/list")
    public String getProduit(Model model){
        List<Produit> produitList = produitRepository.findAll();
        model.addAttribute("produitList",produitList);

        return "produits";
    }


    @PostMapping(value = "/produit/save")
    public String saveProduit(@RequestParam("picture")MultipartFile file,
                              Produit produit) throws IOException {

        if (!(file.isEmpty())){
            produit.setPhoto(file.getOriginalFilename());
            produitRepository.save(produit);
        }
        if(!(file.isEmpty())){
            produit.setPhoto(file.getOriginalFilename());
            file.transferTo(new File(imagePro+produit.getId()));
        }

        return "redirect:/produit/list";
    }

    @GetMapping(value = "/getProduit/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhotoProduit(@PathVariable("id") Long id) throws IOException {
        File file= new File(imagePro+id);

        return IOUtils.toByteArray(new FileInputStream(file));
    }

    @GetMapping(value = "/lystById/{id}")
    public String getProduitById(@PathVariable("id") Long id, Model model, String p){
        List<Produit>  listByCategorie = produitRepository.findByCategorie(id);
        model.addAttribute("listByCategorie",listByCategorie);
        String n = categorieRepository.findByCategorie(id);
        model.addAttribute("n",n);

        return "produitSite";
    }

    @GetMapping(value = "/prodtById/{id}")
    public String getOneProduct(@PathVariable("id")Long id, Model model){
        Produit one = produitRepository.getOne(id);
        model.addAttribute("one",one);
        String np = produitRepository.findProduit(id);
        model.addAttribute("np",np);

        return "singleProduit";
    }

    @GetMapping(value = "/getFullSearch")
    public String searchProductFulText(@Param("keyword") String keyword, Model model){
        List<Produit> searchList = produitRepository.search(keyword);
        model.addAttribute("searchList",searchList);
        //System.out.println("/////////////////// "+ keyword );

        return "searchResult";
    }
}
