package model.dao.util;

import lombok.var;
import model.entity.Airplane;
import model.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleUtil {
    public static Role checkRoleExist(int roleId, String name, List<Role> roles) {
        var mayBeRole = roles.stream().filter(role -> role.getId() == roleId).findFirst().orElse(null);

        if (mayBeRole!=null) {
            return mayBeRole;
        } else {
            var role = Role.builder()
                    .id(roleId)
                    .name(name)
                    .build();
            roles.add(role);

            return role;
        }
    }
}
