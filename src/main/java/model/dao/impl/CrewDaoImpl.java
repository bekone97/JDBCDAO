package model.dao.impl;

import lombok.var;
import model.dao.CrewDao;
import model.dao.util.CrewUtil;
import model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CrewDaoImpl extends DefaultDaoImpl<Crew>  implements CrewDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(CrewDaoImpl.class);
    public static final String INSERT = "INSERT INTO CREW (crewId,name,roleId) VALUES (seq_crew.nextval,?,?)";
    public static final String UPDATE = "UPDATE crew SET name =? , roleId = ? where crewId = ? ";
    public static final String DELETE = "DELETE FROM crew WHERE crewID = ?";
    public static final String SELECT_BY_CREW_ID = "SELECT c.crewId,c.name,r.roleId ,r.name as nameOfRole," +
            " a.airplaneId , a.serialNumber FROM " +
            "crew c LEFT JOIN role r ON (r.roleID=c.roleID) JOIN airplane_crew ac ON (c.crewId=ac.crewId) " +
            "JOIN airplane a ON (ac.airplaneId=a.airplaneId) WHERE c.crewId = ?";
    public static final String SELECT_CREW = "SELECT c.crewId,c.name,r.roleId ,r.name as nameOfRole, a.airplaneId " +
            ", a.serialNumber FROM crew c LEFT JOIN role r ON (r.roleID=c.roleID) JOIN airplane_crew ac " +
            "ON (c.crewId=ac.crewId) JOIN airplane a ON (ac.airplaneId=a.airplaneId)";
    public static final String ID = "crewId";
    public static final String NAME = "name";
    public static final String NAME_OF_ROLE = "nameOfRole";
    public static final String ROLE_ID = "roleId";
    public static final String AIRPLANE_ID = "airplaneId";
    public static final String SERIAL_NUMBER = "serialNumber";

    @Override
    void makeDaoRequestForCreate(PreparedStatement preparedStatement, Crew item){
        try {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getRoleId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }


    @Override
    void makeDaoRequestForUpdate(PreparedStatement preparedStatement, Crew item){
        try {
            preparedStatement.setInt(3, item.getCrewId());
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getRoleId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }


    @Override
    void makeDaoRequestForDelete(PreparedStatement preparedStatement, Crew item){
        try {
            preparedStatement.setInt(1, item.getCrewId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @Override
    Crew getItem(ResultSet rs){
        List<Crew> crews = new ArrayList<>();
        try {
            while (rs.next()) {
                var crew = CrewUtil.checkCrewExist(rs.getInt(ID), rs.getString(NAME), rs.getString(NAME_OF_ROLE),
                        rs.getInt(ROLE_ID), crews);
                var airplane = Airplane.builder()
                        .airplaneId(rs.getInt(AIRPLANE_ID))
                        .serialNumber(rs.getString(SERIAL_NUMBER))
                        .build();
                crew.getAirplanes().add(airplane);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
        try {
        return crews.get(0);
        }catch (ArrayIndexOutOfBoundsException e){
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }

    @Override
    List<Crew> getListOfItems(ResultSet rs, List<Crew> items){
        List<Crew> crews = new ArrayList<>();
        try {
            while (rs.next()) {
                var crew = CrewUtil.checkCrewExist(rs.getInt(ID), rs.getString(NAME), rs.getString(NAME_OF_ROLE),
                        rs.getInt(ROLE_ID), crews);

                var airplane = Airplane.builder()
                        .airplaneId(rs.getInt(AIRPLANE_ID))
                        .serialNumber(rs.getString(SERIAL_NUMBER))
                        .build();
                crew.getAirplanes().add(airplane);

                items.add(crew);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return items;
    }

    @Override
    public List<Crew> getCrewsByName(String name) {
        return getAllItems().stream()
                .filter(crew -> crew.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Crew> getCrewsByRole(int roleId) {
        return getAllItems().stream()
                .filter(crew -> crew.getRoleId() == roleId)
                .collect(Collectors.toList());
    }

    @Override
    String getSelectOfItem() {
        return SELECT_CREW;
    }

    @Override
    String getSelectByIdOfItem() {
        return SELECT_BY_CREW_ID;
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
//    private void checkAndCreateAirplaneCrew(Crew item) {
//        if (item.getAirplanes()!=null){
//            AirplaneCrewDaoImpl airplaneCrewDao = new AirplaneCrewDaoImpl();
//            for (Airplane airplane:
//                    item.getAirplanes()) {
//                airplaneCrewDao.create(AirplaneCrew.builder()
//                        .airplaneId(airplane.getAirplaneId())
//                        .crewId(item.getCrewId())
//                        .build());
//            }
//        }
