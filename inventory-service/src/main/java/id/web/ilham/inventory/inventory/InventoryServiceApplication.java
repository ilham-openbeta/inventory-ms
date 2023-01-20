package id.web.ilham.inventory.inventory;

import id.web.ilham.inventory.common.config.OpenApi30Config;
import id.web.ilham.inventory.common.exception.DefaultErrorController;
import id.web.ilham.inventory.common.exception.DefaultExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import({OpenApi30Config.class, DefaultErrorController.class, DefaultExceptionHandler.class})
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

}
