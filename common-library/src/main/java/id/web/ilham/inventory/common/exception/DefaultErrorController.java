package id.web.ilham.inventory.common.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultErrorController implements ErrorController {

    @GetMapping("/error")
    public void handleError() {
        throw new ApplicationException(HttpStatus.NOT_FOUND, "error." + HttpStatus.NOT_FOUND.value() + ".path");
    }
}
