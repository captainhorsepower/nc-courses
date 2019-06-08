//package com.netcracker.edu.varabey;
//
//import com.netcracker.edu.varabey.inventory.data.entity.Category;
//import com.netcracker.edu.varabey.inventory.data.entity.Customer;
//import com.netcracker.edu.varabey.inventory.data.entity.Tag;
//import com.netcracker.edu.varabey.inventory.web.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ComponentScan(basePackages = {"com.netcracker.edu.varabey.inventory.data.dao"})
//public class TestConfig {
//    @Autowired
//    private OrderService service;
//
//    @Bean
//    public Category utilsCategory() {
//        return service.createCategory(new Category("utils"));
//    }
//
//    @Bean
//    public Category animalsCategory() {
//        return service.createCategory(new Category("animals"));
//    }
//
//    @Bean
//    public Tag t1() {
//        return service.createTag(new Tag("cheap"));
//    }
//
//    @Bean
//    public Tag t2() {
//        return service.createTag(new Tag("loved it"));
//    }
//
//    @Bean
//    public Tag t3() {
//        return service.createTag(new Tag("disgusting"));
//    }
//
//    @Bean
//    public Customer cu1() {
//        return service.createCustomer(new Customer("pupkin vasyan", 24));
//    }
//
//    @Bean
//    public Customer cu2() {
//        return service.createCustomer(new Customer("evgenih onehin", 29));
//    }
//}
