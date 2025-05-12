import java.sql.*;
import java.util.Scanner;

public class AttendanceSystem {
        static final String DB_URL = "jdbc:mysql://localhost:3306/attendance_system";
        static final String USER = "root"; // your MySQL username
        static final String PASS = "password"; // your MySQL password

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                while (true) {
                    System.out.println("\n1. Add Student\n2. Mark Attendance\n3. View Attendance\n4. Exit");
                    int choice = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    switch (choice) {
                        case 1:
                            System.out.print("Enter student name: ");
                            String name = sc.nextLine();
                            PreparedStatement ps1 = conn.prepareStatement("INSERT INTO students(name) VALUES (?)");
                            ps1.setString(1, name);
                            ps1.executeUpdate();
                            System.out.println("Student added.");
                            break;
                        case 2:
                            System.out.print("Enter student ID: ");
                            int sid = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter date (YYYY-MM-DD): ");
                            String date = sc.nextLine();
                            System.out.print("Enter status (Present/Absent): ");
                            String status = sc.nextLine();
                            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO attendance(student_id, date, status) VALUES (?, ?, ?)");
                            ps2.setInt(1, sid);
                            ps2.setDate(2, Date.valueOf(date));
                            ps2.setString(3, status);
                            ps2.executeUpdate();
                            System.out.println("Attendance marked.");
                            break;
                        case 3:
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery(
                                    "SELECT a.id, s.name, a.date, a.status FROM attendance a JOIN students s ON a.student_id = s.id");
                            while (rs.next()) {
                                System.out.println("ID: " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Date: " + rs.getDate("date") +
                                        ", Status: " + rs.getString("status"));
                            }
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}


