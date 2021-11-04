package model.dao.impl;

import lombok.var;
import model.dao.AirplaneDao;
import model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.dao.util.AirplaneUtil.checkAirplaneExist;

public class AirplaneDaoImpl extends DefaultDaoImpl<Airplane> implements AirplaneDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirplaneDaoImpl.class);
    public static final String INSERT = "INSERT INTO airplane (airplaneId,serialNumber) values (seq_airplane.nextval,?)";
    public static final String UPDATE = "UPDATE airplane SET serialNumber =? where airplaneId = ? ";
    public static final String DELETE = "DELETE FROM airplane WHERE airplaneId = ?";
    public static final String SELECT_FROM_AIRPLANE_BY_ID = "SELECT a.airplaneId,a.serialNumber,c.crewId," +
            "c.name as nameOfCrew,ro.roleId,ro.name as nameOfRole,r.routeId,r.departure,r.arrival " +
            "FROM airplane a LEFT JOIN airplane_crew ac ON (a.airplaneId=ac.airplaneID)" +
            "JOIN crew c ON (ac.crewId = c.crewId) JOIN role ro ON (c.roleId=ro.roleId)" +
            "LEFT JOIN airplane_route ar ON(a.airplaneId=ar.airplaneId)JOIN route r " +
            "ON(ar.routeId=r.routeId) WHERE a.airplaneId = ?";
    public static final String SELECT_FROM_AIRPLANE = "SELECT a.airplaneId,a.serialNumber,c.crewId," +
            "c.name as nameOfCrew,ro.roleId,ro.name as nameOfRole,r.routeId,r.departure,r.arrival " +
            "FROM airplane a LEFT JOIN airplane_crew ac ON (a.airplaneId=ac.airplaneID)" +
            "JOIN crew c ON (ac.crewId = c.crewId) JOIN role ro ON (c.roleId=ro.roleId)" +
            "LEFT JOIN airplane_route ar ON(a.airplaneId=ar.airplaneId)JOIN route r " +
            "ON(ar.routeId=r.routeId)";
    public static final String ID = "airplaneId";
    public static final String NAME = "serialNumber";
    public static final String CREW_ID = "crewId";
    public static final String NAME_OF_CREW = "nameOfCrew";
    public static final String ROLE_ID = "roleId";
    public static final String NAME_OF_ROLE = "nameOfRole";
    public static final String ROUTE_ID = "routeId";
    public static final String DEPARTURE = "departure";
    public static final String ARRIVAL = "arrival";

    @Override
    void makeDaoRequestForCreate(PreparedStatement preparedStatement, Airplane item)  {
        try {
            preparedStatement.setString(1, item.getSerialNumber());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }

    }

    @Override
    void makeDaoRequestForUpdate(PreparedStatement preparedStatement, Airplane item){
        try {
            preparedStatement.setString(1, item.getSerialNumber());
        preparedStatement.setInt(2, item.getAirplaneId());
        } catch (SQLException e) {
           LOGGER.error(e.getMessage(),e);
        }
    }

    @Override
    void makeDaoRequestForDelete(PreparedStatement preparedStatement, Airplane item) {

        try {
            preparedStatement.setInt(1, item.getAirplaneId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @Override
    Airplane getItem(ResultSet rs)  {
        List<Airplane> airplanes = new ArrayList<>();
        while (true) {
            try {
                if (!rs.next()) break;

            var airplane = checkAirplaneExist(rs.getInt(ID), rs.getString(NAME), airplanes);
            var crew = Crew.builder()
                    .crewId(rs.getInt(CREW_ID))
                    .name(rs.getString(NAME_OF_CREW))
                    .role(Role.builder()
                            .id(rs.getInt(ROLE_ID))
                            .name(rs.getString(NAME_OF_ROLE))
                            .build())
                    .build();
            if (!airplane.getCrews().contains(crew))
                airplane.getCrews().add(crew);

            var route = Route.builder()
                    .routeId(rs.getInt(ROUTE_ID))
                    .departure(rs.getString(DEPARTURE))
                    .arrival(rs.getString(ARRIVAL))
                    .build();
            if (!airplane.getRoutes().contains(route))
                airplane.getRoutes().add(route);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return airplanes.get(0);
    }

    @Override
    List<Airplane> getListOfItems(ResultSet rs, List<Airplane> items){
        try {
            while (rs.next()) {
                var airplane = checkAirplaneExist(rs.getInt(ID), rs.getString(NAME), items);

                var crew = Crew.builder()
                        .crewId(rs.getInt(CREW_ID))
                        .name(rs.getString(NAME_OF_CREW))
                        .role(Role.builder()
                                .id(rs.getInt(ROLE_ID))
                                .name(rs.getString(NAME_OF_ROLE))
                                .build())
                        .build();
                airplane.getCrews().add(crew);

                var route = Route.builder()
                        .routeId(rs.getInt(ROUTE_ID))
                        .departure(rs.getString(DEPARTURE))
                        .arrival(rs.getString(ARRIVAL))
                        .build();
                airplane.getRoutes().add(route);
            }
        }catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return items;
    }
    @Override
    public Airplane getAirplaneBySerialNumber(String serialNumber) {
        var airplane = getAllItems().stream()
                .filter(ap -> ap.getSerialNumber().equals(serialNumber))
                .findFirst()
                .orElse(null);
        return airplane;
    }
    @Override
    String getSelectOfItem() {
        return SELECT_FROM_AIRPLANE;
    }

    @Override
    String getSelectByIdOfItem() {
        return SELECT_FROM_AIRPLANE_BY_ID;
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
//    private void checkAndCreateAirplaneRoute(Airplane item) {
//        if (item.getRoutes()!=null){
//            AirplaneRouteDaoImpl airplaneRouteDao = new AirplaneRouteDaoImpl();
//            for (Route route:
//                    item.getRoutes()) {
//                airplaneRouteDao.create(AirplaneRoute.builder()
//                        .airplaneId(item.getAirplaneId())
//                        .routeId(route.getRouteId())
//                        .build());
//            }
//        }
