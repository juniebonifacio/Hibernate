package com.luv2code.hibernate.demo.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SampleMainApp {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
			// now use the session object to save / retrieve Java objects
			
		} finally {
			if(null != factory) {
				factory.close();
			}
		}
		
	}
}
