package com.test.backend.Repository;

import com.test.backend.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductR extends JpaRepository<Products,Long> {
}
