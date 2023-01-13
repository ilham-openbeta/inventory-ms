package id.web.ilham.inventory.inventory.controller;

import id.web.ilham.inventory.common.model.ResponseMessage;
import id.web.ilham.inventory.inventory.model.UnitConverterRequest;
import id.web.ilham.inventory.inventory.model.UnitConverterResponse;
import id.web.ilham.inventory.inventory.service.UnitConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/units")
@RestController
@RequiredArgsConstructor
public class UnitController {

    private final UnitConverterService unitConverterService;

    @GetMapping("/convert")
    public ResponseMessage<UnitConverterResponse> unitConverter(@RequestParam String from, @RequestParam String to, @RequestParam String value) {
        return unitConverterService.execute(UnitConverterRequest.builder().from(from).to(to).value(value).build());
    }

}
