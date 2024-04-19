package cs489.apsd.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"", "/","/elibrary","/public/home"})
    public String home0() {
        return "redirect:/elibrary/public/home";
    }

    @GetMapping(value = {"/public/home","/elibrary/public/home"})
    public String home1() {
        return "public/index";
    }

    @GetMapping(value = {"/public/about","/elibrary/public/about"})
    public String about() {
        return "public/about";
    }

    @GetMapping(value = {"/public/virtualtour","/elibrary/public/virtualtour"})
    public String virtualtour() {
        return "public/virtualtour";
    }

    @GetMapping(value = {"/secured/home","/elibrary/secured/home"})
    public String home2() {
        return "secured/index";
    }
}
