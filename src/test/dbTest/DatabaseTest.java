package test.dbTest;

import static org.junit.Assert.*;

import org.junit.*;

import models.database.*;

public class DatabaseTest {
    private MySQLDB mySQLDB;

    @Before
    public void Init() {
        this.mySQLDB = new PlayerDAO();
    }

    @Test
    public void Connection() {
        //assertNotEquals(null, this.mySQLDB.LoadConnection());
    }

    @Test
    public void CreateTable() {
        assertTrue(this.mySQLDB.CreateTable("player"));
    }

    @Test
    public void DropTable() {
        assertTrue(this.mySQLDB.DropTable("player"));
    }
}
