package cs489.apsd.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryServicesController {

    @GetMapping(value = {"/secured/services","/elibrary/secured/services"})
    public String services() {
        return "secured/services";
    }

}
