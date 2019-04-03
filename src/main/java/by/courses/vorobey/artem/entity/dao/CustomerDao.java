package by.courses.vorobey.artem.entity.dao;

import by.courses.vorobey.artem.entity.Address;
import by.courses.vorobey.artem.entity.Customer;
import by.courses.vorobey.artem.utils.DatabaseManager;
import by.courses.vorobey.artem.utils.PostgreSQLDatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements DAO<Customer> {

    private static final String CUSTOMERS_TABLE_NAME = "customers";
    private static final String CUSTOMER_INSERT_PARAM_LIST = "(customer_nickname, birthday_date, address_id)";

    private static final String ADDRESS_TABLE_NAME = "addresses";
    private static final String ADDRESS_INSERT_PARAM_LIST = "(city, street, build_number)";

    private static final String SELECT_COL_LIST =
            CUSTOMERS_TABLE_NAME + ".customer_id,"
            + CUSTOMERS_TABLE_NAME + ".customer_nickname,"
            + CUSTOMERS_TABLE_NAME + ".birthday_date,"
            + CUSTOMERS_TABLE_NAME + ".address_id,"
            + ADDRESS_TABLE_NAME + ".customer_id,"
            + ADDRESS_TABLE_NAME + ".city,"
            + ADDRESS_TABLE_NAME + ".street,"
            + ADDRESS_TABLE_NAME + ".build_number";


    private DatabaseManager manager =
            PostgreSQLDatabaseManager.getManager();


    /*
     * return is kind of redundant in my situation, as customer.address is final field
     * but I will keep it, because create usually create returns what it has just created;
     */
    private Address create(Address addr) throws SQLException {
        /* addr is guaranteed to be not null by this point */

        String insertAddressSQL =
                "INSERT INTO " + ADDRESS_TABLE_NAME + ADDRESS_INSERT_PARAM_LIST
                        + " VALUES(?, ?, ?)";

        PreparedStatement st = manager.getConnection()
                .prepareStatement(insertAddressSQL, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, addr.getCity());
        st.setString(2, addr.getStreet());
        st.setInt(3, addr.getBuildNumber());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        /*
         * in case address was added successfully
         * I update address_id inside in addr object
         */
        if (rs.next()) {
            int generatedAddrID = rs.getInt("address_id");
            addr.setAddressId(generatedAddrID);
        } else {
            System.out.println("address wasn't added");
        }

        return addr;
    }

    private Address update(Address addr) throws SQLException {
        String updateAddressSQL =
                "UPDATE " + ADDRESS_TABLE_NAME
                        + " SET"
                            + " customer_id = ?,"
                            + " city = ?,"
                            + " street = ?,"
                            + " build_number = ?"
                        + " WHERE address_id = " + addr.getAddressId();


        PreparedStatement st = manager.getConnection().prepareStatement(updateAddressSQL);

        st.setInt(1, addr.getCustomer().getCustomerId());
        st.setString(2, addr.getCity());
        st.setString(3, addr.getStreet());
        st.setInt(4, addr.getBuildNumber());

        int updatedRowCount = st.executeUpdate();

        System.out.println("updated " + updatedRowCount + " address(es)");

        st.close();

        return addr;
    }

    private void deleteAddress(Long id) {

        try {

            String deleteAddressSQL =
                    "DELETE FROM " + ADDRESS_TABLE_NAME
                            + " WHERE address_id = ?";

            PreparedStatement st = manager.getConnection().prepareStatement(deleteAddressSQL);

            st.setLong(1, id);

            int deletedRowCount = st.executeUpdate();

            System.out.println("deleted " + deletedRowCount + " address(es)");

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * creates customer and corresponding address in db
     * @param customer customer to be added to db
     * @return customer, added to db; null, if adding fails;
     */
    public Customer create(Customer customer) {

        /*
         * if customer != null, then his
         * address is guaranteed to be != null by lombok
         */
        if (customer == null) {
            System.out.println("cannot add null-customer!");
            return null;
        }

        try {

            /*
             * because user and customer have one to one mapping,
             * each user must correspond to his own address,
             * so I have to create it inside addresses table first;
             */
            create(customer.getAddress());

            String insertCustomerSQL =
                    "INSERT INTO " + CUSTOMERS_TABLE_NAME + CUSTOMER_INSERT_PARAM_LIST
                            + "VALUES(?, ?, ?)";

            PreparedStatement st = manager.getConnection()
                    .prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, customer.getNickname().trim());
            st.setDate(2, customer.getBirthday());
            st.setInt(3, customer.getAddress().getAddressId());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            /*
             * in case customer was added successfully
             * I update customer id inside in customer object
             * and customer inside address object
             */
            if (rs.next()) {
                int generatedCustomerID = rs.getInt("customer_id");

                customer.setCustomerId(generatedCustomerID);
                customer.getAddress().setCustomer(customer);

                System.out.println("added " + customer.toString() + " to " + CUSTOMERS_TABLE_NAME + " table");

                /* set proper customer_id in addresses table */
                update(customer.getAddress());

            } else {
                System.out.println("customer " + customer.getNickname() + " wasn't added");
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return customer;
    }

    public Customer read(Long id) {

        if (id == null) {
            System.out.println("cannot find null-id customer!");
            return null;
        }

        Customer customer = null;

        try {

            String findCustomerSQL =
                    "SELECT " + SELECT_COL_LIST
                            + " FROM " + CUSTOMERS_TABLE_NAME
                            + " INNER JOIN " +  ADDRESS_TABLE_NAME
                            + " ON customers.address_id = addresses.address_id"
                    + " WHERE addresses.customer_id = ?";

            PreparedStatement st = manager.getConnection().prepareStatement(findCustomerSQL);

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            /* rs should contain <= 1 result */
            if (rs.next()) {
                Address addr = Address.of(
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getInt("build_number")
                );

                addr.setAddressId(rs.getInt("address_id"));

                customer = Customer.of(
                        rs.getInt("customer_id"),
                        rs.getString("customer_nickname"),
                        rs.getDate("birthday_date"),
                        addr
                );

                addr.setCustomer(customer);

                System.out.println("read : " + customer);

            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return customer;
    }

    public Customer update(Customer customer) {

        if (customer == null) {
            System.out.println("cannot update null customer!");
            return null;
        }

        try {

            /* only nickname, address fields and birthday are updatable */
            update(customer.getAddress());

            String updateCustomerSQL =
                    "UPDATE " + CUSTOMERS_TABLE_NAME
                        + " SET"
                            + " customer_nickname = ?,"
                            + " birthday_date = ?"
                        + " WHERE customer_id = " + customer.getCustomerId();

            PreparedStatement st = manager.getConnection().prepareStatement(updateCustomerSQL);

            st.setString(1, customer.getNickname());
            st.setDate(2, customer.getBirthday());

            int updatedRowCount = st.executeUpdate();

            System.out.println("updated " + updatedRowCount + " customer(s)");

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return customer;
    }

    public void delete(Long id) {

        if (id == null) {
            System.out.println("cannot delete null-id customer!");
            return;
        }

        try {

            Customer customer = read(id);

            String deleteCustomerSQL =
                    "DELETE FROM " + CUSTOMERS_TABLE_NAME
                            + " WHERE customer_id = ?";

            PreparedStatement st = manager.getConnection().prepareStatement(deleteCustomerSQL);

            st.setLong(1, id);

            int deletedRowCount = st.executeUpdate();

            System.out.println("deleted " + deletedRowCount + " customer(s)");

            deleteAddress((long) customer.getAddress().getAddressId());

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> readAll() {
        List<Customer> customers = new ArrayList<Customer>();

        try {

            String findCustomerSQL =
                    "SELECT " + SELECT_COL_LIST
                            + " FROM " + CUSTOMERS_TABLE_NAME
                            + " INNER JOIN " +  ADDRESS_TABLE_NAME
                            + " ON customers.address_id = addresses.address_id";

            PreparedStatement st = manager.getConnection().prepareStatement(findCustomerSQL);

            ResultSet rs = st.executeQuery();

            int readCount = 0;

            while (rs.next()) {
                Address addr = Address.of(
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getInt("build_number")
                );

                addr.setAddressId(rs.getInt("address_id"));

                Customer customer = Customer.of(
                        rs.getInt("customer_id"),
                        rs.getString("customer_nickname"),
                        rs.getDate("birthday_date"),
                        addr
                );

                addr.setCustomer(customer);

                readCount++;

                customers.add(customer);
            }

            st.close();

            System.out.println("read " + readCount + " customers");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return customers;
    }
}
