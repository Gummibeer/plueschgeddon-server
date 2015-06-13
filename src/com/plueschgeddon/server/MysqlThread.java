package com.plueschgeddon.server;

import cat.quickdb.db.AdminBase;

public class MysqlThread extends Thread {

    public void run() {
        Main.println("Database-Thread is running", Main.ANSI_PURPLE);
        Main.println("connect to Database: " + Main.config[2] + ":" + Main.config[3], Main.ANSI_BLUE);
        try {
            Class.forName("cat.quickdb.db.AdminBase");
            AdminBase admin = new AdminBase(AdminBase.DATABASE.MYSQL, Main.config[2], Main.config[3], Main.config[4],Main.config[5], Main.config[6]);
            Main.println("connected to Database", Main.ANSI_GREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}