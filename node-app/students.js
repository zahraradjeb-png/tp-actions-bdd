const { Pool } = require('pg');

const pool = new Pool({
  host: 'localhost', port: 5432,
  database: 'tp', user: 'postgres', password: 'postgres',
});

async function addStudent(name, grade) {
  const r = await pool.query(
    'INSERT INTO students (name, grade) VALUES ($1, $2) RETURNING id', [name, grade]);
  return r.rows[0].id;
}

async function getStudent(id) {
  const r = await pool.query('SELECT name, grade FROM students WHERE id = $1', [id]);
  if (r.rows.length === 0) return null;
  return { name: r.rows[0].name, grade: parseFloat(r.rows[0].grade) };
}

async function averageGrade() {
  const r = await pool.query('SELECT AVG(grade) AS avg FROM students');
  return parseFloat(r.rows[0].avg);
}

module.exports = { pool, addStudent, getStudent, averageGrade };
