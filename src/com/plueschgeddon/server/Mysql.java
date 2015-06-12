package com.plueschgeddon.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Mysql {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void connect() {
        try {
            Main.println("connect to database", Main.ANSI_BLUE);
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + Main.config[2] + ":" + Main.config[3] + "/" + Main.config[4] + "?" + "user=" + Main.config[5] + "&password=" + Main.config[6]);
            Main.println("connected to database: " + Main.config[2] + ":" + Main.config[3] + "/" + Main.config[4], Main.ANSI_GREEN);
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    public void select() {
        try {
            if(connect == null) {
                connect();
            }
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from `" + Main.config[4] + "`.`db`");

            while (resultSet.next()) {
                String host = resultSet.getString("Host");
                String db = resultSet.getString("Db");
                Main.println("Database: " + host + "/" + db, Main.ANSI_PURPLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
                connect = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} 