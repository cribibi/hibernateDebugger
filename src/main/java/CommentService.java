import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentService {
    public static CommentService INSTANCE;

    public CommentService() {
    }

    public static CommentService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new CommentService();
        }
        return INSTANCE;
    }

    //metoda create
    public void createNewComment(String commentText, int submId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserService us1=UserService.getINSTANCE();
        Comments newComment = new Comments(commentText, us1.getUserById(submId));
        transaction.commit();
        session.save(newComment);
        session.close();
    }

    //metoda read
    public Comments getCommentById(Integer commentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query commentById = session.createQuery("select c from Comments c where " +
                "c.commentId = :idCautat");
        commentById.setParameter("idCautat", commentId);
        Comments singleResult = (Comments) commentById.getSingleResult();
        return singleResult;
    }

    public List<Comments> getAllCommentsByUserId(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allCommentsByUserId = session.createQuery("from Comments c " +
                "where c.userulCareAScrisCommentul = : idCautat");
        allCommentsByUserId.setParameter("idCautat", userId);
        List issuesByUserIdList = ((org.hibernate.query.Query) allCommentsByUserId).list();
        return issuesByUserIdList;
    }

    public List<Comments> getAllCommentsByUserName(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allCommentsByUserId = session.createQuery("from Comments c " +
                "where c.userulCareAScrisCommentul = : numeCautat");
        allCommentsByUserId.setParameter("numeCautat", userName);
        List issuesByUserIdList = ((org.hibernate.query.Query) allCommentsByUserId).list();
        return issuesByUserIdList;
    }

    public List<Comments> getAllComments() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allComments = session.createQuery("select c from Comments c");
        List allCommentsList = ((org.hibernate.query.Query) allComments).list();
        return allCommentsList;
    }

    //metoda update
    public void updateCommentTextByCommentId(String newCommentText, int commentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Comments CommentsById = getCommentById(commentId);
        CommentsById.commentText = newCommentText;
        session.update(CommentsById);
        transaction.commit();
    }

    public void updateCommentTextByCommentIdHql(String newComment, int commentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql=" update Comments  " +
                " set commentText = :newCommentText " +
                " where commentId = :commentId";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("newCommentText", newComment);
        query.setParameter("commentId", commentId);
        int i = query.executeUpdate();
        transaction.commit();
        session.close();
    }

    //metoda de la mihai facuta pe id - merge
    public void editCommentByUserId(String newCommentText , int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hqlUpdate = "update Comments  set commentText = :newCommentText " +
                " where userulCareAScrisCommentul.userId = :userId";
        int updatedEntities = session.createQuery( hqlUpdate )
                .setParameter("newCommentText", newCommentText)
                .setParameter("userId", userId)
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    //metoda de la mihai modificata
    public void editCommentByUserName(String newCommentText , String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserService us1 = UserService.getINSTANCE();
        User userIdByUserName = us1.getUserByUserName(userName);
        int userIdCareCorespundeNumelui=userIdByUserName.userId;
        String hqlUpdate = "update Comments  set commentText = :newCommentText " +
                " where userulCareAScrisCommentul.userId=: userIdCautat ";
        int updatedEntities = session.createQuery( hqlUpdate )
                .setParameter("newCommentText", newCommentText)
                .setParameter("userIdCautat", userIdCareCorespundeNumelui)
                .executeUpdate();
        transaction.commit();
        session.close();
    }


    //metoda delete
    public void deleteCommentById(int commentId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        String hql=" delete from Comments " +
                " where commentId =: commentId";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("commentId", commentId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
