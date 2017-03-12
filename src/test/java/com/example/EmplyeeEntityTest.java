package com.example;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dragonis on 12.03.2017.
 */
public class EmplyeeEntityTest {

    private SessionFactory sessionFactory;
    private Session session = null;

    @Before
    public void before() {

        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.setProperty("hibernate.hbm2ddl.auto", "create");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }

        session = sessionFactory.openSession();

    }


    @Test
    public void returnCorrectrlyCountRowsInTableEmployee() {

        // create the objects needed for testing
        EmployeeEntity employeeEntity = new EmployeeEntity();
        EmployeeEntity employeeEntity2 = new EmployeeEntity();
        EmployeeEntity employeeEntity3 = new EmployeeEntity();

        List<EmployeeEntity> employees = new ArrayList<EmployeeEntity>();

        session.beginTransaction();
        employeeEntity.setEname("Wojtek");
        employeeEntity.setJob("Programista");

        employeeEntity2.setEname("aaa");
        employeeEntity2.setJob("bbb");

        employeeEntity2.setEname("111");
        employeeEntity2.setJob("222");

        employees.add(employeeEntity);
        employees.add(employeeEntity2);
        employees.add(employeeEntity3);

        for (EmployeeEntity employee : employees) {
            session.save(employee);
        }

        session.getTransaction().commit();

        Criteria criteria = session.createCriteria(EmployeeEntity.class);
        List<EmployeeEntity> total = (List<EmployeeEntity>) criteria.list();
        assertEquals(3, total.size());
    }

    @After
    public void after() {
        session.close();
        sessionFactory.close();
    }
}

