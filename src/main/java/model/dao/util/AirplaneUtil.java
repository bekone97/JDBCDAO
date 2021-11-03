package model.dao.util;

import lombok.var;
import model.entity.Airplane;
import model.entity.Crew;
import model.entity.Role;
import model.entity.Route;

import java.util.ArrayList;
import java.util.List;

public class AirplaneUtil {
    private AirplaneUtil() {}

    public static Airplane checkAirplaneExist(int airplaneId, String serialNumber, List<Airplane> airplanes) {
        var mayBeAirplane = airplanes.stream().
                filter(airplane -> airplane.getAirplaneId() == airplaneId).
                findFirst().
                orElse(null);

        if (mayBeAirplane!=null) {
            return mayBeAirplane;
        } else {
            var airplane = Airplane.builder()
                    .airplaneId(airplaneId)
                    .serialNumber(serialNumber)
                    .crews(new ArrayList<>())
                    .routes(new ArrayList<>())
                    .build();
            airplanes.add(airplane);

            return airplane;
        }
    }
}
