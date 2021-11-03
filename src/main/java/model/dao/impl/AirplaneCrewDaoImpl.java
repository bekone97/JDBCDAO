package model.dao.impl;

import lombok.var;
import model.DataSourceManager;
import model.entity.AirplaneCrew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AirplaneCrewDaoImpl {
    private final static Logger logger= LoggerFactory.getLogger(AirplaneCrewDaoImpl.class);
    private String sql = null;
    public static final String INSERT = "INSERT INTO airplane_crew (airplaneId,crewId) values (? , ?)";
    public static final String DELETE = "DELETE FROM airplane_crew WHERE airplaneId = ? AND crewId=?";

    public void create(AirplaneCrew item) {
        logger.debug("Creating "+ "item:{}",item);
        sql= INSERT;
        try (var connection = DataSourceManager.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getCrewId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }


    public void delete(AirplaneCrew item) {
        logger.debug("Delete "+ "item:{}",item);
        String sql = DELETE;
        try (var connection = DataSourceManager.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(sql)){
             preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getCrewId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
