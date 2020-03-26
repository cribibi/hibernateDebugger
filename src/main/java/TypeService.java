import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class TypeService {
    private static TypeService INSTANCE;

    private TypeService() {
    }

    public static TypeService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new TypeService();
        }
        return INSTANCE;
    }

    //metoda create
    public void createNewType(String typeName){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Type newType=new Type(typeName);
        //newType.typeName=typeName;
        transaction.commit();
        session.save(newType);
        session.close();
    }

    //metoda read
    public Type getTypeById(Integer typeId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query typeById=session.createQuery("select t from Type t where " +
                "t.typeId = :idCautat");
        typeById.setParameter("idCautat", typeId);
        Type singleResult = (Type) typeById.getSingleResult();
        return singleResult;
    }

    public List<Type> getAllTypes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query allTypes = session.createQuery("select t from Type t");
        List allTypesList = ((org.hibernate.query.Query) allTypes).list();
        return allTypesList;
    }

    //metoda delete
    public void deleteTypeByID(int typeId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        String hql=" delete from Type " +
                " where typeId =: typeId";
        org.hibernate.query.Query query = session.createQuery(hql);
        query.setParameter("typeId", typeId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }


}
