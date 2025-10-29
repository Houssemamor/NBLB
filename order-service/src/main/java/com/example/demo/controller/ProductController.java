package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.Service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductServiceImp productService; // Ajout de 'private' pour bonne pratique

    @RequestMapping("/order-service/addProduct")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("productform", product); // Méthode correcte avec import approprié
        return "new_product";
    }

    @RequestMapping(value = "/order-service/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("productform") Product product) { // Correction de "ProductForm" à "productform"
        productService.createProduct(product);
        return "redirect:/order-service/all";
    }
    @RequestMapping("/order-service/all")
    public String listProducts(Model model) {
        List<Product> listProducts = productService.getAllProducts();
        model.addAttribute("listProducts", listProducts);
        return "liste_products";
    }
}