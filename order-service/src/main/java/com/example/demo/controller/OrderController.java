package com.example.demo.controller;

import org.springframework.ui.Model;
import com.example.demo.model.Order;
import com.example.demo.Service.OrderServiceImp;
import com.example.demo.Service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;



@Controller
public class OrderController {

    @Autowired
    private OrderServiceImp orderService;
    @RequestMapping("/order-service/addorder")
    public String addOrder( Model model){
        Order order=new Order();
        model.addAttribute("orderform",order);
        return "add_order";
    }
    @RequestMapping(value="/order-service/saveorder",method= RequestMethod.POST)
    public String saveProduct(@ModelAttribute("orderform") Order order){
        orderService.createOrder(order);
        return "redirect:/order-service/allorder";
    }
    @RequestMapping("/order-service/allorder")
    public String allOrders(Model model){
        List<Order> listOrders =orderService.getOrders();
        model.addAttribute("listOrders",listOrders);
        return "List_orders";
    }


}