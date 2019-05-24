//package com.netcracker.edu.varabey.inventory.data.dao;
//
//import com.netcracker.edu.varabey.inventory.data.entity.*;
//import com.netcracker.edu.varabey.inventory.data.service.OrderService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDateTime;
//import java.fragments.ArrayList;
//import java.fragments.Arrays;
//import java.fragments.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class InventoryDatabaseTests {
//    @Autowired private OrderService service;
//
//    @Autowired private Customer cu1;
//    @Autowired private Customer cu2;
//
//    @Autowired private Category animalsCategory;
//    @Autowired private Category utilsCategory;
//
//    @Autowired private Tag t1;
//    @Autowired private Tag t2;
//    @Autowired private Tag t3;
//
//    private LocalDateTime d1 = LocalDateTime.of(2019, 3, 23, 13, 40, 0);
//    private LocalDateTime d2 = LocalDateTime.of(2019, 4, 15, 10, 10, 10);
//
//    @Test
//    public void itAtLeastInitializesTest() {
//        assertNotNull(service.findCustomer(cu1.getId()));
//        assertNotNull(service.findCustomer(cu2.getId()));
//        assertNotNull(service.findCategory(animalsCategory.getId()));
//        assertNotNull(service.findCategory(utilsCategory.getId()));
//        assertNotNull(service.findTag(t1.getId()));
//        assertNotNull(service.findTag(t2.getId()));
//        assertNotNull(service.findTag(t3.getId()));
//    }
//
//    private Order getO1() {
//        Order o1 = new Order(cu1, d1);
//
//        OrderItem item = new OrderItem(10., "tractor", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t2);
//        o1.addItem(item);
//
//        item = new OrderItem(20., "bull", animalsCategory);
//        item.addTag(t1);
//        o1.addItem(item);
//
//        o1.addItem(new OrderItem(1., "dirt", utilsCategory));
//        return o1;
//    }
//
//    @Test
//    public void createOrderAndSaveItToDataBaseTest() {
//        Order o1 = getO1();
//        o1 = service.save(o1);
//
//        Order o2 = service.findById(o1.getId());
//        assertNotNull(o2);
//        assertEquals(o1.getTotalPrice(), o2.getTotalPrice());
//        assertEquals(o1.toString(), o2.toString());
//
//        /* clean-up */
//        service.deleteOrder(o1.getId());
//        assertNull(service.findById(o1.getId()));
//    }
//
//    @Test
//    public void readAllOrdersTest() {
//        Order o1 = getO1();
//        o1 = service.save(o1);
//
//        Order o2 = new Order(cu1, d2);
//
//        OrderItem item = new OrderItem(100., "tractor", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t2);
//        o2.addItem(item);
//
//        item = new OrderItem(200., "bull", animalsCategory);
//        item.addTag(t1);
//        item.addTag(t3);
//        o2.addItem(item);
//
//        o2.addItem(new OrderItem(300., "dirt", utilsCategory));
//
//        o2 = service.save(o2);
//
//        List<Order> expected = new ArrayList<>(Arrays.asList(o1, o2));
//        List<Order> actual = service.findAll();
//
//        assertEquals(expected.toString(), actual.toString());
//
//        /* clean-up */
//        service.deleteOrder(o1.getId());
//        service.deleteOrder(o2.getId());
//        assertNull(service.findById(o1.getId()));
//        assertNull(service.findById(o2.getId()));
//    }
//
//    @Test
//    public void updateOrderInDataBaseTest() {
//        Order o1 = new Order(cu1, d1);
//
//        OrderItem item1 = new OrderItem(10., "tractor", utilsCategory);
//        item1.addTag(t1);
//        item1.addTag(t2);
//        o1.addItem(item1);
//
//        OrderItem item2 = new OrderItem(20., "bull", animalsCategory);
//        item2.addTag(t1);
//        o1.addItem(item2);
//
//        OrderItem item3 = new OrderItem(1., "dirt", utilsCategory);
//        o1.addItem(item3);
//
//        o1 = service.save(o1);
//
//        item1 = o1.getItems().get(0);
//        item2 = o1.getItems().get(1);
//        item3 = o1.getItems().get(2);
//
//        // just add duplicate items
//        o1.addItem(item1);
//        o1.addItem(item1);
//
//        // update fields
//        item2.setName("item 2 updated name");
//        item3.setPriceByValue(666.);
//
//        Integer count = o1.getItemCount();
//        Double price = o1.getTotalPrice();
//        o1 = service.updateOrder(o1);
//
//        assertEquals(count, o1.getItemCount());
//        assertEquals(price, o1.getTotalPrice());
//
//        Order o2 = service.findById(o1.getId());
//        assertNotNull(o2);
//        assertEquals(o1.getItemCount(), o2.getItemCount());
//        assertEquals(o1.getTotalPrice(), o2.getTotalPrice());
//
//        // clean-up
//        service.deleteOrder(o1.getId());
//        assertNull(service.findById(o1.getId()));
//    }
//
//    @Test
//    public void addAndRemoveOrderItemsUpdateTest() {
//        Order o1 = new Order(cu1, d1);
//
//        OrderItem item1 = new OrderItem(10., "tractor", utilsCategory);
//        item1.addTag(t1);
//        item1.addTag(t2);
//        o1.addItem(item1);
//
//        OrderItem item2 = new OrderItem(20., "bull", animalsCategory);
//        item2.addTag(t1);
//        o1.addItem(item2);
//
//        OrderItem item3 = new OrderItem(1., "dirt", utilsCategory);
//        o1.addItem(item3);
//
//        o1 = service.save(o1);
//
//        item1 = o1.getItems().get(0);
//        item2 = o1.getItems().get(1);
//        item3 = o1.getItems().get(2);
//
//        o1.removeItem(item1);
//
//        item2.removeTag(t1);
//        item2.addTag(t2);
//
//        item3.addTag(t1);
//        item3.addTag(t2);
//
//        // add new orderItem
//        OrderItem newItem = new OrderItem(999., "new item", animalsCategory);
//        o1.addItem(newItem);
//
//        Integer count = o1.getItemCount();
//        Double price = o1.getTotalPrice();
//        o1 = service.updateOrder(o1);
//
//        assertEquals(count, o1.getItemCount());
//        assertEquals(price, o1.getTotalPrice());
//
//        Order o2 = service.findById(o1.getId());
//        assertNotNull(o2);
//        assertEquals(o1.getItemCount(), o2.getItemCount());
//        assertEquals(o1.getTotalPrice(), o2.getTotalPrice());
//
//        List<OrderItem> itemList = o1.getItems();
//        o1.removeItem(itemList.get(0));
//
//
//        cu1 = service.findCustomer(cu1.getId());
//        cu1.getOrders().forEach( o -> service.deleteOrder(o.getId()));
//        cu1.getOrders().forEach( o -> assertNull(service.findById(o.getId())));
//
//    }
//
//    @Test
//    public void findOrderItemsByCustomerAndTagTest() {
//        // order 1 -------------------------------------------------------
//        Order o1 = new Order(cu1, d1);
//
//        OrderItem item = new OrderItem(10., "tractor", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t2);
//        o1.addItem(item);
//
//        item = new OrderItem(20., "bull", animalsCategory);
//        item.addTag(t1);
//        o1.addItem(item);
//
//        o1.addItem(new OrderItem(1., "dirt", utilsCategory));
//        // order 2 ---------------------------------------------------------
//        Order o2 = new Order(cu1, d2);
//
//        item = new OrderItem(100., "tractor", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t2);
//        o2.addItem(item);
//
//        item = new OrderItem(200., "bull", animalsCategory);
//        item.addTag(t1);
//        item.addTag(t3);
//        o2.addItem(item);
//
//        item = new OrderItem(300., "dirt", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t3);
//        o2.addItem(item);
//        // order 3------------------------------------------------------------------
//        Order o3 = new Order(cu2, d2);
//
//        item = new OrderItem(100., "tractor", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t2);
//        o3.addItem(item);
//
//        item = new OrderItem(200., "bull", animalsCategory);
//        item.addTag(t1);
//        item.addTag(t3);
//        o3.addItem(item);
//
//        item = new OrderItem(300., "dirt", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t3);
//        o3.addItem(item);
//
//        o1 = service.save(o1);
//        o2 = service.save(o2);
//        o3 = service.save(o3);
//
//        List<OrderItem> items;
//
//        Integer cu1t1ItemCount = 5;
//        items = service.findAllOrderItemsByCustomerAndTag(cu1, t1);
//        assertEquals(cu1t1ItemCount, (Integer) items.size());
//
//        Integer cu1t2ItemCount = 2;
//        items = service.findAllOrderItemsByCustomerAndTag(cu1, t2);
//        assertEquals(cu1t2ItemCount, (Integer) items.size());
//
//        Integer cu2t2ItemCount = 1;
//        items = service.findAllOrderItemsByCustomerAndTag(cu2, t2);
//        assertEquals(cu2t2ItemCount, (Integer) items.size());
//
//        // clean-up
//        service.deleteOrder(o1.getId());
//        service.deleteOrder(o2.getId());
//        service.deleteOrder(o3.getId());
//
//        assertNull(service.findById(o1.getId()));
//        assertNull(service.findById(o2.getId()));
//        assertNull(service.findById(o3.getId()));
//    }
//
//    @Test
//    public void findAllOrderItemsByCustomerAndCategoryTest() {
//        // order 1 -------------------------------------------------------
//        Order o1 = new Order(cu1, d1);
//
//        OrderItem item = new OrderItem(10., "tractor", utilsCategory);
//        item.addTag(t1);
//        o1.addItem(item);
//
//        item = new OrderItem(20., "bull", animalsCategory);
//        item.addTag(t2);
//        o1.addItem(item);
//
//        item = new OrderItem(30., "dirt", utilsCategory);
//        item.addTag(t3);
//        o1.addItem(item);
//        // order 2 ---------------------------------------------------------
//        Order o2 = new Order(cu1, d2);
//
//        item = new OrderItem(100., "tractor", animalsCategory);
//        item.addTag(t1);
//        item.addTag(t2);
//        o2.addItem(item);
//
//        item = new OrderItem(200., "bull", utilsCategory);
//        item.addTag(t1);
//        item.addTag(t3);
//        o2.addItem(item);
//
//        item = new OrderItem(300., "dirt", utilsCategory);
//        item.addTag(t2);
//        item.addTag(t3);
//        o2.addItem(item);
//        // order 3------------------------------------------------------------------
//        Order o3 = new Order(cu2, d2);
//
//        item = new OrderItem(101., "tractor", utilsCategory);
//        o3.addItem(item);
//
//        item = new OrderItem(202., "bull", animalsCategory);
//        o3.addItem(item);
//
//        item = new OrderItem(303., "dirt", utilsCategory);
//        o3.addItem(item);
//
//        o1 = service.save(o1);
//        o2 = service.save(o2);
//        o3 = service.save(o3);
//
//        List<OrderItem> items;
//
//        Integer cu1AnimalsItemCount = 2;
//        items = service.findAllOrderItemsByCustomerAndCategory(cu1, animalsCategory);
//        assertEquals(cu1AnimalsItemCount, (Integer) items.size());
//
//        Integer cu1UtilsItemCount = 4;
//        items = service.findAllOrderItemsByCustomerAndCategory(cu1, utilsCategory);
//        assertEquals(cu1UtilsItemCount, (Integer) items.size());
//
//        Integer cu2AnimalsItemCount = 1;
//        items = service.findAllOrderItemsByCustomerAndCategory(cu2, animalsCategory);
//        assertEquals(cu2AnimalsItemCount, (Integer) items.size());
//
//        // clean-up
//        service.deleteOrder(o1.getId());
//        service.deleteOrder(o2.getId());
//        service.deleteOrder(o3.getId());
//
//        assertNull(service.findById(o1.getId()));
//        assertNull(service.findById(o2.getId()));
//        assertNull(service.findById(o3.getId()));
//    }
//
//    @Test
//    public void findCategoryByNameTest() {
//        Category utils = service.findCategory("utils");
//        assertEquals(utilsCategory, utils);
//
//        Category notFound = service.findCategory("smth random");
//        assertNull(notFound);
//    }
//}
