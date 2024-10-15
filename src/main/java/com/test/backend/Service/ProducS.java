package com.test.backend.Service;

import com.test.backend.Model.Products;
import com.test.backend.Repository.ProductR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProducS {

    private  final ProductR productR;

    @Autowired
    public ProducS(ProductR productR) {
        this.productR = productR;
    }

    public boolean addProduct(Products p) {

        if (p.getName().trim().isEmpty() || p.getPrice() == null || p.getQuantity() == null) {
            return false;
        }
        productR.save(p);
        return true;
    }


    public List<Products> getAllProducts() {
        return productR.findAll();
    }


    public Optional<Products> getProductById(long id) {
        return productR.findById(id);
    }


    public boolean updateProduct(long id, Products p) {
        Optional<Products> existingProductOpt = productR.findById(id);
        if (existingProductOpt.isPresent()) {
            Products existingProduct = existingProductOpt.get();
            existingProduct.setName(p.getName());
            existingProduct.setPrice(p.getPrice());
            existingProduct.setQuantity(p.getQuantity());
            productR.save(existingProduct);
            return true;
        }
        return false;
    }


    public boolean deleteProduct(long id) {
        Optional<Products> existingProductOpt = productR.findById(id);
        if (existingProductOpt.isPresent()) {
            productR.deleteById(id);
            return true;
        }
        return false;
    }
}
