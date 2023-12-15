package test.dbTest;

import static org.junit.Assert.*;

import org.junit.*;

import models.database.*;

public class DatabaseTest {
    private AbstractDB myDB;

    @Before
    public void init() {
        this.myDB = new TamagotchiDB();
    }

    @Test
    public void connection() {
        //assertNotEquals(null, this.myDB.());
    }

    @Test
    public void createTable() {
        //assertTrue(this.mySQLDB.createTable("player"));
    }

    @Test
    public void dropTable() {
        //assertTrue(this.mySQLDB.dropTable("player"));
    }
}
