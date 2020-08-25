package Server;

import Default.Location;
import Default.MD2;
import Default.Midget;
import Default.Ship;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class DB {
    static Connection connection;
    static PreparedStatement preparedStatement;

    public DB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:1488/lab7","postgres", "Ubreportiv");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
//    CREATE TABLE users
//            (
//                    Login TEXT PRIMARY KEY,
//                    Password TEXT,
//                    Email TEXT
//            );
//    CREATE TABLE midgets
//            (
//                    Id SERIAL PRIMARY KEY,
//                    Name TEXT,
//                    Age INT,
//                    Tall INT,
//                    Iq INT,
//                    Location TEXT,
//                    Date TIMESTAMP,
//                    Login TEXT,
//            );

    public static String info (String login, String password){
        String ass="In Database ";
        try {
            if (!checkUser(login,password).equals("OK")) return "You are not an user" ;
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT COUNT(ID) FROM MIDGETS;");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                ass = ass + res.getInt(1) + " Objects.\n";
            }
            preparedStatement.close();
            preparedStatement = connection.prepareStatement("SELECT COUNT(LOGIN) FROM USERS;");
            res = preparedStatement.executeQuery();
            while (res.next()) {
                ass = ass + res.getString(1) + " Users registered.\n";
            }
            preparedStatement.close();
        } catch (Exception e){
            System.out.println("Error!");
            e.printStackTrace();
        }
        return ass;
    }

//    public static Midget getMidget (int id, String login, String password){
//        try {
//            Class.forName("org.postgresql.Driver");
//            preparedStatement = connection.prepareStatement("SELECT * FROM MIDGETS where id=? and login=? and password=?;");
//            preparedStatement.setInt(1,id);
//            preparedStatement.setString(2,login);
//            preparedStatement.setString(3, password);
//            ResultSet res = preparedStatement.executeQuery();
//            res.next();
//            String name = res.getString("name");
//            int age=res.getInt("age");
//            int tall=res.getInt("tall");
//            int iq=res.getInt("iq");
//            Location location=(Location)res.getObject("location");
//            ZonedDateTime date=res.getTimestamp("date").toLocalDateTime().atZone(ZoneId.systemDefault());
//            return new Midget(name,age,tall,iq,location,date);
//        } catch (Exception e) {
//            System.out.println("Error!");
//        }
//        return null;
//    }



    public static String clear (String login, String password){
        try {
            if (!checkUser(login,password).equals("OK")) return "You are not an user" ;
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT count (*) FROM MIDGETS;");
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            int until = res.getInt("count");
            preparedStatement = connection.prepareStatement("DELETE FROM MIDGETS where login=?;");
            preparedStatement.setString(1,login);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement("SELECT count (*) FROM MIDGETS;");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int after = resultSet.getInt("count");
            if (after == until) return "No";
        } catch (Exception e){
            System.out.println("Error!");
        }
        return "OK";
    }

    public static String remove (Midget m, String login, String password){
        try {
            if (!checkUser(login,password).equals("OK")) return "You are not an user" ;
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT count (*) FROM MIDGETS;");
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            int until = res.getInt("count");
            preparedStatement = connection.prepareStatement("DELETE FROM MIDGETS where id=? and login=?;  ");
            preparedStatement.setInt(1,m.hashCode());
            preparedStatement.setString(2,login);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement("SELECT count (*) FROM MIDGETS;");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int after = resultSet.getInt("count");
            if (after == until) return "No";
        } catch (Exception e){
            System.out.println("Error!");
        }
        return "OK";
    }

    public static String addMidget (Midget m, String login, String password){
        try {
            if (!checkUser(login,password).equals("OK")) return "You are not an user" ;
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("INSERT INTO MIDGETS VALUES (?,?,?,?,?,?,?,?);  ");
            preparedStatement.setInt(1,m.hashCode());
            preparedStatement.setString(2, m.getName());
            preparedStatement.setInt(3,m.getAge());
            preparedStatement.setInt(4,m.getTall());
            preparedStatement.setInt(5,m.getIq());
            preparedStatement.setString(6,m.getLocation().toString());
            ZonedDateTime now = ZonedDateTime.now();
            preparedStatement.setTimestamp(7,Timestamp.valueOf(now.toLocalDateTime()));
            preparedStatement.setString(8,login);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e){
            System.out.println("Error!");
        }
        return "Item added";
    }

    public static String addIfMax (Midget m, String login, String password){
        try {
            if (!checkUser(login,password).equals("OK")) return "You are not an user";
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT * FROM MIDGETS;");
            ResultSet res = preparedStatement.executeQuery();
            int MaxIQ = 0;
            while (res.next()){
                int iq = res.getInt("iq");
                if (iq>=MaxIQ) MaxIQ = iq;
            }
            if (m.getIq()>=MaxIQ) DB.addMidget(m,login,password);
            else return "Midget is not the smartest one";

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }
        return "Item removed";
    }

    public static String showTable(String login, String password){
        Ship ship=new Ship();
        try {
            if (!checkUser(login,password).equals("OK")) return "You are not an user";
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT * FROM MIDGETS;");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                String name = res.getString("name");
                int age = res.getInt("age");
                int tall = res.getInt("tall");
                int iq = res.getInt("iq");
                String loc = res.getString("location");
                int x = Integer.parseInt(loc.substring(loc.indexOf('=')+1,loc.indexOf(',')));
                int y = Integer.parseInt(loc.substring(loc.lastIndexOf('=')+1,loc.indexOf('}')));
                Location location = new Location(x,y);

                ZonedDateTime date=res.getTimestamp("date").toLocalDateTime().atZone(ZoneId.systemDefault());
                ship.add(new Midget(name, age, tall, iq, location, date));
            }
        } catch (Exception e){
            System.out.println("Error!");
        }
        return ship.show();
    }

    public static String checkUser (String login, String password  ){
        String s = "Invalid login or password";
        try {
            String pass = MD2.encrypt(password);
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT * FROM USERS where login=? and password=?;");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,pass);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next())
                s="OK";
            res.close();
        } catch (Exception e){
            System.out.println("Error!");
        }
        return s;
    }

    public static String checkLogin (String login){
        String s = "Invalid login or password";
        try {
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT * FROM USERS where login=?;");
            preparedStatement.setString(1,login);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next())
                s="OK";
            res.close();
        } catch (Exception e){
            System.out.println("Error!");
        }
        return s;
    }

    public static String addUser (String login, String password, String email){
        try {
            Class.forName("org.postgresql.Driver");
            preparedStatement = connection.prepareStatement("SELECT * FROM USERS where login=?;");
            preparedStatement.setString(1,login);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()) return "This user already exist";
            preparedStatement = connection.prepareStatement("INSERT INTO USERS VALUES (?,?,?);  ");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e){
            System.out.println("Error!");
        }
        return "OK";
    }
}
