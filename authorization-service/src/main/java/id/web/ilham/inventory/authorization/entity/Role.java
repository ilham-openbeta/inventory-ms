package id.web.ilham.inventory.authorization.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Role implements Serializable {
    private Integer id;

    private String name;

}