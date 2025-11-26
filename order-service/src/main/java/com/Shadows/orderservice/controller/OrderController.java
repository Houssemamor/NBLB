package com.Shadows.orderservice.controller;

import com.Shadows.orderservice.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import com.Shadows.orderservice.model.Order;
import com.Shadows.orderservice.Service.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@Controller
public class OrderController {

    @Autowired
    private OrderServiceImp orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @RequestMapping("/order-service/allorder")
    public String allOrders(Model model, HttpSession session){
        model.addAttribute("gatewayUrl", gatewayUrl);
        String token = (String) session.getAttribute("jwtToken");
        List<Order> listOrders;

        if (token != null && jwtUtil.validateToken(token)) {
            String role = jwtUtil.extractRole(token);
            String username = jwtUtil.extractUsername(token);
            model.addAttribute("userRole", role);
            model.addAttribute("username", username);

            if ("CLIENT".equals(role)) {
                listOrders = orderService.getOrdersByUsername(username);
            } else if ("ADMIN".equals(role)) {
                listOrders = orderService.getOrders();
            } else {
                // For SHOP, show orders containing their products
                listOrders = orderService.getOrdersByProductOwner(username);
            }
        } else {
            // Fallback or redirect to login
            listOrders = orderService.getOrders();
        }

        model.addAttribute("listOrders",listOrders);
        return "List_orders";
    }

    @RequestMapping("/order-service/send/{id}")
    public String sendOrder(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        if (token != null && jwtUtil.validateToken(token)) {
            String role = jwtUtil.extractRole(token);
            if (!"CLIENT".equals(role)) {
                java.util.Optional<Order> orderOpt = orderService.getOrderById(id);
                if (orderOpt.isPresent() && "PAID".equals(orderOpt.get().getStatus())) {
                    orderService.updateOrderStatus(id, "SHIPPED");
                }
            }
        }
        return "redirect:/order-service/allorder";
    }

    @RequestMapping("/order-service/cancel/{id}")
    public String cancelOrder(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        if (token != null && jwtUtil.validateToken(token)) {
             orderService.updateOrderStatus(id, "CANCELLED");
        }
        return "redirect:/order-service/allorder";
    }

    @RequestMapping(value = "/order-service/api/orders/{id}/status", method = {RequestMethod.POST, RequestMethod.GET})
    @org.springframework.web.bind.annotation.ResponseBody
    public String updateOrderStatusApi(@PathVariable Long id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "Status updated";
    }
}