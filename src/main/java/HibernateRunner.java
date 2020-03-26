import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HibernateRunner {
    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        ProjectService ps1 = ProjectService.getINSTANCE();
        IssueService is1 = IssueService.getINSTANCE();
        CommentService cs1 = CommentService.getINSTANCE();
        TypeService ts1 = TypeService.getINSTANCE();
        UserService us1 = UserService.getINSTANCE();
        StatusService ss1 = StatusService.getINSTANCE();

        cs1.editCommentByUserName("comment nou", "Bianca Cricler");
        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }
}
