package Jdbcc.HospitalManagement;

import java.sql.*;
import java.util.Scanner;

public class Appointments {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String Username = "root";
    private static final String password = "Priti";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, Username, password);
            Patients patient = new Patients(con, sc);
            Doctors doctors = new Doctors(con);
            while (true) {
                System.out.println("1.AddPatient" + " 2.Viewpatients" + " 3.ViewDoctors" + " 4.AddAppointment " + "5.Exit");
                System.out.println("select Your choice");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        doctors.viewDoctors();
                        break;
                    case 4:bookAppoitment(con,sc,patient,doctors);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("please! Enter Correct choice");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }

    }

        public static void bookAppoitment(Connection con, Scanner scanner,Patients patients,Doctors Doctors){
            System.out.println("Enter PAtient id : ");
            int patientsId= scanner.nextInt();
            System.out.println("Enter Doctor id : ");
            int doctorsId=scanner.nextInt();
            System.out.println("ENter the appointment date(YYYY-MM-DD)");
            String appointmentDate=scanner.next();
            if(patients.getPatientsId(patientsId) && Doctors.getDoctorsId(doctorsId)){
                if(checkDoctorAvailable(doctorsId,appointmentDate,con)){
                   String query="insert into appointment(Patient_id,Doctors_id,date) values(?,?,?)";
                   try{
                       PreparedStatement pre=con.prepareStatement(query);
                       pre.setInt(1,patientsId);
                       pre.setInt(2,doctorsId);
                       pre.setString(3,appointmentDate);
                     int rowaffected=pre.executeUpdate();
                     if(rowaffected>0){
                         System.out.println("Appointment Booked");
                     }else{
                         System.out.println("Failed to book appointment");
                       }
                   }catch(SQLException e){
                       e.getMessage();
                   }
                }else{
                    System.out.println("doctor not available");
                }
            }else{
                System.out.println("Either doctor or patient  is not exsisting");
            }

        }

    public static boolean checkDoctorAvailable(int doctorId, String appointmentDate, Connection con) {
        String query = "SELECT COUNT(*) FROM appointment WHERE Doctors_id = ? AND date = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, doctorId);
            pre.setString(2, appointmentDate);

            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
               if(count == 0){
                   return true;
               }else{ // Doctor is available only if count is 0
                   return false;
               }
            } else {
                // No appointments found for the doctor on the given date
                return true; // Doctor is available
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }
}
