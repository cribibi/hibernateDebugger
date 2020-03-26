import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class ProjectService {

    private static ProjectService INSTANCE;

    private ProjectService() {
    }

    public static ProjectService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ProjectService();
        }
        return INSTANCE;
    }

    //metoda create
    public void createNewProject(String projectName, String identifier, String description){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Project newProject=new Project(projectName, identifier, description);
        transaction.commit();
        session.save(newProject);
        session.close();
    }

    //metoda read
    public Project getProjectByID(Integer projectId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query projectById=session.createQuery("select p from Project p where " +
                "p.projectId = :idCautat");
        projectById.setParameter("idCautat", projectId);
        Project singleResult = (Project) projectById.getSingleResult();
        return singleResult;
    }

    public Project getProjectByIdentifier(String identifier){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query projectById=session.createQuery("select p from Project p where " +
                "p.identifier = :identifierCautat");
        projectById.setParameter("identifierCautat", identifier);
        Project singleResult = (Project) projectById.getSingleResult();
        return singleResult;
    }

    public List<Project> getAllProjects() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allProjects = session.createQuery("select p.name from Project p");
        List projectsList = ((org.hibernate.query.Query) allProjects).list();
        return projectsList;
    }

    public List<Project> getAllProjectsByUser(Integer userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allProjectsByUSer = session.createQuery("select p.name from Project p ");
        List projectsList = ((org.hibernate.query.Query) allProjectsByUSer).list();
        return projectsList;
    }

    //metoda delete
    public void deleteProjectById(int projectId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        String hql=" delete from Project " +
                " where projectId =: projectId";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("projectId", projectId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }



}
