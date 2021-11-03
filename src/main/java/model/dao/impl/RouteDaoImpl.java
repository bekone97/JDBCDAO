package model.dao.impl;

import lombok.var;
import model.DataSourceManager;
import model.dao.RouteDao;
import model.entity.Airplane;
import model.entity.AirplaneRoute;
import model.entity.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static model.dao.util.RouteUtil.checkRouteExist;

public class RouteDaoImpl  extends DefaultDaoImpl<Route> implements RouteDao {
    private final static Logger logger= LoggerFactory.getLogger(RouteDaoImpl.class);
    public static final String INSERT = "INSERT INTO route (routeId,departure,arrival ) values (SEQ_ROUTE.nextval, ? ,?)";
    public static final String UPDATE = "UPDATE route SET departure =?,arrival = ? where routeId = ? ";
    public static final String DELETE = "DELETE FROM route WHERE routeId = ?";
    public static final String ID = "routeId";
    public static final String DEPARTURE = "departure";
    public static final String ARRIVAL = "arrival";
    public static final String SELECT_ALl = "select r.routeId,r.arrival,r.departure,a.airplaneId,a.serialNumber from route r" +
            " LEFT JOIN airplane_route ar ON(ar.routeId=r.routeId) JOIN airplane a " +
            "ON (a.airplaneId = ar.airplaneId)";
    public static final String SELECT_BY_ID = "select r.routeId,r.arrival,r.departure,a.airplaneId,a.serialNumber " +
            "from route r LEFT JOIN airplane_route ar ON(ar.routeId=r.routeId) JOIN airplane a " +
            "ON (a.airplaneId = ar.airplaneId) where r.routeId = ?";
    public static final String AIRPLANE_ID = "airplaneId";
    public static final String SERIAL_NUMBER = "serialNumber";


    @Override
    void makeDaoRequestForCreate(PreparedStatement preparedStatement, Route item) {
        try {
            preparedStatement.setString(1, item.getDeparture());
        preparedStatement.setString(2, item.getArrival());
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    void makeDaoRequestForUpdate(PreparedStatement preparedStatement, Route item){
        try {
            preparedStatement.setInt(3, item.getRouteId());
            preparedStatement.setString(1, item.getDeparture());
            preparedStatement.setString(2, item.getArrival());
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    void makeDaoRequestForDelete(PreparedStatement preparedStatement, Route item){

        try {
            preparedStatement.setInt(1, item.getRouteId());
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    Route getItem(ResultSet rs){
        List<Route> routes = new ArrayList<>();
        try {
            while (rs.next()) {
                var route = checkRouteExist(rs.getInt(ID), rs.getString(DEPARTURE), rs.getString(ARRIVAL), routes);

                var airplane = Airplane.builder()
                        .airplaneId(rs.getInt(AIRPLANE_ID))
                        .serialNumber(rs.getString(SERIAL_NUMBER))
                        .build();
                route.getAirplanes().add(airplane);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
        return routes.get(0);
    }

    @Override
    List<Route> getListOfItems(ResultSet rs, List<Route> items){
        try {
            while (rs.next()) {
                var route = checkRouteExist(rs.getInt(ID), rs.getString(DEPARTURE), rs.getString(ARRIVAL), items);

                var airplane = Airplane.builder()
                        .airplaneId(rs.getInt(AIRPLANE_ID))
                        .serialNumber(rs.getString(SERIAL_NUMBER))
                        .build();
                route.getAirplanes().add(airplane);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
        return items;
    }


    @Override
    public List<Route> getRoutesByDeparture(String departure) {
        return getAllItems().stream()
                .filter(route -> route.getDeparture().equals(departure))
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> getRoutesByArrival(String arrival) {
        return getAllItems().stream()
                .filter(route -> route.getArrival().equals(arrival))
                .collect(Collectors.toList());
    }
    @Override
    String getSelectOfItem() {
        return SELECT_ALl;
    }

    @Override
    String getSelectByIdOfItem() {
        return SELECT_BY_ID;
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

//    private void checkAndCreateAirplaneRoute(Route item) {
//        if (item.getAirplanes()!=null){
//            AirplaneRouteDaoImpl airplaneRouteDao = new AirplaneRouteDaoImpl();
//            for (Airplane airplane:
//                    item.getAirplanes()) {
//                airplaneRouteDao.create(AirplaneRoute.builder()
//                        .airplaneId(airplane.getAirplaneId())
//                        .routeId(item.getRouteId())
//                        .build());
//            }
//        }
//    }
//
