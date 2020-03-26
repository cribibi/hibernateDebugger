import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserService {
    public static UserService INSTANCE;

    public UserService() {
    }

    public static UserService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }
    //metoda create
    public void createNewUser(String userName){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User newUser=new User(userName);
        //newUser.userName=userName;
        transaction.commit();
        session.save(newUser);
        session.close();
    }


    //metoda read
    public User getUserById(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query userById=session.createQuery("select u from User u where " +
                "u.userId = :idCautat");

        userById.setParameter("idCautat", userId);
        User singleResult = (User) userById.getSingleResult();
        return singleResult;
    }

    public User getUserByUserName(String userName){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query userByName=session.createQuery("select u from User u where " +
                "u.userName = :numeCautat");
        userByName.setParameter("numeCautat", userName);
        User singleResult = (User) userByName.getSingleResult();
        return singleResult;
    }

    public User getUserNameById(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query userById=session.createQuery("select u.userName from User u where " +
                "u.userId = :idCautat");

        userById.setParameter("idCautat", userId);
        User singleResult = (User) userById.getSingleResult();
        return singleResult;
    }

    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allUSers = session.createQuery("select u from User u");
        List allUsersList = ((org.hibernate.query.Query) allUSers).list();
        return allUsersList;
    }

    //metoda delete
    public void deleteUserByUserName(String userName){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        String hql=" delete from User where " +
                " userName =: username";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("username", userName );
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void deleteUserByUserId(int userId){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        String hql=" delete from User where " +
                " userId =: userid";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("userid", userId );
        query.executeUpdate();
        transaction.commit();
        session.close();
    }



}
