package com.clover.SpringJDBCSalesManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

  
@Repository
@Transactional
public class SalesDAO {
	
	// specify that an instance of JdbcTemplate class will be automatically created and injected by Spring
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void initTable() {
    	jdbcTemplate.execute(
    			"CREATE TABLE sales(" +
    	        "id bigint AUTO_INCREMENT PRIMARY KEY," +
    			"item VARCHAR(255) NOT NULL," +
    	        "quantity INT NOT NULL," +
    			"amount FLOAT(25) NOT NULL)"
    	);
    }
  
    public List<Sale> list() {
    	String sql = "SELECT * FROM SALES";
    	 
        List<Sale> listSale = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
        // BeanPropertyRowMapper does the mapping values from JDBC ResultSet to Java objects.
        // make sure name of properties matches column name.
     
        return listSale;
    }
    
    public void save(Sale sale) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("sales").usingColumns("item", "quantity", "amount");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(sale);
         
        insertActor.execute(param);    
    }
  
    public Sale get(int id) {
        String sql = "SELECT * FROM SALES WHERE id = ?";
        Object[] args = {id};
        Sale sale = jdbcTemplate.queryForObject(sql,
                        BeanPropertyRowMapper.newInstance(Sale.class), args);
        return sale;
    }
  
    public void update(Sale sale) {
        String sql = "UPDATE SALES SET item=:item, quantity=:quantity, amount=:amount WHERE id=:id";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(sale);
     
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);       
    }
  
    public void delete(int id) {
        String sql = "DELETE FROM SALES WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
