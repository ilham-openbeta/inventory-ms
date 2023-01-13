package id.web.ilham.inventory.login.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Role implements Serializable {
    private Integer id;

    private String name;

}