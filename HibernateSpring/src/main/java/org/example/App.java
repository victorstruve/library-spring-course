package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.Period;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try(sessionFactory){
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = session.get(Person.class, 1);

            session.getTransaction().commit();

            System.out.println("Вне сессии");
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            System.out.println("Внутри второй транзакции");

            person= (Person) session.merge(person);
            Hibernate.initialize(person.getItems());
            session.getTransaction().commit();

            person.getItems().forEach(System.out::println);

        }
    }
}
