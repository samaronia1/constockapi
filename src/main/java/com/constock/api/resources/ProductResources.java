package com.constock.api.resources;

import com.constock.api.models.Product;
import com.constock.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductResources {

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/product/{id}")
    Product getProdut(@PathVariable(value = "id") long id) {
        return productRepository.findById(id);
    }

    @GetMapping(value = "/products")
    List<Product> listProducts() {
        return productRepository.findAll();
    }

    @PostMapping(value = "/product")
    Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping(value = "/product")
    Product updateProduct(@RequestBody Product product) {
        Product result = productRepository.findById(product.getId());
        result.setName(product.getName() != null ? product.getName() : result.getName());
        result.setQuantity(product.getQuantity() != null ? product.getQuantity() : result.getQuantity());
        result.setValue(product.getValue() != null ? product.getValue() : result.getValue());
        return productRepository.save(result);
    }

    @DeleteMapping(value = "/product/{id}")
    void deleteProduct(@PathVariable(value = "id") long id) {
        productRepository.deleteById(id);
    }
}
