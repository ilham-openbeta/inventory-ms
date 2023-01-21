package id.web.ilham.inventory.controller;

import id.web.ilham.inventory.common.model.ResponseMessage;
import id.web.ilham.inventory.model.UnitConverterRequest;
import id.web.ilham.inventory.model.UnitConverterResponse;
import id.web.ilham.inventory.service.UnitConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/units")
@RestController
@RequiredArgsConstructor
public class UnitController {

    private final UnitConverterService unitConverterService;

    @Operation(summary = "Neutrino Convert", description = "Convert unit of measurement", tags = {"units"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/convert")
    public ResponseMessage<UnitConverterResponse> unitConverter(@RequestParam String from, @RequestParam String to, @RequestParam String value) {
        return unitConverterService.execute(UnitConverterRequest.builder().from(from).to(to).value(value).build());
    }
}
