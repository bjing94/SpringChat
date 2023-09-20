package org.bjing.chat.demo;

import org.bjing.chat.db.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {
    @GetMapping("/demo")
    public String test(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out.println(user);
        System.out.println(user.getAuthorities());
        return "Ok";
    }

    @GetMapping("/admin/demo")
    public String testAdmin(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out.println(user);
        System.out.println(user.getAuthorities());
        return "Ok";
    }
}
