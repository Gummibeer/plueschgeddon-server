package com.plueschgeddon.server.Models;

public class Player {

    private int id;
    private String name;
    private String email;
    private int experience;
    private int level;
    private int maxhealth;
    private int curhealth;
    private int maxmana;
    private int curmana;
    private int coins;
    private int damagebuff;
    private int rangebuff;
    private int armorbuff;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
    public void increaseExperience(int experience) {
        this.experience += experience;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxhealth() {
        return maxhealth;
    }
    public void setMaxhealth(int maxhealth) {
        this.maxhealth = maxhealth;
    }

    public int getCurhealth() {
        return curhealth;
    }
    public void setCurhealth(int curhealth) {
        this.curhealth = curhealth;
    }

    public int getMaxmana() {
        return maxmana;
    }
    public void setMaxmana(int maxmana) {
        this.maxmana = maxmana;
    }

    public int getCurmana() {
        return curmana;
    }
    public void setCurmana(int curmana) {
        this.curmana = curmana;
    }

    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }
    public void increaseCoins(int coins) {
        this.coins += coins;
    }
    public void decreaseCoins(int coins) {
        this.coins -= coins;
    }

    public int getDamagebuff() {
        return damagebuff;
    }
    public void setDamagebuff(int damagebuff) {
        this.damagebuff = damagebuff;
    }
    public void increaseDamagebuff(int damagebuff) {
        this.damagebuff += damagebuff;
    }

    public int getRangebuff() {
        return rangebuff;
    }
    public void setRangebuff(int rangebuff) {
        this.rangebuff = rangebuff;
    }
    public void increaseRangebuff(int rangebuff) {
        this.rangebuff += rangebuff;
    }

    public int getArmorbuff() {
        return armorbuff;
    }
    public void setArmorbuff(int armorbuff) {
        this.armorbuff = armorbuff;
    }
    public void increaseArmorbuff(int armorbuff) {
        this.armorbuff += armorbuff;
    }
}
