package Lesson2;

import java.sql.*;

public class MainHW2 {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void setConnection() throws ClassNotFoundException, SQLException {
        // для MySQL
        //        Class.forName("com.mysql.cj.jdbc.Driver");
        //        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/j3_test_schema?verifyServerCertificate=false&useSSL=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root" ,"12345");

        Class.forName("org.sqlite.JDBC");       // jdbc:sqlite:main.db
        connection = DriverManager.getConnection("jdbc:sqlite:main_j3.db");
        stmt = connection.createStatement();

    }

    public static void disconnect() throws SQLException {
        connection.close();
    }

    public static void main(String[] args) throws SQLException{
        try {
            setConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        long t = System.currentTimeMillis();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (\n" +
                "    id INTEGER PRIMARY KEY,\n" +
                "    prodid INTEGER UNIQUE,\n" +
                "    title VARCHAR NOT NULL,\n" +
                "    cost REAL NOT NULL);");

        stmt.executeUpdate("DELETE FROM products");

        connection.setAutoCommit(false);
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO products (prodid, title, cost)\n" +
                "VALUES (?, ?, ?)");

        for (int i = 1; i <= 10000 ; i++) {
            pstmt.setInt(1, 200100+i);
            pstmt.setString(2, "Товар_"+i);
            pstmt.setInt(3, 100 + i*10);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        connection.setAutoCommit(true);
        System.out.println(System.currentTimeMillis() - t);
        disconnect();



//        ResultSet rs = stmt.executeQuery("SELECT * FROM products");
//        while(rs.next()){
//            System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " +
//                        rs.getString(3) + " " + rs.getInt(4));
//        }

    }


}
