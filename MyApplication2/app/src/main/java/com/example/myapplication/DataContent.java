package com.example.myapplication;

public class DataContent {
    private String email, username, password, name;
    private int points, userId, benefitId, price;

    private static DataContent instance = new DataContent();

    private DataContent(){}

    public static DataContent getInstance(){
        return instance;
    }

    public void setEmail(String email){this.email = email;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setName (String name){this.name = name;}
    public void setPoints(int points){this.points = points;}
    public void setUserId(int userId){this.userId = userId;}
    public void setBenefitId(int benefitId){this.benefitId = benefitId;}
    public void setPrice(int price){this.price = price;}

    public String getEmail(){return email;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getName(){return name;}
    public int getPoints(){return points;}
    public int getUserId(){return userId;}
    public int getBenefitId(){return benefitId;}
    public int getPrice(){return price;}
}
