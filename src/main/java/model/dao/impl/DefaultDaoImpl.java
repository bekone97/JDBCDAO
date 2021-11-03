package model.dao.impl;

import lombok.var;
import model.DataSourceManager;
import model.dao.DefaultDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DefaultDaoImpl<T> implements DefaultDao<T>{
        private final static Logger logger= LoggerFactory.getLogger(DefaultDaoImpl.class);

    String sql = null;

    @Override
    public void create(T item) {
        logger.debug("Creating "+" item:{}",item);
        sql= getInsertOfItem();
        try (var connection = DataSourceManager.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(sql)){
            makeDaoRequestForCreate(preparedStatement, item);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public T getItemById(int id) {
        logger.debug("Receiving item by "+" id:{}",id);
     sql=getSelectByIdOfItem();
     T item = null;
        try(Connection connection = DataSourceManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            item = getItem(rs);
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return item;

    }

    @Override
    public List<T> getAllItems() {
        logger.debug("Receiving all items");
        sql=getSelectOfItem();
        List<T> items = new ArrayList<>();
        try(Connection connection = DataSourceManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet rs = preparedStatement.executeQuery();
            items=getListOfItems(rs,items);
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return items;


    }
    @Override
    public void update(T item) {
        logger.debug("Updating "+" item:{}",item);
        sql= getUpdateOfItem();
        try (var connection = DataSourceManager.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(sql)){
            makeDaoRequestForUpdate(preparedStatement, item);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(T item) {
        logger.debug("Delete "+" item:{}",item);
        sql = getDeleteOfItem();
        try (var connection = DataSourceManager.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(sql)){
            makeDaoRequestForDelete(preparedStatement, item);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

     String getSelectOfItem(){
        return null;
     }
     String getSelectByIdOfItem(){
        return null;
    }
     String getInsertOfItem(){
         return null;
     }
    String getDeleteOfItem(){
         return null;
    }
     String getUpdateOfItem(){
         return null;
     }
    abstract void makeDaoRequestForCreate(PreparedStatement preparedStatement, T item) ;
    abstract void makeDaoRequestForUpdate(PreparedStatement preparedStatement,T item) ;
    abstract void makeDaoRequestForDelete(PreparedStatement preparedStatement,T item) ;
    abstract T getItem(ResultSet rs) ;
    abstract List<T> getListOfItems(ResultSet rs, List<T> items) ;
}
