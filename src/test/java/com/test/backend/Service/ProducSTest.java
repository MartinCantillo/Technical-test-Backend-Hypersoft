package com.test.backend.Service;

import com.test.backend.Model.Products;
import com.test.backend.Repository.ProductR;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducSTest {


    //Testing
    @Mock
    private ProductR productRepository;

    @InjectMocks
    private ProducS productService;

    private Products product;

    private Products product2;

    private Products updatedProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(ProducSTest.class);

        product = new Products();
        product.setId(1);
        product.setName("Product A");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setQuantity(10);

        product2 = new Products();
        product2.setId(2);
        product2.setName("Product B");
        product2.setPrice(BigDecimal.valueOf(150.0));
        product2.setQuantity(100);


        updatedProduct = new Products();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(BigDecimal.valueOf(200.0));
        updatedProduct.setQuantity(50);
    }

    @Test
    void addProduct() {


        when(productRepository.save(any(Products.class))).thenReturn(product);

        boolean result = productService.addProduct(product);

        assertTrue(result);


        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        assertNotNull(productService.getAllProducts());
    }

    @Test
    void getProductById() {

        Optional<Products> productOptional = Optional.of(product2);


        when(productRepository.findById(product.getId())).thenReturn(productOptional);


        Optional<Products> prodResultado = productService.getProductById(product.getId());


        assertTrue(prodResultado.isPresent());
        assertEquals(product2.getId(), prodResultado.get().getId());
    }


    @Test
    void updateProduct() {

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Products.class))).thenReturn(updatedProduct);

        boolean result = productService.updateProduct(1L, updatedProduct);


        assertTrue(result);


        verify(productRepository, times(1)).save(updatedProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void deleteProduct() {

        when(productRepository.findById(1L)).thenReturn(Optional.of(product)); // "product" es el producto que ya existe


        boolean result = productService.deleteProduct(1L);


        assertTrue(result);


        verify(productRepository, times(1)).deleteById(1L);
        verify(productRepository, times(1)).findById(1L);
    }
}