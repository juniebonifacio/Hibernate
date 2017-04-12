package com;

import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.domain.hibernate.Message;

/**
 *	Pseudocode:
 *
 *	1. Create a main program, where I'm going to ask the user to enter in a message.
 *  2. Read that message and then establish a session with the database using hibernate,
 *  3. And then save my message to the database.
 *  4. At the end of the program, just print out all the messages that exist in the 
 *     database, which should grow as we run the program multiple times. 
 */
public class App {
	
	private static SessionFactory factory;
	private static ServiceRegistry registry;
	
    public static void main( String[] args ) {

    	Scanner in = new Scanner(System.in);
    	String m = "";
    	System.out.println("Enter a message: ");
    	m = in.nextLine();
    	
    	try {
    		/** Beginning of Hibernate Code. */
			Configuration conf = new Configuration().configure("/hibernate/hibernate.cfg.xml");
			
    		/** Create my registry. We're going to use this variable when we establish our factory connection. */
			registry = new StandardServiceRegistryBuilder().applySettings(
					conf.getProperties()).build();
    		
			/** Build my session factory object */
			factory = conf.buildSessionFactory(registry);
			
		} catch (HibernateException ex) {
			System.err.println("Failed to create session factory object " + ex);
			throw new ExceptionInInitializerError(ex);
		}
    	
    	/** Open a Hibernate Session */
    	Session session = factory.openSession();
    	Transaction tx = null;
    	Short msgId = null;
    	
    	try {
    		tx = session.beginTransaction();
			Message msg = new Message(m);
			
			/** save() command will return the primary ID */
			msgId = (Short) session.save(msg); 
			
			/** Using HQL hibernate query, select all rows of messages table  */
			List messages = session.createQuery("FROM Message").list();
			
			for(java.util.Iterator iterator = messages.iterator(); iterator.hasNext();) {
				Message message = (Message) iterator.next();
				System.out.println("message: " + message.getMessage());
			}
			
			/**  */
			tx.commit();
			
		} catch (HibernateException e) {
			if(tx != null) {
				tx.rollback();
				e.printStackTrace();
			}
		} finally {
			if(null != session) {
				session.close();				
			}
		}
    	StandardServiceRegistryBuilder.destroy(registry);
    }
}
