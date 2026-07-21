package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection con;
    private Scanner scanner;

    public Patient(Connection con, Scanner scanner) {
        this.con = con;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();


        try {
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("\nPatient Added Successfully !!");
            } else {
                System.out.println("Patient Not Added");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        try {
            String query = "SELECT * FROM patients";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+------------------+-----------+-----------+");
            System.out.println("| patient ID | Name             | Age       | Gender    |");
            System.out.println("+------------+------------------+-----------+-----------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("| %-10s | %-16s | %-19s | %-19s |\n", id, name, age, gender);
                System.out.println("+------------+------------------+-----------+-----------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientByID(int id) {
        try {
            String query = "SELECT * FROM patients WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

    }

}