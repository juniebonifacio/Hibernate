package com;

import java.util.HashSet;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.domain.hibernate.Employee;
import com.domain.hibernate.Phone;

public class HibernateExampleApp {

	private static SessionFactory factory;
	private static ServiceRegistry registry;
	private static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		try {
			Configuration configuration = new Configuration().configure("/hibernate/hibernate.cfg.xml");
			registry = new StandardServiceRegistryBuilder().applySettings(
					configuration.getProperties()).build();
			factory = configuration.buildSessionFactory(registry);
		} catch (HibernateException e) {
			System.err.println("Failed to create sessionFactory object."  + e);
			throw new ExceptionInInitializerError(e);
		}
		
		HibernateExampleApp hibernateExampleApp = new HibernateExampleApp();
		String more = "yes";
		Integer empId = 0;
		
		/** Add few employee records in database */
		while(more.charAt(0) == 'y' || more.charAt(0) == 'Y') {
			empId = hibernateExampleApp.addEmployee();
			System.out.println("More employees? (y/n)");
			more = in.nextLine();
		}
		
		/** Update employee's records */
		hibernateExampleApp.updateEmployee(65,95000);
		
		/** Delete an employee from the database */
		// hibernateExampleApp.deleteEmployee(67);
	}

	/* Method to CREATE an employee in the database */
	public Integer addEmployee() {
		System.out.println("Enter first name: ");
		String firstName = in.nextLine();
		System.out.println("Enter last name: ");
		String lastName = in.nextLine();
		System.out.println("Enter cell: ");
		String cell = in.nextLine();
		System.out.println("Enter home phone: ");
		String hPhone = in.nextLine();
		System.out.println("Enter salary: ");
		int salary = in.nextInt();
		in.nextLine(); /** we need this as on previous line and it leaves the inline symbol in the buffer */
		HashSet<Phone> hashSet = new HashSet<Phone>();
		hashSet.add(new Phone(cell));
		hashSet.add(new Phone(hPhone));
		
		
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;

		try {
			
			tx = session.beginTransaction();
			Employee employee = new Employee(firstName, lastName, salary);
			employee.setPhones(hashSet);
			
			employeeID = (Integer) session.save(employee);
			tx.commit();
			
		} catch (HibernateException e) {
			if(null != tx) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(null != session) {
				session.close();
			}
		}
		
		return employeeID;
	}
	
	private void updateEmployee(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	private void deleteEmployee(int i) {
		// TODO Auto-generated method stub
		
	}
	
}
