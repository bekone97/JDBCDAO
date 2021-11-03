import model.dao.CrewDao;
import model.dao.impl.*;
import model.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {
        RouteDaoImpl routeDao = new RouteDaoImpl();
       System.out.println(routeDao.getAllItems());
        System.out.println(routeDao.getItemById(1));
        AirplaneDaoImpl airplaneDao = new AirplaneDaoImpl();
        System.out.println(routeDao.getItemById(1));
        List<Airplane> airplanes = airplaneDao.getAllItems();
        System.out.println(airplanes);
        RoleDaoImpl roleDao = new RoleDaoImpl();
        List<Role> roles = roleDao.getAllItems();
        System.out.println(roles);
        System.out.println(roleDao.getItemById(1));
        CrewDaoImpl crewDao = new CrewDaoImpl();
        System.out.println(crewDao.getItemById(4));
       List<Crew> crews = crewDao.getAllItems();
        System.out.println(crews);

    }
}
