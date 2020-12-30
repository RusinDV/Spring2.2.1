package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        if (user.getCar() != null) {
            currentSession.save(user.getCar());
        }
        currentSession.save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserWithCar(String model, int series) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select u from User u where u.car.model = :model and u.car.series = :series",User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        User result = null;
        try {
            result = (User) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Model with series no exists. ");
        }
        return result;
    }


}
