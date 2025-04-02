package pkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A utility.
 */
@SpringBootApplication
public class X {
    public static void main(String[] args) {
        SpringApplication.run(X.class, args);
    }
}

@RestController
class SuccessController {
    /**
     * Endpoint to indicate success.
     */
    @GetMapping("/")
    public String success() {
        return "Project Deployment Success!";
    }
}
