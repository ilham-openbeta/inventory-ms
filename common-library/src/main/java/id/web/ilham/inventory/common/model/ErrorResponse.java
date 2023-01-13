package id.web.ilham.inventory.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorResponse implements CommonResponse{
    Map<String, List<String>> errors;
}
