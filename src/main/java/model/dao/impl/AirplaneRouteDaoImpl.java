package model.dao.impl;

import lombok.var;
import model.DataSourceManager;
import model.dao.ManyToManyDao;
import model.entity.AirplaneRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AirplaneRouteDaoImpl implements ManyToManyDao<AirplaneRoute> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirplaneRouteDaoImpl.class);
    public static final String INSERT = "INSERT INTO airplane_route (airplaneId,routeId) values (? , ?)";
    public static final String DELETE = "DELETE FROM airplane_route WHERE airplaneId =? AND routeId = ?";
@Override
    public void create(AirplaneRoute item) {
        LOGGER.debug("Creating "+ "item:{}",item);
        try (var connection = DataSourceManager.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(INSERT)){
            preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getRouteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

@Override
    public void delete(AirplaneRoute item) {
        LOGGER.debug("Delete "+ "item:{}",item);
        try (var connection = DataSourceManager.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getRouteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
}
