package id.web.ilham.inventory.authorization.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserRole implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
