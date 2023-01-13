package id.web.ilham.inventory.login.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserRoles implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
