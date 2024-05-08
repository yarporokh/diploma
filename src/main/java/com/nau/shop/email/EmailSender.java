package com.nau.shop.email;

public interface EmailSender {
    void sender(String to, String email);
    String confirmationEmail(String token);
}
