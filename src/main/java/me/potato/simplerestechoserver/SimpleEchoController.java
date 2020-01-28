package me.potato.simplerestechoserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class SimpleEchoController {

    @GetMapping("/echo")
    public Simple getEcho(@RequestParam String message, HttpServletRequest request) {
        log.info("ClientIP(X-FORWARDED-FOR) ={}", request.getHeader("X-Forwarded-For"));
        return new Simple(message, request.getHeader("X-Forwarded-For"));
    }

}
