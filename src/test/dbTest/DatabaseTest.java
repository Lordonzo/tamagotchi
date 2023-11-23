package test.dbTest;

import static org.junit.Assert.*;

import org.junit.*;

import models.database.*;

public class DatabaseTest {
    private MySQLDB mySQLDB;

    @Before
    public void init() {
        this.mySQLDB = new PlayerDAO();
    }

    @Test
    public void connection() {
        //assertNotEquals(null, this.mySQLDB.LoadConnection());
    }

    @Test
    public void createTable() {
        assertTrue(this.mySQLDB.createTable("player"));
    }

    @Test
    public void dropTable() {
        assertTrue(this.mySQLDB.dropTable("player"));
    }
}
