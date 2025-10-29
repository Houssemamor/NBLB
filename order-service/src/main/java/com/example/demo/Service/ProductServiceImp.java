package com.example.demo.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    // Créer un nouveau produit
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    // Récupérer tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    // Récupérer un produit par ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    @Override
    // Mettre à jour un produit
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    // Supprimer un produit par ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
