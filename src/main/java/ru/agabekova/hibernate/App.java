package ru.agabekova.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.agabekova.hibernate.model.Item;
import ru.agabekova.hibernate.model.Person;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println("Получили человека из таблицы");
            System.out.println(person);


            session.getTransaction().commit();
            System.out.println("сессия закончилась");

            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            System.out.println("Внутри второй транзакции");

            person = (Person) session.merge(person);

            Hibernate.initialize(person.getItems());

            session.getTransaction().commit();

            System.out.println(person.getItems());

        } finally {
            sessionFactory.close();
        }
    }
}
