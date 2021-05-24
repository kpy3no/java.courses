package ru.sber.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.sber.model.Car;
import ru.sber.model.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.*;

@Component
public class UserJdbcTemplateDaoImpl implements UserDao {

    private Map<Long, User> usersMap = new HashMap<>();

    private final String SQL_SELECT_USERS_WITH_CARS =
            "SELECT users.*, car.id as car_id, car.model FROM users LEFT JOIN car ON users.id = car.owner_id";

    private final String SQL_SELECT_ALL_BY_FIRST_NAME = "select * from users where first_name = ?";

    private final String SQL_INSERT_USER = " " +
            "insert into users(first_name, second_name) values (:firstName, :lastName)";


    JdbcTemplate template;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserJdbcTemplateDaoImpl(DataSource source) {
        this.template = new JdbcTemplate(source);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(source);
    }

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_SELECT_ALL_BY_FIRST_NAME, userRowMapper, firstName);
    }

    @Override
    public Optional<User> find(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User model) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", model.getUserName());
        params.put("lastName" , model.getLastName());
        namedParameterJdbcTemplate.update(SQL_INSERT_USER, params);
    }

    @Override
    public List<User> findAll() {
        List<User> result = template.query(SQL_SELECT_USERS_WITH_CARS, userListRowMapper);
        usersMap.clear();
        return result;
    }

    /**
     * Маппер одного юзера
     */
    private RowMapper<User> userRowMapper = (ResultSet resultSet, int i) -> {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("second_name"));
        return user;
    };

    /**
     * Маппер списка юзеров с машинами
     */
    private RowMapper<User> userListRowMapper
            = (ResultSet resultSet, int i) -> {
        Long id = resultSet.getLong("id");

        if (!usersMap.containsKey(id)) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("second_name");
            User user = new User(id, firstName, lastName, new ArrayList<>());
            usersMap.put(id, user);
        }

        Car car = new Car(resultSet.getLong("car_id"),
                resultSet.getString("model"), usersMap.get(id));
        usersMap.get(id).getCars().add(car);
        return usersMap.get(id);
    };

}
