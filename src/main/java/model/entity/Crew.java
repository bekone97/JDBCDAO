package model.entity;

import lombok.*;

import java.util.List;
import java.util.Objects;
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Crew {

    private int crewId;
    private String name;
    private Role role;
    private int roleId;
    List<Airplane> airplanes;

    public Crew(String name,int roleId){
        this.name=name;
        this.roleId=roleId;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "crewId=" + crewId +
                ", name='" + name + '\'' +
                role.toString()+
                '}';
    }
}
