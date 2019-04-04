package by.courses.vorobey.artem.entity.dao;

import by.courses.vorobey.artem.entity.Item;
import by.courses.vorobey.artem.entity.Order;
import by.courses.vorobey.artem.utils.DatabaseConnectionManager;
import by.courses.vorobey.artem.utils.PostgreSQLDatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDao implements DAO<Order> {

    private static final String ORDERS_TABLE_NAME = "orders";
    private static final String ORDER_INSERT_PARAM_LIST =
            "(customer_id, item_count, total_price, order_date)";

    private static final String ORDER_ITEMS_TABLE_NAME = "order_items_pending";
    private static final String ORDER_ITEMS_INSERT_PARAM_LIST = "(order_id, item_id)";

    private DatabaseConnectionManager manager =
            PostgreSQLDatabaseConnectionManager.getManager();

    /** create single order-item record in db */
    private void createOrderItemRecord(Long orderId, Long itemId) throws SQLException {

        if (orderId == null || itemId == null) {
            System.out.println("something wrong with given order and item id, cannot add this record...");
            return;
        }

        String insertItemSQL = "INSERT INTO " + ORDER_ITEMS_TABLE_NAME
                + ORDER_ITEMS_INSERT_PARAM_LIST + "VALUES(?, ?)";

        Connection c = manager.getConnection();
        PreparedStatement st = c.prepareStatement(insertItemSQL, Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, Math.toIntExact(orderId));
        st.setInt(2, Math.toIntExact(itemId));

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            System.out.println("added order-item record" + " to " + ORDER_ITEMS_TABLE_NAME + " table");
        }

        st.close();

        manager.closeConnection(c);

    }

    /** update single order-item record in db */
    private void updateOrderItemRecord(Long recordId, Long itemId) throws SQLException {
        String updateRecordSQL =
                "UPDATE " + ORDER_ITEMS_TABLE_NAME
                        + " SET"
                        + " item_id = " + itemId
                        + " WHERE record_id = " + recordId;

        Connection c = manager.getConnection();
        PreparedStatement st = c.prepareStatement(updateRecordSQL);

        st.executeUpdate();

        System.out.println("updated order-item record");

        st.close();

        manager.closeConnection(c);
    }

    /** delete single order-item record in db */
    private void deleteOrderItemRecord(Long recordId) throws SQLException {
        String deleteRecordSQL = "DELETE FROM " + ORDER_ITEMS_TABLE_NAME
                + " WHERE record_id = " + recordId;

        Connection c = manager.getConnection();
        PreparedStatement st = c.prepareStatement(deleteRecordSQL);

        st.executeUpdate();

        System.out.println("deleted order-item record");

        st.close();

        manager.closeConnection(c);
    }

    /** update all order-item records, that correspond to given order */
    private void updateOrderItemsRecords(Order order) throws SQLException {

        String findOldItemsSQL = "SELECT record_id, item_id FROM " + ORDER_ITEMS_TABLE_NAME
                + " WHERE " + ORDER_ITEMS_TABLE_NAME + ".order_id = " + order.getOrderId();

        Connection c = manager.getConnection();
        PreparedStatement st = c.prepareStatement(findOldItemsSQL);

        ResultSet rs = st.executeQuery();


        /*
         * this list contains record_ids of order-item records,
         * that should no longer be included in given order.
         */
        List<Long> oldExtraItemsRecordIDs = new ArrayList<>(rs.getFetchSize());
        /*
         * this is list of item_ids in updated order items
         */
        List<Long> newItemIDs = order.getItems().stream()
                .map(Item::getItemId)
                .collect(Collectors.toList());

        /*
         * after this while loop
         * 1)  oldExtraItemsRecordIDs will contain all record_ids,
         *      that should be overwritten or updated.
         * 2)  newItemIDs will contain only items, that are in
         *      updated order, but aren't in the old order.
         */
        while (rs.next()) {

            Long oldItemID = rs.getLong("item_id");

            /*
             * if new items contain item already stored item ->
             * then there is no need to update it, should keep it.
             * otherwise, this is extra item
             */
            if (!newItemIDs.remove(oldItemID)) {
                Long extraRecordId = rs.getLong("record_id");
                oldExtraItemsRecordIDs.add( extraRecordId );
            }
        }

        while (!newItemIDs.isEmpty()) {
            if (oldExtraItemsRecordIDs.isEmpty()) {
                createOrderItemRecord(
                        order.getOrderId(), newItemIDs.remove(0)
                );
            } else {
                updateOrderItemRecord(
                        oldExtraItemsRecordIDs.remove(0), newItemIDs.remove(0));
            }
        }

        while (!oldExtraItemsRecordIDs.isEmpty()) {
            deleteOrderItemRecord(oldExtraItemsRecordIDs.remove(0));
        }

        st.close();

        manager.closeConnection(c);
    }


    /**
     * creates order in db
     * @param order to be created
     * @return created order
     */
    @Override
    public Order create(Order order) {

        if (order == null) {
            System.out.println("cannot add null-order!");
            return null;
        }

        try {

            String insertOrderSQL = "INSERT INTO " + ORDERS_TABLE_NAME + ORDER_INSERT_PARAM_LIST
                        + "VALUES(?, ?, ?, ?)";

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);

            st.setLong(1, order.getCustomerId());
            st.setInt(2, order.getItemCount());
            st.setFloat(3, order.getPrice());
            st.setDate(4, order.getOrderDate());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            /*
             * in case order was added successfully
             * I update customer id inside in customer object
             * and customer inside address object
             */
            if (rs.next()) {
                long generatedOrderID = rs.getInt("order_id");

                order.setOrderId(generatedOrderID);

                System.out.println("added order (id=" + order.getOrderId() + ") to " + ORDERS_TABLE_NAME + " table");

                /*
                 * wanted to use stream, but there is a problem with catching exception
                 * in future releases this might be updated.
                 */
                for (Item i : order.getItems()) {
                    createOrderItemRecord(order.getOrderId(), i.getItemId());
                }

            } else {
                System.out.println("order wasn't added");
            }

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return order;
    }

    /**
     * retrieve order with target id from db
     * @param id target id
     * @return read order
     */
    @Override
    public Order read(Long id) {

        if (id == null) {
            System.out.println("cannot find null-id order!");
            return null;
        }

        Order order = null;

        try {

            String findOrderSQL = "SELECT * FROM "
                    + ORDERS_TABLE_NAME
                    + " JOIN " + ORDER_ITEMS_TABLE_NAME
                    + " ON orders.order_id = order_items_pending.order_id"
                    + " WHERE orders.order_id = " + id;

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(findOrderSQL);

            ResultSet rs = st.executeQuery();

            /* rs should contain <= 1 result */
            if (rs.next()) {

                order = Order.of();
                order.setOrderDate(rs.getDate("order_date"));
                order.setCustomerId(rs.getLong("customer_id"));
                order.setOrderId(rs.getLong("order_id"));

                int expectedItemCount = rs.getInt("item_count");

                List<Item> items = new ArrayList<>(expectedItemCount);

                ItemDao itemDao = new ItemDao();

                do {
                    Item item = itemDao.read((long) rs.getInt("item_id"));
                    items.add(item);
                    expectedItemCount--;
                }
                while(rs.next());

                order.setItems(items);

                System.out.println("read : order (id=" + order.getOrderId() + ")");
                System.out.println("items delta = " + expectedItemCount);
            }

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return order;
    }

    /**
     * updates allowed order properties and corresponding db records.
     * You are allowed to update
     *  'order date'
     *   items in list (remove are add new from available items)
     * @param order order with updates
     * @return updated order
     */
    @Override
    public Order update(Order order) {

        if (order == null) {
            System.out.println("cannot update null order!");
            return null;
        }

        try {


            String updateOrderSQL =
                    "UPDATE " + ORDERS_TABLE_NAME
                            + " SET"
                            + " order_date = ?,"
                            + " item_count = ?,"
                            + " total_price = ?"
                            + " WHERE order_id = " + order.getOrderId();

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(updateOrderSQL);

            st.setDate(1, order.getOrderDate());
            st.setInt(2, order.getItemCount());
            st.setFloat(3, order.getPrice());

            int updatedRowCount = st.executeUpdate();

            updateOrderItemsRecords(order);

            System.out.println("updated " + updatedRowCount + " order(s)");

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return order;
    }

    /**
     * removes target order and corresponding order-item records from db
     * @param id of target order
     */
    @Override
    public void delete(Long id) {

        if (id == null) {
            System.out.println("cannot delete null-id order!");
            return;
        }

        try {

            String deleteOrderSQL =
                    "DELETE FROM " + ORDERS_TABLE_NAME
                            + " WHERE order_id = " + id + ";\n"
                    + "DELETE FROM " + ORDER_ITEMS_TABLE_NAME
                            + " WHERE order_id = " + id;

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(deleteOrderSQL);

            int deletedRowCount = st.executeUpdate();

            System.out.println("deleted " + deletedRowCount + " order(s)");

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return list of all present in db orders
     */
    @Override
    public List<Order> readAll() {
        List<Order> orders = new ArrayList<>();

        try {

            String readAllOrderIDsSQL = "SELECT order_id FROM " + ORDERS_TABLE_NAME;

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(readAllOrderIDsSQL);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                orders.add( read( (long) rs.getInt("order_id")) );
            }

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
