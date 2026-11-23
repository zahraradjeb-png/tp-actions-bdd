import java.sql.*;
import java.util.Optional;

public class Students {
    public record Student(String name, double grade) {}

    private final Connection conn;

    public Students(Connection conn) { this.conn = conn; }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/tp", "postgres", "postgres");
    }

    public int addStudent(String name, double grade) throws SQLException {
        String sql = "INSERT INTO students (name, grade) VALUES (?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, grade);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public Optional<Student> getStudent(int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT name, grade FROM students WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Student(rs.getString(1), rs.getDouble(2)));
            }
        }
    }

    public double averageGrade() throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT AVG(grade) FROM students")) {
            rs.next();
            return rs.getDouble(1);
        }
    }
}
