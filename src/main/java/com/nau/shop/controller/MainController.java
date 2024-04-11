package com.nau.shop.controller;

import com.nau.shop.dto.RegisterBody;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class MainController {
    @GetMapping()
    public String mainPage(Model model, HttpSession session) {
        model.addAttribute("registerBody", new RegisterBody());

        Boolean isNew = (Boolean) session.getAttribute("isNew");
        model.addAttribute("isNew", isNew);

        session.removeAttribute("isNew");
        return "main";
    }

    @GetMapping("contacts")
    public String getContactPage() {
        return "contactPage";
    }

    @GetMapping("shipping-and-payment")
    public String getShippingAndPaymentPage() {
        return "shippingAndPaymentPage";
    }

    @GetMapping("return-and-exchange")
    public String getReturnAndExchangePage() {
        return "returnAndExchangePage";
    }
}
