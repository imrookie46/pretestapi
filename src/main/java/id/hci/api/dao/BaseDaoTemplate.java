package id.hci.api.dao;


import id.hci.api.dao.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BaseDaoTemplate {

    @Autowired
    JdbcTemplate jdbc;

    public List<Movie> getHistoryMovie(int idCustomer) {
        String sql = " SELECT distinct a.* from movie_master a ," +
                " customer_history b, movie_serial c " +
                "where c.id_serial = b.id_serial " +
                "and c.id_movie = a.id_movie " +
                "and b.id_customer = "+idCustomer;

        return jdbc.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class));
    }

    public List<Movie> getMovieCategory(int idCategory) {
        String sql = " select a.* " +
                "from movie_master a, movie_category b, category_master c " +
                "where a.id_movie = b.id_movie " +
                "and c.id_category = b.id_category " +
                "and b.id_category = "+idCategory;

        return jdbc.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class));
    }

    public List<Movie> getMovieSerial(int idMovie) {
        String sql = " select a.*,b.* " +
                "from  movie_master a, movie_serial b " +
                "where a.id_movie = b.id_movie " +
                "and a.id_movie ="+idMovie;

        return jdbc.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class));
    }

    public List<Movie> getMovieSerialExclude(int idMovie, int serial) {
        String sql = " select a.*,b.* " +
                "from  movie_master a, movie_serial b " +
                "where a.id_movie = b.id_movie " +
                "and a.id_movie ="+idMovie+ " and b.id_serial != "+serial;

        return jdbc.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class));
    }

    public String getPathVideo(int id_serial) {
        String sql = "SELECT video_path from movie_serial " +
                "where id_serial ="+id_serial;
        return jdbc.queryForObject(sql, String.class);
    }

    public Customer getCustomerDetail(String userName){
        String sql = "SELECT  * from customer where user_name = ? ";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<Customer>(Customer.class);
        return jdbc.queryForObject(sql,rowMapper,userName);
    }

    @Transactional
    public int saveHistory(int idCustomer, int idSerial) throws Exception {
        try {

            String sql = "INSERT INTO customer_history ( created_date ,"+
                    "   id_customer, " +
                    "   id_serial) " +
                    " VALUES (" +
                    "   now()," +
                    "   ?, ? )";
            jdbc.update(sql,
                    idCustomer,
                    idSerial);
            return idCustomer;
        } catch (DuplicateKeyException duplicate) {
            throw new Exception("failed save");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}