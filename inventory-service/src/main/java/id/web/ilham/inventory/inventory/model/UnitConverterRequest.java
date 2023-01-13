package id.web.ilham.inventory.inventory.model;

import id.web.ilham.inventory.common.model.CommonRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitConverterRequest implements CommonRequest {

    String from;
    String to;
    String value;

}
