package com.test.backend.Rest;

import com.test.backend.Model.Products;
import com.test.backend.Service.ProducS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api")
public class ProductApi {

    private final ProducS productService;

    @Autowired
    public ProductApi(ProducS productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Products product) {

        if (product.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre no puede estar vacío.");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El precio no puede ser negativo.");
        }
        if (product.getQuantity() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad no puede ser negativa.");
        }

        boolean result = productService.addProduct(product);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el producto.");
        }
    }

}
