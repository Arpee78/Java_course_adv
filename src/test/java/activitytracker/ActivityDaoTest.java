package activitytracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ActivityDaoTest {

    private EntityManagerFactory entityManagerFactory;
    private ActivityDao activityDao;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(entityManagerFactory);
    }

    @AfterEach
    void close() {
        entityManagerFactory.close();
    }

    @org.junit.jupiter.api.Test
    void saveActivity() {
        Activity activity = new Activity(
                LocalDateTime.of(2023,3,15,3,5), "Futás az erdőben", ActivityType.RUNNING);
        activityDao.saveActivity(activity);
        Activity expected = activityDao
                .findActivityById(activity.getId());
        assertEquals("Futás az erdőben", expected.getDescription());
    }

    @org.junit.jupiter.api.Test
    void listActivities() {
        Activity activity1 = new Activity(
                LocalDateTime.of(2023,3,15,3,5), "Futás az erdőben", ActivityType.RUNNING);
        Activity activity2 = new Activity(
                LocalDateTime.of(2023,4,15,3,5), "Biciklitúra a tó körül", ActivityType.BIKING);
        Activity activity3 = new Activity(
                LocalDateTime.of(2023,5,15,3,5), "Túra a hegyekben", ActivityType.HIKING);
        activityDao.saveActivity(activity1);
        activityDao.saveActivity(activity2);
        activityDao.saveActivity(activity3);
        List<Activity> activities = activityDao.listActivities();
        assertEquals(3, activities.size());
        assertThat(activities)
                .extracting(Activity::getDescription)
                .contains("Futás az erdőben", "Biciklitúra a tó körül", "Túra a hegyekben");
    }

    @org.junit.jupiter.api.Test
    void findActivityById() {
        Activity activity = new Activity(
                LocalDateTime.of(2023,3,15,3,5), "Futás az erdőben", ActivityType.RUNNING);
        activityDao.saveActivity(activity);
        Activity expected = activityDao.findActivityById(activity.getId());
        assertEquals("Futás az erdőben", expected.getDescription());
    }

    @org.junit.jupiter.api.Test
    void deleteActivity() {
        Activity activity = new Activity(
                LocalDateTime.of(2023,3,15,3,5), "Futás az erdőben", ActivityType.RUNNING);
        activityDao.saveActivity(activity);
        activityDao.deleteActivity(activity.getId());
        List<Activity> expected = activityDao.listActivities();

        assertEquals(0, expected.size());
    }
}