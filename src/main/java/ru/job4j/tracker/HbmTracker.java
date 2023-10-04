package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "UPDATE Item SET name = :fName, created = :fCreated WHERE id = :fID")
                    .setParameter("fName", item.getName())
                    .setParameter("fCreated", item.getCreated())
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE Item WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        Query<Item> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery("FROM Item", Item.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return query.list();
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        Query<Item> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "FROM Item as i WHERE i.name = :fKey", Item.class);
            query.setParameter("fKey", "%" + key + "%");
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return query.list();
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        Query<Item> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "FROM Item as i WHERE i.id = :fId", Item.class);
            query.setParameter("fId", id);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return query.uniqueResult();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
