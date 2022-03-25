/**
 * 
 */
package com.promineotech.jeep.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
/**
 * @author D
 *
 */
@Component
@Slf4j
public class DefaultJeepSalesDao implements JeepSalesDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public List<Jeep> fetchJeeps(JeepModel model, String trim) {
    log.debug("JeepSalesDao warm and fuzzy check : model={}, trim={}",model,trim);
   
    String Query1 = "" + "SELECT * " + " FROM models " +
    " WHERE model_id = :model_id AND trim_level= :trim_level";
    Map<String, Object> params = new HashMap<>();
    params.put("model_id",  model.toString());
    params.put("trim_level", trim);
    
    return jdbcTemplate.query(Query1,params,
        new org.springframework.jdbc.core.RowMapper<>() {

          @Override
          public Jeep mapRow(ResultSet rs, int rowNum) throws SQLException {
            // @formatter:off
            return Jeep.builder()
//The video has the Primary key included in the returned results but that is just an auto_increment
//Really don't want to even return this -- the video really goes round and round
// Of course this begs the question of "what if we want the PK for later operations?"                
//                .modelPk(rs.getLong("model_pk"))
                .modelID(JeepModel.valueOf(rs.getString("model_id")))
                .trimLevel(rs.getString("trim_level"))
                .numDoors(rs.getInt("num_doors"))
                .wheelSize(rs.getInt("wheel_size"))
                .basePrice(new BigDecimal(rs.getString("base_price")))
                .build();
            // @formatter:on
          }});
  }
}
// Now this was interesting -- by the actual SpringBoot docs this implements in separate class
//And wow am I glad @Builder exists
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import org.springframework.jdbc.core.RowMapper;
//
//public class JeepMapper implements RowMapper<Jeep> {
//   public Jeep mapRow(ResultSet rs, int rowNum) throws SQLException {
//      Jeep jeep = new Jeep();
//      jeep.setModelPk(rs.getLong("model_pk"))
//      jeep.setModelID(JeepModel.valueOf(rs.getString("model_id")))
//      jeep.setTrimLevel(rs.getString("trim_level"))
//      jeep.setNumDoors(rs.getInt("num_doors"))
//      jeep.setWheelSize(rs.getInt("wheel_size"))
//      jeep.setBasePrice(new BigDecimal(rs.getString("base_price")))
//      return jeep;
//   }
//}
//
//And the call changes to 
//  JdbcTemplate jeepJDBCTemplate; 
//  jeepJDBCTemplate = new JdbcTemplate(dataSource);
// 
//  List <Jeep> jeep = jeepJDBCTemplate.query(SQL,params, new JeepMapper());
//
//With a bean definition 
//<!-- Definition for jeepJDBCTemplate bean -->
//<bean id = "jeepJDBCTemplate" 
//   class = "com. "....stuff_for_rent_here..." .DefaultJeepSalesDao">
//   <property name = "dataSource" ref = "dataSource" />    
//</bean>


