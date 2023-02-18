package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class ActivityTrackerMain {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Activity activity1 = new Activity(LocalDateTime.of(2023, Month.FEBRUARY,12,10,0), "Futás", ActivityType.RUNNING);
            Activity activity2 = new Activity(LocalDateTime.of(2023, Month.FEBRUARY,27,10,0), "Túrázás", ActivityType.HIKING);
            Activity activity3 = new Activity(LocalDateTime.of(2023, Month.MARCH,2,9,0), "Biciklizés", ActivityType.BIKING);
            entityManager.persist(activity1);
            entityManager.persist(activity2);
            entityManager.persist(activity3);
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            List<Activity> activities = entityManager.createQuery("select a from Activity a order by a.startTime",
                    Activity.class).getResultList();
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            Activity activityRead = entityManager.find(Activity.class,1L);
            activityRead.setStartTime(LocalDateTime.of(2023, Month.JULY,1,10,0));
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            Activity activityRead2 = entityManager.find(Activity.class,1L);
            entityManager.remove(activityRead2);
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }



    }
}
