package model.dao.util;

import lombok.var;
import model.DataSourceManager;
import model.entity.Airplane;
import model.entity.Crew;
import model.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewUtil {

    public void makePreparedStatementForCudOperations(String sql, int numberOfParameterIndexForFirstValue,
                                                      int firstValue, int numberOfParameterIndexForSecondValue, String secondValue,
                                                      int numberOfParameterForThirdValue, Role thirdValue){
        try (Connection connection = DataSourceManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(numberOfParameterIndexForFirstValue, firstValue);
            preparedStatement.setString(numberOfParameterIndexForSecondValue, secondValue);
            preparedStatement.setObject(numberOfParameterForThirdValue,thirdValue);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void makePreparedStatementForCudOperations(String sql, int firstValue){
        try (Connection connection = DataSourceManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, firstValue);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void makePreparedStatementForCudOperations(String sql, int numberOfParameterIndexForFirstValue, int firstValue,
                                                      int numberOfParameterIndexForSecondValue, Role secondValue){
        try (Connection connection = DataSourceManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(numberOfParameterIndexForFirstValue, firstValue);
            preparedStatement.setObject(numberOfParameterIndexForSecondValue, secondValue);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static Crew checkCrewExist(int crewId, String name,String nameOfRole,int roleId, List<Crew> crews) {
        var mayBeCrew =crews.stream().filter(crew -> crew.getCrewId() == crewId).findFirst().orElse(null);

        if (mayBeCrew!=null) {
            return mayBeCrew;
        } else {
            var crew = Crew.builder()
                    .crewId(crewId)
                    .name(name)
                    .role(Role.builder()
                            .id(roleId)
                            .name(nameOfRole)
                            .build())
                    .airplanes(new ArrayList<>())
                    .build();

           crews.add(crew);

            return crew;
        }
    }


}
