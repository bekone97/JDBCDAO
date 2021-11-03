package model.dao;

import model.entity.Airplane;

import java.sql.SQLException;
import java.util.List;

public interface AirplaneDao extends DefaultDao<Airplane> {
    Airplane getAirplaneBySerialNumber(String serialNumber);


}
