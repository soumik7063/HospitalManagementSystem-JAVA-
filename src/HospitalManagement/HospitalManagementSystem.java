package HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
     private static final String URL
            = "jdbc:mysql://localhost:3306/hospital_db";

    private static final String USER = "root";

    private static final String PASSWORD = "Nssr..12";
    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Patient patient = new Patient(con,scanner);
            Doctor doctor = new Doctor(con);
            while(true){ 
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointments");
                System.out.println("5. Exit");
                System.out.println("Enter your choice");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient,doctor,con,scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice");
                        break; 
                }
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient , Doctor doctor,Connection con , Scanner sc ){
        System.out.print("Enter patient ID: ");
        int patientID = sc.nextInt();
        System.out.print("Enter doctor ID: ");
        int doctorID = sc.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD)");
        String date = sc.next();

        if(patient.getPatientByID(patientID) && doctor.geDoctorByID(doctorID)){
            if(checkDoctorAvailability(doctorID,date,con)){
                String query = "INSERT INTO APPOINTMENTS(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                try {
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1,patientID);
                    ps.setInt(2,doctorID);
                    ps.setString(3,date);
                    int affectedRows = ps.executeUpdate();
                    if(affectedRows > 0){
                        System.out.println("Appointment Booked Successfully !!");
                    }else{
                        System.out.println("Appointment Not Booked");
                    }
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor is not available");
            }
        }else{
            System.out.println("Either patient or doctor not found");
        }
    }
    public static boolean checkDoctorAvailability(int doctorID,String date , Connection con){
        try {
            String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,doctorID);
            ps.setString(2,date);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                if(count > 0){
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
