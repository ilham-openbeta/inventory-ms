package id.web.ilham.inventory.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class NeutrinoResponse implements Serializable {

    @JsonProperty("from-type")
    private String fromType;
    @JsonProperty("from-value")
    private String fromValue;
    private String result;
    @JsonProperty("result-float")
    private Double resultFloat;
    @JsonProperty("to-type")
    private String toType;
    private Boolean valid;
}
