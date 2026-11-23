<?php

class Students
{
    private PDO $pdo;

    public function __construct()
    {
        $this->pdo = new PDO(
            'pgsql:host=localhost;port=5432;dbname=tp', 'postgres', 'postgres',
            [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION]
        );
    }

    public function reset(): void
    {
        $this->pdo->exec('TRUNCATE students RESTART IDENTITY');
    }

    public function addStudent(string $name, float $grade): int
    {
        $st = $this->pdo->prepare(
            'INSERT INTO students (name, grade) VALUES (?, ?) RETURNING id');
        $st->execute([$name, $grade]);
        return (int) $st->fetchColumn();
    }

    public function getStudent(int $id): ?array
    {
        $st = $this->pdo->prepare('SELECT name, grade FROM students WHERE id = ?');
        $st->execute([$id]);
        $row = $st->fetch(PDO::FETCH_ASSOC);
        if ($row === false) {
            return null;
        }
        return ['name' => $row['name'], 'grade' => (float) $row['grade']];
    }

    public function averageGrade(): float
    {
        return (float) $this->pdo->query('SELECT AVG(grade) FROM students')->fetchColumn();
    }
}
