package Jdbcc.HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection con;

    public Doctors(Connection con){
        this.con=con;
    }

    public void viewDoctors(){
        String query="select * from Doctors";
        try{
            PreparedStatement pre=con.prepareStatement(query);
            ResultSet result= pre.executeQuery();
            System.out.println("+--------------+-----------------+----------------------+");
            System.out.println("| Doctor_id    | Name            | Department       |");
            System.out.println("+--------------+-----------------+----------------------+");
            while(result.next()){
                int id=result.getInt("id");
                String Name=result.getString("Name");
                String Department=result.getString("Department");

                System.out.printf("| %-12s | %-15s | %-20s |\n",id,Name,Department);
                System.out.println("+--------------+-----------------+----------------------+");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public  boolean getDoctorsId(int id){
        String query="select * from Doctors where id=?";
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
