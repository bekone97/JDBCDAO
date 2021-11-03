package model.dao.impl;

import lombok.var;
import model.DataSourceManager;
import model.dao.RoleDao;
import model.dao.util.RoleUtil;
import model.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl extends DefaultDaoImpl<Role> implements RoleDao {
    private final static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    public static final String SELECT_FROM_ROLE_BY_ID = "SELECT * FROM role where roleId = ?";
    public static final String SELECT_FROM_ROLE = "SELECT * FROM role";
    public static final String ID = "roleId";
    public static final String NAME = "name";
    public static final String INSERT = "INSERT INTO role(roleId,name) values (SEQ_ROLE.nextval, ?)";
    public static final String UPDATE = "UPDATE role SET name =? where roleId = ? ";
    public static final String DELETE = "DELETE FROM role WHERE roleId = ?";
    private String sql;


    @Override
    void makeDaoRequestForUpdate(PreparedStatement preparedStatement, Role item) {
        try {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    void makeDaoRequestForDelete(PreparedStatement preparedStatement, Role item) {
        try {
            preparedStatement.setInt(1, item.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    Role getItem(ResultSet rs) {
        Role role = null;
        try {
            if (rs.next()) {
                role = Role.builder()
                        .id(rs.getInt(ID))
                        .name(rs.getString(NAME))
                        .build();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return role;
    }

    @Override
    List<Role> getListOfItems(ResultSet rs, List<Role> items) {
        try {
            while (rs.next()) {
                var role = RoleUtil.checkRoleExist(rs.getInt(ID), rs.getString(NAME), items);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public Role getRoleByName(String name) {
        var role = getAllItems().stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElse(null);
        return role;
    }

    @Override
    void makeDaoRequestForCreate(PreparedStatement preparedStatement, Role item){

        try {
            preparedStatement.setString(1, item.getName());
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    String getSelectOfItem() {
        return SELECT_FROM_ROLE;
    }

    @Override
    String getSelectByIdOfItem() {
        return SELECT_FROM_ROLE_BY_ID;
    }

    @Override
    String getInsertOfItem() {
        return INSERT;
    }

    @Override
    String getDeleteOfItem() {
        return DELETE;
    }

    @Override
    String getUpdateOfItem() {
        return UPDATE;
    }

}
