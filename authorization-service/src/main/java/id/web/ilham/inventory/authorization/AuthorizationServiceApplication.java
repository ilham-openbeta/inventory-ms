package id.web.ilham.inventory.authorization;

import id.web.ilham.inventory.common.exception.DefaultErrorController;
import id.web.ilham.inventory.common.exception.DefaultExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DefaultErrorController.class, DefaultExceptionHandler.class})
public class AuthorizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }

}
