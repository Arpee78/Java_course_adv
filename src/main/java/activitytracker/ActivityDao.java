package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ActivityDao {

    private EntityManagerFactory entityManagerFactory;

    public ActivityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveActivity(Activity activity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(activity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Activity> listActivities(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select a from Activity a", Activity.class).getResultList();
    }

    public Activity findActivityById(long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Activity.class, id);
    }

    public void deleteActivity(long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager.find(Activity.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(activity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
