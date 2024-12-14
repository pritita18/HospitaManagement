package Jdbcc.HospitalManagement;

import java.sql.*;
import java.util.Scanner;

public class Patients {

    private Connection con;

    private Scanner sc;

    public Patients(Connection con,Scanner sc){
        this.con=con;
        this.sc=sc;
    }

    public void addPatient() {
        String query = "insert into patients (Name,Age,Gender) values(?,?,?)";
        System.out.println("Enter Name");
        String Name = sc.next();
        System.out.println("Enter Age");
        int Age = sc.nextInt();
        System.out.println("Enter Gender");
        String Gender = sc.next();
        try {
            PreparedStatement pre = con.prepareStatement(query);
            pre.setString(1, Name);
            pre.setInt(2, Age);
            pre.setString(3, Gender);
            pre.executeUpdate();
            System.out.println("Data inserted succsessfuly");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewPatient(){
        String query="select * from Patients";
        try{
            PreparedStatement pre=con.prepareStatement(query);
           ResultSet result= pre.executeQuery();
            System.out.println("+-------+---------------+--------------+----------------+");
            System.out.println("| id    | Name          | Age          | Gender         |");
            System.out.println("+-------+---------------+--------------+----------------+");
           while(result.next()){
               int id=result.getInt("id");
               String Name=result.getString("Name");
               int Age=result.getInt("Age");
               String Gender= result.getString("Gender");

               System.out.printf("| %-5s | %-13s | %-12s | %-14s |\n",id,Name,Age,Gender);
               System.out.println("+-------+---------------+--------------+----------------+");
           }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public  boolean getPatientsId(int id){
        String query="select * from Patients where id=?";
        try{
            PreparedStatement pre=con.prepareStatement(query);
            pre.setInt(1,id);
            ResultSet result=pre.executeQuery();
            if(result.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
