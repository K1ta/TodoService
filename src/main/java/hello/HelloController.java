package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/help")
    public String index2() {
        return "Help";
    }

    @RequestMapping("/int")
    public int index3() {
        return 20;
    }

}