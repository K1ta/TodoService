package hello;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
public class HelloController {

    @CrossOrigin(origins="http://localhost:63342")
    @GetMapping(path="/todo/id")
    public Long getId() {
        Long id = 0l;
        try {
            String url = "jdbc:postgresql://localhost:5432/todo";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select min(id) as id from\n" +
                        "(select id+1 as id\n" +
                        "from data\n" +
                        "except\n" +
                        "select id\n" +
                        "from data\n" +
                        ") as res;");
                if(rs.next()) {
                    id = rs.getLong(1);
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

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(path = "/todo/all")
    public ArrayList<ListItem> getAll() {
        ArrayList<ListItem> res = new ArrayList<>();
        try {
            String url = "jdbc:postgresql://localhost:5432/todo";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
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

    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(path = "/todo/remove", consumes="application/json")
    public void removeData(@RequestBody ListItem item) {
        System.out.println(item.getId() + " " + item.getData());
        try {
            String url = "jdbc:postgresql://localhost:5432/todo";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
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

    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(path = "/todo/add")
    public void addData(@RequestBody ListItem item) {
        try {
            String url = "jdbc:postgresql://localhost:5432/todo";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                stmt.execute("INSErt into data VALUES (" + item.getId() + ",'" + item.getData() + "')");
                System.out.println(item.getData());
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}