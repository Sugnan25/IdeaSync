package com.ideasync.ideasync;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {

    @GetMapping("/test")
    public String test() {
        return "✅ IDEA SYNC SERVER IS WORKING";
    }
}
