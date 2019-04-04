package by.courses.vorobey.artem.entity.dao;

import by.courses.vorobey.artem.entity.Item;
import by.courses.vorobey.artem.utils.DatabaseManager;
import by.courses.vorobey.artem.utils.PostgreSQLDatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao implements DAO<Item> {

    private static final String ITEMS_TABLE_NAME = "items";
    private static final String ITEM_INSERT_PARAM_LIST = "(item_name, item_price)";

    private DatabaseManager manager =
            PostgreSQLDatabaseManager.getManager();




    /**
     * creates item in db
     * @param item customer to be added to db
     * @return item, added to db; null, if adding fails;
     */
    @Override
    public Item create(Item item) {

        if (item == null) {
            System.out.println("cannot add null-item!");
            return null;
        }

        try {

            String insertItemSQL =
                    "INSERT INTO "
                        + ITEMS_TABLE_NAME + ITEM_INSERT_PARAM_LIST
                        + "VALUES(?, ?)";

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(insertItemSQL, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, item.getName());
            st.setFloat(2, item.getPrice());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            /*
             * in case item was added successfully
             * I update customer id inside in customer object
             * and customer inside address object
             */
            if (rs.next()) {
                int generatedCustomerID = rs.getInt("item_id");

                item.setItemId(generatedCustomerID);

                System.out.println("added " + item + " to " + ITEMS_TABLE_NAME + " table");

            } else {
                System.out.println("item " + item.getName() + " wasn't added");
            }

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return item;
    }

    @Override
    public Item read(Long id) {

        if (id == null) {
            System.out.println("cannot find null-id item!");
            return null;
        }

        Item item = null;

        try {

            String findItemSQL = "SELECT * FROM " + ITEMS_TABLE_NAME + " WHERE item_id = ?";

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(findItemSQL);

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            /* rs should contain <= 1 result */
            if (rs.next()) {

                item = Item.of(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getFloat("item_price")
                );

                System.out.println("read : " + item);
            }

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return item;
    }

    @Override
    public Item update(Item item) {

        if (item == null) {
            System.out.println("cannot update null item!");
            return null;
        }

        try {

            String updateItemSQL =
                    "UPDATE " + ITEMS_TABLE_NAME
                            + " SET"
                            + " item_name = ?,"
                            + " item_price = ?"
                            + " WHERE item_id = " + item.getItemId();

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(updateItemSQL);

            st.setString(1, item.getName());
            st.setFloat(2, item.getPrice());

            int updatedRowCount = st.executeUpdate();

            System.out.println("updated " + updatedRowCount + " item(s)");

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return item;
    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            System.out.println("cannot delete null-id item!");
            return;
        }

        // TODO: 4/4/2019 deal with foreign keys when I add them
        try {

            String deleteItemSQL =
                    "DELETE FROM " + ITEMS_TABLE_NAME
                            + " WHERE item_id = " + id;

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(deleteItemSQL);

            int deletedRowCount = st.executeUpdate();

            System.out.println("deleted " + deletedRowCount + " item(s)");

            st.close();

            manager.closeConnection(c);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Item> readAll() {
        List<Item> items = new ArrayList<>();

        try {

            String findItemSQL =
                    "SELECT * FROM " + ITEMS_TABLE_NAME;

            Connection c = manager.getConnection();
            PreparedStatement st = c.prepareStatement(findItemSQL);

            ResultSet rs = st.executeQuery();

            int readCount = 0;

            while (rs.next()) {

                Item item = Item.of(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getFloat("item_price")
                );

                readCount++;

                items.add(item);
            }

            st.close();

            manager.closeConnection(c);

            System.out.println("read " + readCount + " items");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return items;
    }
}
