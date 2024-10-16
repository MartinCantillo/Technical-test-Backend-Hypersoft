package com.test.backend.Rest;

import com.test.backend.Model.Products;
import com.test.backend.Service.ProducS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<String, String>> addProduct(@RequestBody Products product) {

        Map<String, String> response = new HashMap<>();


        if (product.getName().trim().isEmpty() || product.getPrice().compareTo(BigDecimal.ZERO) < 0
                || product.getQuantity() < 0) {
            response.put("error", "Por favor verifica los datos del producto.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        boolean result = productService.addProduct(product);
        if (result) {
            response.put("success", "Producto creado correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("error", "Error al crear el producto.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("getAll")
    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    public ResponseEntity<Object> getProductById(@PathVariable long id) {
        Optional<Products> product = productService.getProductById(id);
        return product.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto no existe"));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<String, String>> updateProduct(@PathVariable long id, @RequestBody Products product) {
        Map<String, String> response = new HashMap<>();

        if (product.getName().trim().isEmpty()) {
            response.put("message", "El nombre no puede estar vacío.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            response.put("message", "El precio no puede ser negativo.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (product.getQuantity() < 0) {
            response.put("message", "La cantidad no puede ser negativa.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        boolean updated = productService.updateProduct(id, product);
        if (updated) {
            response.put("message", "Producto actualizado correctamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Producto no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
