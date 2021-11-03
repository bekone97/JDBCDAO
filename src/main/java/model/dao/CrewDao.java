package model.dao;

import model.entity.Crew;

import java.util.List;

public interface CrewDao extends DefaultDao<Crew>{
    List<Crew> getCrewsByName(String name);
    List<Crew> getCrewsByRole(int roleId);

}
