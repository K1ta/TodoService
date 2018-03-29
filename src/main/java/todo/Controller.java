package todo;

import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@RestController
public class Controller {

    @CrossOrigin
    @GetMapping(path = "/todo/id")
    public Long getId() {
        Long id = 0l;
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/application.properties"));
            Connection con = DriverManager.getConnection(props.getProperty("url"), props.getProperty("login"), props.getProperty("password"));
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select id from data where id = 0");
                if (rs.next()) {
                    rs = stmt.executeQuery("select min(id) as id from\n" +
                            "(select id+1 as id\n" +
                            "from data\n" +
                            "except\n" +
                            "select id\n" +
                            "from data\n" +
                            ") as res;");
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @CrossOrigin
    @GetMapping(path = "/todo/all")
    public ArrayList<ListItem> getAll() {
        ArrayList<ListItem> res = new ArrayList<>();
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/application.properties"));
            Connection con = DriverManager.getConnection(props.getProperty("url"), props.getProperty("login"), props.getProperty("password"));
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM data");
                while (rs.next()) {
                    res.add(new ListItem(rs.getLong(1), rs.getString(2)));
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @CrossOrigin
    @PostMapping(path = "/todo/remove")
    public void removeData(@RequestBody ListItem item) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/application.properties"));
            Connection con = DriverManager.getConnection(props.getProperty("url"), props.getProperty("login"), props.getProperty("password"));
            try {
                Statement stmt = con.createStatement();
                stmt.execute("DELETE from data WHERE id='" + item.getId() + "'");
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin
    @PostMapping(path = "/todo/add")
    public void addData(@RequestBody ListItem item) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/application.properties"));
            Connection con = DriverManager.getConnection(props.getProperty("url"), props.getProperty("login"), props.getProperty("password"));
            try {
                Statement stmt = con.createStatement();
                stmt.execute("INSErt into data VALUES (" + getId() + ",'" + item.getData() + "')");
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}