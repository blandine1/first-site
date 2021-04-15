package com.site.first.fistsite.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private long qte;
    private Double prix;
//    @Lob
//    @Column(columnDefinition = "MEDIUMBLOB")
    private String photo;
    private String description;

    @ManyToOne
    private Categorie categorie;

}
