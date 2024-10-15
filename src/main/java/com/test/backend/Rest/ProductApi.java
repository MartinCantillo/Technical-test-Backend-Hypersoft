package com.test.backend.Rest;

import com.test.backend.Model.Products;
import com.test.backend.Service.ProducS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductApi {

    private final ProducS productService;

    @Autowired
    public ProductApi(ProducS productService) {
        this.productService = productService;
    }

    @GetMapping
    public String helloWord() {
        return "Hello World";
    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addProduct(@RequestBody Products product) {
        if (product.getName().trim().isEmpty() || product.getPrice().compareTo(BigDecimal.ZERO) < 0
                || product.getQuantity() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor verifica .");
        }

        boolean result = productService.addProduct(product);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el producto.");
        }
    }

    @GetMapping("getAll")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Object> getProductById(@PathVariable long id) {
        Optional<Products> product = productService.getProductById(id);
        return product.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto no existe"));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateProduct(@PathVariable long id, @RequestBody Products product) {
        if (product.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre no puede estar vacío.");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El precio no puede ser negativo.");
        }
        if (product.getQuantity() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad no puede ser negativa.");
        }

        boolean updated = productService.updateProduct(id, product);
        if (updated) {
            return ResponseEntity.ok("Producto actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
