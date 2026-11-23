import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class StudentsTest {
    private Connection conn;
    private Students students;

    @BeforeEach
    void setUp() throws SQLException {
        conn = Students.connect();
        try (Statement st = conn.createStatement()) {
            st.execute("TRUNCATE students RESTART IDENTITY");
        }
        students = new Students(conn);
    }

    @AfterEach
    void tearDown() throws SQLException { conn.close(); }

    @Test
    void ajoutEtLecture() throws SQLException {
        int id = students.addStudent("Amel", 15);
        assertEquals(new Students.Student("Amel", 15.0), students.getStudent(id).get());
    }

    @Test
    void etudiantInexistant() throws SQLException {
        assertEquals(Optional.empty(), students.getStudent(9999));
    }

    @Test
    void moyenne() throws SQLException {
        students.addStudent("Amel", 10);
        students.addStudent("Karim", 14);
        assertEquals(12.0, students.averageGrade());
    }
}
