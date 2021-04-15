package com.site.first.fistsite.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Categorie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
//    @Lob
//    @Column(columnDefinition = "MEDIUMBLOB")
    private String photo;
    private String description;

    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits=new ArrayList<>();

}
