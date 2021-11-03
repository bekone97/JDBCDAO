package model.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airplane {
    private int airplaneId;
    private String serialNumber;
    private List<Crew> crews=new ArrayList<>();
    private List<Route> routes=new ArrayList<>();

    public Airplane( String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "airplaneId=" + airplaneId +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
