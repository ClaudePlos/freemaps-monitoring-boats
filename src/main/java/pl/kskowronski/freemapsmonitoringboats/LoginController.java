package pl.kskowronski.freemapsmonitoringboats;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String get() {
        return "login";
    }

}
