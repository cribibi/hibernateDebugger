import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class StatusService {
    private static StatusService INSTANCE;

    private StatusService() {
    }

    public static StatusService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new StatusService();
        }
        return INSTANCE;
    }

    //metoda create
    public void createNewStatus(String statuseName){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Status newStatus=new Status(statuseName);
        //newStatus.statusName=statuseName;
        transaction.commit();
        session.save(newStatus);
        session.close();
    }

    //metoda read
    public Status getStatusById(Integer statusId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query statusById=session.createQuery("select s from Status s where " +
                "s.statusId = :idCautat");
        statusById.setParameter("idCautat", statusId);
        Status singleResult = (Status) statusById.getSingleResult();
        return singleResult;
    }

    public List<Status> getAllStatuses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allStatuses = session.createQuery("select s from Status s");
        List allStatusesList = ((org.hibernate.query.Query) allStatuses).list();
        return allStatusesList;
    }

    //metoda delete
    public void deleteStatusById(int statusId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        String hql=" delete from Status " +
                " where statusId =: statusId";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("statusId", statusId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
