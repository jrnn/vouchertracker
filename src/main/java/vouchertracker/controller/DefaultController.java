package vouchertracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello world!!";
    }

    @GetMapping("*")
    public String defaultRedirect() {
        return "redirect:/";
    }

}