package my.company.payment.controller;

import my.company.payment.model.Payment;
import my.company.payment.repository.PaymentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentRepository repo;

    public PaymentController(PaymentRepository repo) {
        this.repo = repo;
    }

    // Afficher la page de paiement
    @GetMapping("/form")
    public String showPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        return "payment-form"; // Nom du fichier HTML (payment-form.html)
    }

    // Traiter le paiement
    @PostMapping("/process")
    public String processPayment(@ModelAttribute Payment payment, Model model) {
        payment.setStatus("COMPLETED");
        payment.setDate(new Date());
        repo.save(payment);
        model.addAttribute("message", "Paiement effectué avec succès !");
        return "payment-success"; // Afficher la page de succès
    }
}
