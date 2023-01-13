package id.web.ilham.inventory.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class NeutrinoRequest implements Serializable {

    @JsonProperty("from-value")
    private String fromValue;
    @JsonProperty("from-type")
    private String fromType;
    @JsonProperty("to-type")
    private String toType;
}
