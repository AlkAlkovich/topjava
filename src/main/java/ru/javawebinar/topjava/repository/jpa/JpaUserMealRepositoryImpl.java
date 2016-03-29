package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional
public class JpaUserMealRepositoryImpl implements UserMealRepository {


    @PersistenceContext
    private EntityManager em;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            userMeal.setUser(em.getReference(User.class, userId));
            em.persist(userMeal);
        } else {
            User user = (em.getReference(User.class, userId));
            if(userMeal.getUser()==null){
                userMeal.setUser(user);
                em.merge(userMeal);
            }else if (user.getId().equals(userMeal.getUser().getId())){
                em.merge(userMeal);
            }else {
                return null;
            }
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        UserMeal userMeal = get(id, userId);
        if (userMeal != null) {
            em.remove(userMeal);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = em.find(UserMeal.class, id);
        return userMeal.getUser().getId() == userId ? userMeal : null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        Query query = em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Query query = em.createNamedQuery(UserMeal.GET_BETWEEN, UserMeal.class);
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }
}