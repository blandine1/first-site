package com.site.first.fistsite.DAO;

import com.site.first.fistsite.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    @Query("select c.nom from Categorie c where c.id=?1")
    public String findByCategorie(Long id);
}
