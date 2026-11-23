<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__ . '/../Students.php';

class StudentsTest extends TestCase
{
    private Students $students;

    protected function setUp(): void
    {
        $this->students = new Students();
        $this->students->reset();
    }

    public function testAjoutEtLecture(): void
    {
        $id = $this->students->addStudent('Amel', 15);
        $this->assertSame(['name' => 'Amel', 'grade' => 15.0], $this->students->getStudent($id));
    }

    public function testEtudiantInexistant(): void
    {
        $this->assertNull($this->students->getStudent(9999));
    }

    public function testMoyenne(): void
    {
        $this->students->addStudent('Amel', 10);
        $this->students->addStudent('Karim', 14);
        $this->assertSame(12.0, $this->students->averageGrade());
    }
}
