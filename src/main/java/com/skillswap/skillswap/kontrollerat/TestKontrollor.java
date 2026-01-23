package com.skillswap.skillswap.kontrollerat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestKontrollor {

    @GetMapping("/")
    public String home() {
        return "SkillSwap backend po punon ✅";
    }
}
