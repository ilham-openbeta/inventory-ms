package id.web.ilham.inventory.inventory.service;

import id.web.ilham.inventory.common.model.ResponseMessage;
import id.web.ilham.inventory.common.service.CommonService;
import id.web.ilham.inventory.inventory.adaptor.NeutrinoAdaptor;
import id.web.ilham.inventory.inventory.model.NeutrinoRequest;
import id.web.ilham.inventory.inventory.model.NeutrinoResponse;
import id.web.ilham.inventory.inventory.model.UnitConverterRequest;
import id.web.ilham.inventory.inventory.model.UnitConverterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitConverterService implements CommonService<UnitConverterRequest, UnitConverterResponse> {

    private final NeutrinoAdaptor neutrinoAdaptor;

    @Override
    public ResponseMessage<UnitConverterResponse> execute(UnitConverterRequest request) {
        NeutrinoResponse neutrinoResponse = neutrinoAdaptor.convertUnit(NeutrinoRequest.builder()
                .fromType(request.getFrom())
                .toType(request.getTo())
                .fromValue(request.getValue())
                .build());

        return ResponseMessage.success(UnitConverterResponse.builder().value(neutrinoResponse.getResult()).build());
    }
}