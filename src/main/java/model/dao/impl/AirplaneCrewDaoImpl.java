package model.dao.impl;

import lombok.var;
import model.DataSourceManager;
import model.dao.ManyToManyDao;
import model.entity.AirplaneCrew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AirplaneCrewDaoImpl implements ManyToManyDao<AirplaneCrew> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirplaneCrewDaoImpl.class);
    private String sql = null;
    public static final String INSERT = "INSERT INTO airplane_crew (airplaneId,crewId) values (? , ?)";
    public static final String DELETE = "DELETE FROM airplane_crew WHERE airplaneId = ? AND crewId=?";
@Override
    public void create(AirplaneCrew item) {
        LOGGER.debug("Creating item:{}",item);
        try (var connection = DataSourceManager.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(INSERT)){
            preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getCrewId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

@Override
    public void delete(AirplaneCrew item) {
        LOGGER.debug("Delete    item:{}",item);
        try (var connection = DataSourceManager.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(DELETE)){
             preparedStatement.setInt(1,item.getAirplaneId());
            preparedStatement.setInt(2,item.getCrewId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
}
