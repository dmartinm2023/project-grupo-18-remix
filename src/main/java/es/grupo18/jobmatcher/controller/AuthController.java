package es.grupo18.jobmatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import es.grupo18.jobmatcher.repository.FileAccountRepository;
import es.grupo18.jobmatcher.model.Account;
import es.grupo18.jobmatcher.model.Company;
import es.grupo18.jobmatcher.model.User;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private FileAccountRepository accountRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload, HttpSession session) {
        String email = payload.get("email");
        String password = payload.get("password");

        System.out.println("Login attempt for: " + email); // Debug log

        Account account = accountRepository.findByEmail(email);
        
        if (account != null && account.getPassword().equals(password)) {
            session.setAttribute("user", account);
            
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "accountType", account instanceof Company ? "company" : "user",
                "name", account.getName(),
                "email", account.getEmail(),
                "redirect", "/main"
            ));
        }

        return ResponseEntity.badRequest().body(Map.of(
            "error", "Invalid credentials"
        ));
    }

    @PostMapping("/api/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of(
            "message", "Session successfully closed",
            "redirect", "/login"
        ));
    }

    @GetMapping("/check-session")
    @ResponseBody
    public ResponseEntity<?> checkSession(HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        if (account != null) {
            return ResponseEntity.ok(Map.of(
                "loggedIn", true,
                "accountType", account instanceof Company ? "company" : "user",
                "name", account.getName()
            ));
        }
        return ResponseEntity.ok(Map.of("loggedIn", false));
    }

    @GetMapping("/form")
    public String form() {
        return "form";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        String name = payload.get("name");

        if (accountRepository.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Email already in use"
            ));
        }

        Account newAccount = new User();
        newAccount.setEmail(email);
        newAccount.setPassword(password);
        newAccount.setName(name);

        accountRepository.save(newAccount);

        return ResponseEntity.ok(Map.of(
            "message", "Registration successful",
            "redirect", "/login"
        ));
    }

}