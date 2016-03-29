package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal u WHERE u.id=:id"),
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT u FROM UserMeal u WHERE u.user.id=:userId ORDER BY u.dateTime DESC"),
        @NamedQuery(name = UserMeal.GET_BETWEEN, query = "SELECT u FROM UserMeal u WHERE u.user.id=:userId AND u.dateTime BETWEEN :startDate AND :endDate ORDER BY u.dateTime DESC")
})
@Entity
@Table(name = "meals",uniqueConstraints = {@UniqueConstraint(columnNames = "date_time", name = "meals_unique_date_time_idx")})
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String ALL_SORTED = "UserMeal.allSorted";
    public static final java.lang.String GET_BETWEEN = "UserMeal.getBetween";
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "description")
    private String description;

    @Column(name = "calories")
    protected int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
