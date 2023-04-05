package com.isep.acme.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.isep.acme.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.sku=:sku")
    void deleteBySku(@Param("sku") String sku);

}
