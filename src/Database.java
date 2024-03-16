import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
//        public static List<Question> list = new ArrayList<>();
        public static Connection connectDatabase() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizgame", "root", "");
                System.out.println("connect success....");
//                String query = "SELECT * FROM questions"; // Corrected syntax with "FROM"
//                PreparedStatement ps = conn.prepareStatement(query);
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("id");
//                        String content = rs.getString("content");
//                        Question question = new Question(id, content);
//                        list.add(question);
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace(); // Print stack trace for debugging
//                    System.err.println("Error executing query: " + e.getMessage());
//                }
            } catch (Exception e) {
                e.getMessage();
            }
            return conn;
        }

    public static void main(String[] args) {
        connectDatabase();
//        for(Question q: list){
//            System.out.println(q);
//        }
    }
}
