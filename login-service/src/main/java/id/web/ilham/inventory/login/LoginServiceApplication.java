package id.web.ilham.inventory.login;

import id.web.ilham.inventory.common.config.OpenApi30Config;
import id.web.ilham.inventory.common.exception.DefaultErrorController;
import id.web.ilham.inventory.common.exception.DefaultExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({OpenApi30Config.class, DefaultErrorController.class, DefaultExceptionHandler.class})
public class LoginServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginServiceApplication.class, args);
    }

}
