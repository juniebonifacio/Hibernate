package com.luv2code.hibernate.demo.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudentDemoApp {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
			// create a student object
			Student tempStudent = new Student("Paul", "Wall", "paul@luv2code.com");
			
			// start transaction
			session.beginTransaction();
			
			// save the student
			session.save(tempStudent);
			
			// commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
		} finally {
			if(null != factory) {
				factory.close();
			}
		}
		
	}
}
