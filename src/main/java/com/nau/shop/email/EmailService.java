package com.nau.shop.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailSender {
    private final JavaMailSender mailSender;

    @Override
    public void sender(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Підтвердження пошти");
            helper.setFrom("shop@shop.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public String confirmationEmail(String token) {
        return "<body style=\"font-family: Arial, sans-serif;\">\n" +
                "\n" +
                "    <div style=\"max-width: 600px; margin: 0 auto; padding: 20px;\">\n" +
                "        <h2>Підтвердження пошти</h2>\n" +
                "        <p>Вітаємо!</p>\n" +
                "        <p>Для завершення процесу реєстрації, будь ласка, підтвердіть свою електронну адресу, перейшовши за наступним посиланням:</p>\n" +
                "        <p><a href=\"http://localhost:8080/auth/confirm?token="+ token + "\" style=\"background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;\">Підтвердити пошту</a></p>\n" +
                "        <p>Якщо ви не реєструвалися на нашому сайті, проігноруйте цей лист.</p>\n" +
                "        <p>З повагою,<br>Адміністрація магазину</p>\n" +
                "    </div>";
    }
}
