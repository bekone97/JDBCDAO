package model.entity;

import lombok.*;

import java.util.List;
import java.util.Objects;
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route {
    private int routeId;
    private String departure;
    private String arrival;
    List<Airplane> airplanes;
    public Route(String departure, String arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                '}';
    }
}
