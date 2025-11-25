package com.Shadows.orderservice.controller;

import com.Shadows.orderservice.model.Category;
import com.Shadows.orderservice.model.Product;
import com.Shadows.orderservice.model.ProductStatus;
import com.Shadows.orderservice.Service.ProductServiceImp;
import com.Shadows.orderservice.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductServiceImp productService; // Ajout de 'private' pour bonne pratique

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/order-service/addProduct")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("productform", product); // Méthode correcte avec import approprié
        model.addAttribute("categories", Category.values());
        return "new_product";
    }

    @RequestMapping(value = "/order-service/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("productform") Product product, HttpSession session) { // Correction de "ProductForm" à "productform"
        // Set creation date
        product.setCreatedAt(new Date(System.currentTimeMillis()));
        
        // Set default status
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.AVAILABLE);
        }

        // Set addedBy from session token
        String token = (String) session.getAttribute("jwtToken");
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            product.setAddedBy(username);
        }

        productService.createProduct(product);
        return "redirect:/order-service/all";
    }
    @RequestMapping("/order-service/all")
    public String listProducts(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        List<Product> listProducts;

        if (token != null && jwtUtil.validateToken(token)) {
            String role = jwtUtil.extractRole(token);
            String username = jwtUtil.extractUsername(token);
            
            if ("SHOP".equals(role)) {
                // Shop owners only see their own products
                listProducts = productService.getProductsByAddedBy(username);
            } else {
                // Admin or others might see all (or handle differently)
                // For now, default to all for non-shop users, or restrict as needed
                listProducts = productService.getAllProducts();
            }
        } else {
            // Not logged in or invalid token
            return "redirect:/auth/login";
        }

        model.addAttribute("listProducts", listProducts);
        return "list_product";
    }
}