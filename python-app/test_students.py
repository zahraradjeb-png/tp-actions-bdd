import pytest
from students import get_connection, add_student, get_student, average_grade


@pytest.fixture
def conn():
    connection = get_connection()
    with connection.cursor() as cur:
        cur.execute("TRUNCATE students RESTART IDENTITY")  # table vidée avant chaque test
    connection.commit()
    yield connection
    connection.close()


def test_ajout_et_lecture(conn):
    student_id = add_student(conn, "Amel", 15)
    assert get_student(conn, student_id) == ("Amel", 15.0)


def test_etudiant_inexistant(conn):
    assert get_student(conn, 9999) is None


def test_moyenne(conn):
    add_student(conn, "Amel", 10)
    add_student(conn, "Karim", 14)
    assert average_grade(conn) == 13.0