package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapperResultSetExtractor<UserMeal> ROW_MAPPER = new RowMapperResultSetExtractor(new RowMapper() {
        @Override
        public UserMeal mapRow(ResultSet resultSet, int i) throws SQLException {
            UserMeal userMeal=new UserMeal();
            userMeal.setId(resultSet.getInt("id"));
            userMeal.setDescription(resultSet.getString("description"));
            userMeal.setCalories(resultSet.getInt("calories"));
            userMeal.setDateTime(resultSet.getTimestamp("datetime"));
            return userMeal;
        }
    });
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal UserMeal, int userId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", UserMeal.getId())
                .addValue("dateTime", Timestamp.valueOf(UserMeal.getDateTime()))
                .addValue("description", UserMeal.getDescription())
                .addValue("calories", UserMeal.getCalories())
                .addValue("userId", userId);
        if (UserMeal.isNew()) {
            Number newKey = simpleJdbcInsert.executeAndReturnKey(mapSqlParameterSource);
            UserMeal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update("UPDATE meals SET " +
                    "datetime=:dateTime," +
                    "description=:description," +
                    "calories=:calories," +
                    "user_id=:userId " +
                    "WHERE id=:id", mapSqlParameterSource);
        }
        return UserMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {

        List<UserMeal> users = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id,userId);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC",ROW_MAPPER,userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("" +
                "SELECT * FROM meals WHERE user_id=? AND datetime>=? AND datetime<? ",
                ROW_MAPPER,userId,Timestamp.valueOf(startDate),Timestamp.valueOf(endDate));
    }
}
