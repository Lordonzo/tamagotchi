package test.dbTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.*;

import models.database.*;

public class DatabaseTest extends AbstractDB{
    private TamagotchiDB tamaDB;

    @Before
    public void init() {
        this.tamaDB = new TamagotchiDB();
    }

    @Test
    public void connection() {
        assertNotNull(this.loadConnection());
    }

    @Test
    public void createTable() {
        tamaDB.createTable();
        assertTrue(checkIfTableExists("tamagotchi"));
    }

    // @Test
    // public void dropTable() {
    //     tamaDB.delete(1);
    //     assertFalse(checkIfTableExists("tamagotchi"));
    // }

    private boolean checkIfTableExists(String tableName) {
         try (Connection connection = this.loadConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);

            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}