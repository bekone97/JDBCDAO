package model.dao.util;

import lombok.var;
import model.entity.Airplane;
import model.entity.Route;

import java.util.ArrayList;
import java.util.List;

public class RouteUtil {
    public static Route checkRouteExist(int routeId, String departure, String arrival, List<Route> routes) {
        var mayBeRoute = routes.stream().filter(route -> route.getRouteId() == routeId).findFirst().orElse(null);

        if (mayBeRoute!=null) {
            return mayBeRoute;
        } else {
            var route = Route.builder()
                    .routeId(routeId)
                    .departure(departure)
                    .arrival(arrival)
                    .airplanes(new ArrayList<>())
                    .build();
            routes.add(route);

            return route;
        }
    }
}
