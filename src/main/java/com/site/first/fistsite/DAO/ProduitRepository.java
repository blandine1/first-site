package com.site.first.fistsite.DAO;

import com.site.first.fistsite.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("select p from Produit p where categorie_id=?1")
    public List<Produit> findByCategorie(Long id);

    @Query("select p.nom from Produit p where p.id=?1")
    public String findProduit(Long id);

    @Query(value = "SELECT * from Produit where MATCH(nom,description) AGAINST (?1)", nativeQuery = true)
    public List<Produit> search(String keyWord);

}
