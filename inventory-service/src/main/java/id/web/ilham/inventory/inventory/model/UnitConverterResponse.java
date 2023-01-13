package id.web.ilham.inventory.inventory.model;

import id.web.ilham.inventory.common.model.CommonResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitConverterResponse implements CommonResponse {
    String value;

}
