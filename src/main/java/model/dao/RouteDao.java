package model.dao;

import model.entity.Route;

import java.util.List;

public interface RouteDao extends DefaultDao<Route>{
    List<Route> getRoutesByDeparture(String departure);
    List<Route> getRoutesByArrival(String arrival);
}
