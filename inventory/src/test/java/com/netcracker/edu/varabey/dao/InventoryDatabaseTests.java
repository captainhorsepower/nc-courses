package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.*;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseEntityManagerFactory;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InventoryDatabaseTests {
    private static CategoryDAO caService = new DefaultCategoryDAO();
    private static CustomerDAO cuService = new DefaultCustomerDAO();
    private static OrderDAO oService = new DefaultOrderDAO();
    private static TagDAO tService = new DefaultTagDAO();

    private static EntityManagerFactory emf = PostgreSQLDatabaseEntityManagerFactory.getInstance();

    private static Customer cu1;
    private static Customer cu2;

    private static LocalDate d1 = LocalDate.of(2019, 3, 23);
    private static LocalDate d2 = LocalDate.of(2019, 4, 15);

    private static Category animalsCategory;
    private static Category utilsCategory;

    private static Tag t1;
    private static Tag t2;
    private static Tag t3;

    static {
        cu1 = new Customer("pupkin vasyan", 24);
        cuService.create(cu1);

        cu2 = new Customer("evgenih onehin", 29);
        cuService.create(cu2);

        animalsCategory = new Category("animals");
        caService.create(animalsCategory);

        utilsCategory = new Category("utils");
        caService.create(utilsCategory);

        t1 = new Tag("cheap");
        tService.create(t1);

        t2 = new Tag("loved it");
        tService.create(t2);

        t3 = new Tag("disgusting");
        tService.create(t3);
    }

    @Test
    public void itAtLeastInitializesTest() {
        assertNotNull(cuService.read(cu1.getId()));
        assertNotNull(cuService.read(cu2.getId()));
        assertNotNull(caService.read(animalsCategory.getId()));
        assertNotNull(caService.read(utilsCategory.getId()));
        assertNotNull(tService.read(t1.getId()));
        assertNotNull(tService.read(t2.getId()));
        assertNotNull(tService.read(t3.getId()));
    }

    private Order getO1() {
        Order o1 = new Order(cu1, d1);

        OrderItem item = new OrderItem(10., "tractor", utilsCategory);
        item.addTag(t1);
        item.addTag(t2);
        o1.addItem(item);

        item = new OrderItem(20., "bull", animalsCategory);
        item.addTag(t1);
        o1.addItem(item);

        o1.addItem(new OrderItem(1., "dirt", utilsCategory));
        return o1;
    }

    @Test
    public void createOrderAndSaveItToDataBaseTest() {
        Order o1 = getO1();
        o1 = oService.create(o1);

        Order o2 = oService.read(o1.getId());
        assertNotNull(o2);
        assertEquals(o1.getTotalPrice(), o2.getTotalPrice());
        assertEquals(o1.toString(), o2.toString());

        /* clean-up */
        oService.delete(o1.getId());
        assertNull(oService.read(o1.getId()));
    }

    @Test
    public void readAllOrdersTest() {
        Order o1 = getO1();
        o1 = oService.create(o1);

        Order o2 = new Order(cu1, d2);

        OrderItem item = new OrderItem(100., "tractor", utilsCategory);
        item.addTag(t1);
        item.addTag(t2);
        o2.addItem(item);

        item = new OrderItem(200., "bull", animalsCategory);
        item.addTag(t1);
        item.addTag(t3);
        o2.addItem(item);

        o2.addItem(new OrderItem(300., "dirt", utilsCategory));

        o2 = oService.create(o2);

        List<Order> expected = new ArrayList<>(Arrays.asList(o1, o2));
        List<Order> actual = oService.readAll();

        assertEquals(expected.toString(), actual.toString());

        /* clean-up */
        oService.delete(o1.getId());
        oService.delete(o2.getId());
        assertNull(oService.read(o1.getId()));
        assertNull(oService.read(o2.getId()));
    }

    @Test
    public void updateOrderInDataBaseTest() {
        Order o1 = new Order(cu1, d1);

        OrderItem item1 = new OrderItem(10., "tractor", utilsCategory);
        item1.addTag(t1);
        item1.addTag(t2);
        o1.addItem(item1);

        OrderItem item2 = new OrderItem(20., "bull", animalsCategory);
        item2.addTag(t1);
        o1.addItem(item2);

        OrderItem item3 = new OrderItem(1., "dirt", utilsCategory);
        o1.addItem(item3);

        o1 = oService.create(o1);

        item1 = o1.getItems().get(0);
        item2 = o1.getItems().get(1);
        item3 = o1.getItems().get(2);

        // just add duplicate items
        o1.addItem(item1);
        o1.addItem(item1);

        // update fields
        item2.setName("item 2 updated name");
        item3.setPriceByValue(666.);

        Integer count = o1.getItemCount();
        Double price = o1.getTotalPrice();
        o1 = oService.update(o1);

        assertEquals(count, o1.getItemCount());
        assertEquals(price, o1.getTotalPrice());

        Order o2 = oService.read(o1.getId());
        assertNotNull(o2);
        assertEquals(o1.getItemCount(), o2.getItemCount());
        assertEquals(o1.getTotalPrice(), o2.getTotalPrice());

        // clean-up
        oService.delete(o1.getId());
        assertNull(oService.read(o1.getId()));
    }

    @Test
    public void addAndRemoveOrderItemsUpdateTest() {
        Order o1 = new Order(cu1, d1);

        OrderItem item1 = new OrderItem(10., "tractor", utilsCategory);
        item1.addTag(t1);
        item1.addTag(t2);
        o1.addItem(item1);

        OrderItem item2 = new OrderItem(20., "bull", animalsCategory);
        item2.addTag(t1);
        o1.addItem(item2);

        OrderItem item3 = new OrderItem(1., "dirt", utilsCategory);
        o1.addItem(item3);

        o1 = oService.create(o1);

        item1 = o1.getItems().get(0);
        item2 = o1.getItems().get(1);
        item3 = o1.getItems().get(2);

        o1.removeItem(item1);

        item2.removeTag(t1);
        item2.addTag(t2);

        item3.addTag(t1);
        item3.addTag(t2);

        // add new orderItem
        OrderItem newItem = new OrderItem(999., "new item", animalsCategory);
        o1.addItem(newItem);

        Integer count = o1.getItemCount();
        Double price = o1.getTotalPrice();
        o1 = oService.update(o1);

        assertEquals(count, o1.getItemCount());
        assertEquals(price, o1.getTotalPrice());

        Order o2 = oService.read(o1.getId());
        assertNotNull(o2);
        assertEquals(o1.getItemCount(), o2.getItemCount());
        assertEquals(o1.getTotalPrice(), o2.getTotalPrice());

        List<OrderItem> itemList = o1.getItems();
        o1.removeItem(itemList.get(0));


        cu1 = cuService.read(cu1.getId());
        cu1.getOrders().forEach( o -> oService.delete(o.getId()));
        cu1.getOrders().forEach( o -> assertNull(oService.read(o.getId())));

    }

    @Test
    public void findOrderItemsByCustomerAndTagTest() {
        // order 1 -------------------------------------------------------
        Order o1 = new Order(cu1, d1);

        OrderItem item = new OrderItem(10., "tractor", utilsCategory);
        item.addTag(t1);
        item.addTag(t2);
        o1.addItem(item);

        item = new OrderItem(20., "bull", animalsCategory);
        item.addTag(t1);
        o1.addItem(item);

        o1.addItem(new OrderItem(1., "dirt", utilsCategory));
        // order 2 ---------------------------------------------------------
        Order o2 = new Order(cu1, d2);

        item = new OrderItem(100., "tractor", utilsCategory);
        item.addTag(t1);
        item.addTag(t2);
        o2.addItem(item);

        item = new OrderItem(200., "bull", animalsCategory);
        item.addTag(t1);
        item.addTag(t3);
        o2.addItem(item);

        item = new OrderItem(300., "dirt", utilsCategory);
        item.addTag(t1);
        item.addTag(t3);
        o2.addItem(item);
        // order 3------------------------------------------------------------------
        Order o3 = new Order(cu2, d2);

        item = new OrderItem(100., "tractor", utilsCategory);
        item.addTag(t1);
        item.addTag(t2);
        o3.addItem(item);

        item = new OrderItem(200., "bull", animalsCategory);
        item.addTag(t1);
        item.addTag(t3);
        o3.addItem(item);

        item = new OrderItem(300., "dirt", utilsCategory);
        item.addTag(t1);
        item.addTag(t3);
        o3.addItem(item);

        o1 = oService.create(o1);
        o2 = oService.create(o2);
        o3 = oService.create(o3);

        List<OrderItem> items;

        Integer cu1t1ItemCount = 5;
        items = oService.findAllOrderItemsByCustomerAndTag(cu1, t1);
        assertEquals(cu1t1ItemCount, (Integer) oService.findAllOrderItemsByCustomerAndTag(cu1, t1).size());

        Integer cu1t2ItemCount = 2;
        items = oService.findAllOrderItemsByCustomerAndTag(cu1, t2);
        assertEquals(cu1t2ItemCount, (Integer) oService.findAllOrderItemsByCustomerAndTag(cu1, t2).size());

        Integer cu2t2ItemCount = 1;
        items = oService.findAllOrderItemsByCustomerAndTag(cu2, t2);
        assertEquals(cu2t2ItemCount, (Integer) oService.findAllOrderItemsByCustomerAndTag(cu2, t2).size());

        // clean-up
        oService.delete(o1.getId());
        oService.delete(o2.getId());
        oService.delete(o3.getId());

        assertNull(oService.read(o1.getId()));
        assertNull(oService.read(o2.getId()));
        assertNull(oService.read(o3.getId()));
    }

    @Test
    public void findAllOrderItemsByCustomerAndCategoryTest() {
        // order 1 -------------------------------------------------------
        Order o1 = new Order(cu1, d1);

        OrderItem item = new OrderItem(10., "tractor", utilsCategory);
        item.addTag(t1);
        o1.addItem(item);

        item = new OrderItem(20., "bull", animalsCategory);
        item.addTag(t2);
        o1.addItem(item);

        item = new OrderItem(30., "dirt", utilsCategory);
        item.addTag(t3);
        o1.addItem(item);
        // order 2 ---------------------------------------------------------
        Order o2 = new Order(cu1, d2);

        item = new OrderItem(100., "tractor", animalsCategory);
        item.addTag(t1);
        item.addTag(t2);
        o2.addItem(item);

        item = new OrderItem(200., "bull", utilsCategory);
        item.addTag(t1);
        item.addTag(t3);
        o2.addItem(item);

        item = new OrderItem(300., "dirt", utilsCategory);
        item.addTag(t2);
        item.addTag(t3);
        o2.addItem(item);
        // order 3------------------------------------------------------------------
        Order o3 = new Order(cu2, d2);

        item = new OrderItem(101., "tractor", utilsCategory);
        o3.addItem(item);

        item = new OrderItem(202., "bull", animalsCategory);
        o3.addItem(item);

        item = new OrderItem(303., "dirt", utilsCategory);
        o3.addItem(item);

        o1 = oService.create(o1);
        o2 = oService.create(o2);
        o3 = oService.create(o3);

        List<OrderItem> items;

        Integer cu1AnimalsItemCount = 2;
        items = oService.findAllOrderItemsByCustomerAndCategory(cu1, animalsCategory);
        assertEquals(cu1AnimalsItemCount, (Integer) items.size());

        Integer cu1UtilsItemCount = 4;
        items = oService.findAllOrderItemsByCustomerAndCategory(cu1, utilsCategory);
        assertEquals(cu1UtilsItemCount, (Integer) items.size());

        Integer cu2AnimalsItemCount = 1;
        items = oService.findAllOrderItemsByCustomerAndCategory(cu2, animalsCategory);
        assertEquals(cu2AnimalsItemCount, (Integer) items.size());

        // clean-up
        oService.delete(o1.getId());
        oService.delete(o2.getId());
        oService.delete(o3.getId());

        assertNull(oService.read(o1.getId()));
        assertNull(oService.read(o2.getId()));
        assertNull(oService.read(o3.getId()));
    }

//    @Test
//    public void whatHappensWhenIDeleteCustomerTest() {
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
//        o1 = oService.create(o1);
//        o2 = oService.create(o2);
//        o3 = oService.create(o3);
//
//        cuService.delete(cu1.getId());
//        o1.getItems();
//    }
}
