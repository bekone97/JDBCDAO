package model.dao.impl;

import lombok.var;
import model.DataSourceManager;
import model.entity.AirplaneCrew;
import model.entity.AirplaneRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneRouteDaoImpl  {
    private final static Logger logger= LoggerFactory.getLogger(AirplaneRouteDaoImpl.class);
    private String sql = null;
    public static final String INSERT = "INSERT INTO airplane_route (airplaneId,routeId) values (? , ?)";
    public static final String DELETE = "DELETE FROM airplane_route WHERE airplaneId =? AND routeId = ?";

    public void create(AirplaneRoute item) {
        logger.debug("Creating "+ "item:{}",item);
        sql= INSERT;
        try (var connection = DataSourceManager.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getRouteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }


    public void delete(AirplaneRoute item) {
        logger.debug("Delete "+ "item:{}",item);
        String sql = DELETE;
        try (var connection = DataSourceManager.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getRouteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
