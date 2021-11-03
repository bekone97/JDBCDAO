package model.dao;

import model.entity.Role;

import java.util.List;

public interface RoleDao extends DefaultDao<Role>{
    Role getRoleByName(String name);
//    Role getRole(int id);
//    List<Role> getAllRoles();
}
