import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class IssueService {
    private static IssueService INSTANCE;

    private IssueService() {
    }

    public static IssueService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new IssueService();
        }
        return INSTANCE;
    }

    //metoda create
    public void createNewIssue(String title, String description,
                               int projectId, int submId, int typeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        TypeService ts1 = TypeService.getINSTANCE();
        StatusService ss1 = StatusService.getINSTANCE();
        UserService us1 = UserService.getINSTANCE();
        ProjectService ps1 = ProjectService.getINSTANCE();

        Issue newIssue = new Issue(title, description, ps1.getProjectByID(projectId),
                ss1.getStatusById(1), us1.getUserById(submId), ts1.getTypeById(typeId));

        transaction.commit();
        session.save(newIssue);
        session.close();
    }

    //metoda read
    public Issue getIssueById(Integer issueId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query issueById = session.createQuery("select i from Issue i where " +
                "i.issueId = :idCautat");
        issueById.setParameter("idCautat", issueId);
        Issue singleResult = (Issue) issueById.getSingleResult();
        return singleResult;
    }

    public List<Issue> getAllIssues() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allIssues = session.createQuery("select i from Issue i");
        List allIssuesList = ((org.hibernate.query.Query) allIssues).list();
        return allIssuesList;
    }

    public List<Issue> getAllIssuesByProjectId(int projectId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allIssuesByProjectId = session.createQuery("from Issue i " +
                "where i.projectulDeCareCoresponde.projectId = : idCautat");
        allIssuesByProjectId.setParameter("idCautat", projectId);
        List issuesByProjectIdList = ((org.hibernate.query.Query) allIssuesByProjectId).list();
        return issuesByProjectIdList;
    }

    public List<Issue> getAllIssuesByTypeId(int typeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allIssuesByTypeId = session.createQuery("from Issue i " +
                "where i.tipulDeIssue.typeId = : idCautat");
        allIssuesByTypeId.setParameter("idCautat", typeId);
        List issuesByTypeIdList = ((org.hibernate.query.Query) allIssuesByTypeId).list();
        return issuesByTypeIdList;
    }

    public List<Issue> getAllIssuesByStatusId(int statusId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allIssuesByStatusId = session.createQuery("from Issue i " +
                "where i.statusulIsseului.statusId = : idCautat");
        allIssuesByStatusId.setParameter("idCautat", statusId);
        List issuesByStatusIdList = ((org.hibernate.query.Query) allIssuesByStatusId).list();
        return issuesByStatusIdList;
    }

    public List<Issue> getAllIssuesByStatusName(String statusName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allIssuesByStatusName = session.createQuery("from Issue i " +
                "where i.statusulIsseului.statusName = : status");
        allIssuesByStatusName.setParameter("status", statusName);
        List issuesByStatusNameList = ((org.hibernate.query.Query) allIssuesByStatusName).list();
        return issuesByStatusNameList;
    }

    public List<Issue> getAllIssuesByUserId(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allIssuesByUserId = session.createQuery("from Issue i " +
                "where i.userulCareAScrisProblema.userId = : idCautat");
        allIssuesByUserId.setParameter("idCautat", userId);
        List issuesByUserIdList = ((org.hibernate.query.Query) allIssuesByUserId).list();
        return issuesByUserIdList;
    }

    public List<Issue> getAllIssuesByUserName(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allIssuesByUserName = session.createQuery("from Issue i " +
                "where i.userulCareAScrisProblema.userName = : numeCautat");
        allIssuesByUserName.setParameter("numeCautat", userName);
        List issuesByUserNameList = ((org.hibernate.query.Query) allIssuesByUserName).list();
        return issuesByUserNameList;
    }

    //metoda update

    //nu updateaza daca fieldul e null
    public void updateIssueStatusByIssueId(int issueId, int newStatusId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Issue issueById = getIssueById(issueId);
        issueById.statusulIsseului.statusId = newStatusId;
        session.update(issueById);
        transaction.commit();
    }

    //updateaza ok
    public void updateIssueStatusByIssueIdHql(int issueId, int newStatusId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "update Issue " +
                "set statusulIsseului.statusId =: newStatusId " +
                "where issueId=: issueId";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("newStatusId", newStatusId);
        query.setParameter("issueId", issueId);
        query.executeUpdate();
        transaction.commit();
    }

    public void updateIssueDescriptionByIssueIdHql(String editText, int issueId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hqlUpdate = " update Issue " +
                " set description = :newDescription " +
                " where issueId = :issueId ";
        Query query = session.createQuery(hqlUpdate)
                .setParameter("newDescription", editText)
                .setParameter("issueId", issueId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    //mi-l modifica doar pe primul, nu pe toate, cred ca trebuie un for
    public void updateIssueStatusByUserIdHql(int statusId, int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hqlUpdate = " update Issue " +
                " set statusulIsseului.statusId = :newStatus " +
                " where issueId = :userId ";
        Query query = session.createQuery(hqlUpdate)
                .setParameter("newStatus", statusId)
                .setParameter("userId", userId);
        Transaction transaction = session.beginTransaction();
        int result = query.executeUpdate();
        transaction.commit();
        System.out.println("no of rows "+result);
        session.close();
    }



    //metoda delete
    public void deleteIssueByIdFaraHql(int issueId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Issue issueById = getIssueById(issueId);
        session.delete(issueById);
        transaction.commit();
    }

    public void deleteIssueByIdCuHql(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hqlUpdate =
                " delete from Issue " +
                        " where issueId = :idCautat ";
        Query query = session.createQuery(hqlUpdate).setParameter("idCautat", id);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void deleteIssueByStatusCuHql(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hqlUpdate =
                " delete from Issue " +
                        " where statusulIsseului.statusId = :idCautat ";
        Query query = session.createQuery(hqlUpdate).setParameter("idCautat", id);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
