package by.courses.vorobey.artem;

import by.courses.vorobey.artem.entity.Customer;
import by.courses.vorobey.artem.entity.Item;
import by.courses.vorobey.artem.entity.Order;
import by.courses.vorobey.artem.entity.dao.CustomerDao;
import by.courses.vorobey.artem.entity.dao.ItemDao;
import by.courses.vorobey.artem.entity.dao.OrderDao;

import java.sql.Date;
import java.sql.SQLOutput;
import java.util.List;

public class JDBCClient {
    public static void main(String[] args) {
        System.out.println("Welcome!");
        System.out.println("This is my PostgreSQL (Hello World) \'WhereHouse Order Control\' app.");

        System.out.println("\nNow, I will show off what my program can do.");
        System.out.println("Most of DAO methods produce \'logs\' to console, " +
                "so you will be able to see what happens internally.");

        System.out.println();
        System.out.println("Here, I have 3 types of mappings:");
        System.out.println("\tcustomer-address : one to one");
        System.out.println("\tcustomer-order : one to many");
        System.out.println("\torder-item : many to many");

        System.out.println("\nLet's Go:\n\n");

        testDatabase();
    }

    private static void testDatabase() {
        CustomerDao customerDao = new CustomerDao();
        ItemDao itemDao = new ItemDao();
        OrderDao orderDao = new OrderDao();




        System.out.println();
        System.out.println("all customers : (readAll())");

        List<Customer> customers = customerDao.readAll();

        System.out.println("manually printing...");
        customers.forEach(c -> System.out.println(c + "\n"));





        System.out.println();
        System.out.println("all items for orders : (readAll())");

        List<Item> items = itemDao.readAll();

        System.out.println("manually printing...");
        items.forEach(System.out::println);




        System.out.println();
        System.out.println("all orders : (readAll())");

        List<Order> orders = orderDao.readAll();

        System.out.println();
        System.out.println("manually printing...");
        orders.forEach(o -> System.out.println(o + "\n"));





        System.out.println();
        System.out.println("update first order...");

        Order o = orders.get(0);

        o.addItem(items.get(0));
        o.addItem(items.get( items.size() - 1) );
        o.addItem(items.get( items.size() / 2) );

        o.setOrderDate(Date.valueOf("2015-12-24"));

        orderDao.update(o);


        System.out.println();
        System.out.println("add new order");
        o.setCustomerId( customers.get(0).getCustomerId() );
        o.addItem(items.get( items.size() - 1) );
        o.addItem(items.get( items.size() - 1) );
        o.addItem(items.get( items.size() - 1) );
        o.addItem(items.get( items.size() - 1) );

        orderDao.create(o);


        System.out.println("update first item...");
        Item i = items.get(0);
        i.setPrice( i.getPrice() + 12.50F );

        itemDao.update(i);




        System.out.println("read all orders again");
        orders = orderDao.readAll();
        System.out.println();
        System.out.println("manually printing...");
        orders.forEach(ord -> System.out.println(ord + "\n"));



        orderDao.delete(13L);
        orderDao.delete(14L);
        orderDao.delete(15L);
        orderDao.delete(16L);
        orderDao.delete(19L);
        orderDao.delete(20L);

    }
}
