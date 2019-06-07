//package com.netcracker.edu.varabey.dao;
//
//import Customer;
//import CustomerService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CustomerManagementDaoTests {
//
//    @Autowired
//    CustomerService service;
//
//    @Test
//    @Transactional
//    public void createSomeCustomersThenReadAllAndFinallyDelete() {
//
//        Customer c1 = new Customer("name 1", 20);
//        Customer c2 = new Customer("name 2", 25);
//        Customer c3 = new Customer("name 3", 40);
//
//        service.save(c1);
//        service.save(c2);
//        service.save(c3);
//
//        List<Customer> customers = service.findAll();
//
//        assertEquals(3, customers.size());
//        assertEquals(c1.getFio(), customers.get(0).getFio());
//        assertEquals(c2.getFio(), customers.get(1).getFio());
//        assertEquals(c3.getFio(), customers.get(2).getFio());
//
//        service.delete(c1.getId());
//        service.delete(c2.getId());
//        service.delete(c3.getId());
//
//        customers = service.findAll();
//        assertEquals(0, customers.size());
//    }
//
//
//    @Test
//    public void findByIdTest() {
//        Customer c1 = new Customer("name 1", 20);
//        Customer c2 = new Customer("name 2", 25);
//        Customer c3 = new Customer("name 3", 40);
//
//        service.save(c1);
//        service.save(c2);
//        service.save(c3);
//
//        assertEquals(c1.getFio(), service.find(c1.getId()).getFio());
//        assertEquals(c3.getFio(), service.find(c3.getId()).getFio());
//
//        service.delete(c1.getId());
//        service.delete(c2.getId());
//        service.delete(c3.getId());
//
//        assertEquals(0, service.findAll().size());
//    }
//
//    @Test
//    public void UpdateCustomerNameTest() {
//        Customer c1 = new Customer("name 1", 20);
//        c1 = service.save(c1);
//
//        assertEquals("name 1", c1.getFio());
//
//        c1.setFio("name 10001010");
//        c1 = service.update(c1);
//
//        assertEquals("name 10001010", c1.getFio());
//        service.delete(c1.getId());
//        assertNull(service.find(c1.getId()));
//        Customer c2 = new Customer("name 2", 30);
//        try {
//            service.update(c2);
//        } catch (IllegalArgumentException exc) {
//            assertTrue(!false);
//            return;
//        }
//
//        fail();
//    }
//}
