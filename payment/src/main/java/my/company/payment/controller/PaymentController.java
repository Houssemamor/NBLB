package my.company.payment.controller;

import my.company.payment.model.Payment;
import my.company.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Controller
public class PaymentController {
    private final PaymentRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public PaymentController(PaymentRepository repo) {
        this.repo = repo;
    }

    // Afficher la page de paiement
    @RequestMapping("/payment/form")
    public String showPaymentForm(@RequestParam(required = false) Long orderId, 
                                  @RequestParam(required = false) Double amount, 
                                  Model model) {
        model.addAttribute("gatewayUrl", gatewayUrl);
        Payment payment = new Payment();
        if (orderId != null) payment.setOrderId(orderId);
        if (amount != null) payment.setAmount(amount);
        
        model.addAttribute("payment", payment);
        return "payment-form"; // Nom du fichier HTML (payment-form.html)
    }

    // Traiter le paiement
    @RequestMapping("/payment/process")
    public String processPayment(@ModelAttribute Payment payment, Model model) {
        model.addAttribute("gatewayUrl", gatewayUrl);
        payment.setStatus("COMPLETED");
        payment.setDate(new Date());
        repo.save(payment);
        
        if (payment.getOrderId() != null) {
            try {
                // Call order-service to update status
                String url = "http://localhost:8091/order-service/api/orders/" + payment.getOrderId() + "/status?status=PAID";
                restTemplate.postForObject(url, null, String.class);
            } catch (Exception e) {
                e.printStackTrace();
                // Log error but don't fail the payment view
            }
        }

        model.addAttribute("message", "Paiement effectué avec succès !");
        return "payment-success"; // Afficher la page de succès
    }
}
