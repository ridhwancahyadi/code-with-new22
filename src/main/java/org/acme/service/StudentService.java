package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.acme.domain.Student;
import org.acme.dto.StudentData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing Student entities
 */
@ApplicationScoped
public class StudentService {

    private final EntityManager entityManager;

    @Inject
    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Retrieves all students from the database
     *
     * @return List of StudentData objects
     */
    public List<StudentData> getStudents() {
        List<Student> students = entityManager.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();

        return students.stream()
                .map(this::mapToStudentData)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new student using a stored procedure
     *
     * @param studentData The student data to be added
     */
    @Transactional
    public void addStudentWithProcedure(StudentData studentData) {
        String sql = "CALL tambah_student(:id, :name, :balance)";

        entityManager.createNativeQuery(sql)
                .setParameter("id", studentData.id())
                .setParameter("name", studentData.name())
                .setParameter("balance", getBalanceOrDefault(studentData.balance()))
                .executeUpdate();
    }

    /**
     * Adds a new student using a database function
     *
     * @param studentData The student data to be added
     * @return ID of the newly added student, or -1 if operation fails
     */
    @Transactional
    public int addStudentWithFunction(StudentData studentData) {
        String sql = "SELECT fungsi_tambah_student(:id, :name, :balance)";

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("id", studentData.id())
                .setParameter("name", studentData.name())
                .setParameter("balance", getBalanceOrDefault(studentData.balance()))
                .getSingleResult();

        return Optional.ofNullable(result)
                .map(r -> ((Number) r).intValue())
                .orElse(-1);
    }

    /**
     * Deletes a student by ID
     *
     * @param id The ID of the student to delete
     * @return true if student was found and deleted, false otherwise
     */
    @Transactional
    public boolean deleteStudent(Long id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            entityManager.remove(student);
            return true;
        }
        return false;
    }

    /**
     * Updates student data by ID
     *
     * @param id          The ID of the student to update
     * @param studentData The updated student data
     * @return true if student was found and updated, false otherwise
     */
    @Transactional
    public boolean updateStudent(Long id, StudentData studentData) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            updateStudentFields(student, studentData);
            entityManager.merge(student);
            return true;
        }
        return false;
    }

    /**
     * Maps a Student entity to a StudentData DTO
     *
     * @param student The Student entity to map
     * @return The corresponding StudentData DTO
     */
    private StudentData mapToStudentData(Student student) {
        return new StudentData(
                student.getId(),
                student.getName(),
                getBalanceOrDefault(student.getBalance()));
    }

    /**
     * Updates the fields of a Student entity based on StudentData DTO
     *
     * @param student     The Student entity to update
     * @param studentData The StudentData containing new values
     */
    private void updateStudentFields(Student student, StudentData studentData) {
        if (studentData.name() != null && !studentData.name().isEmpty()) {
            student.setName(studentData.name());
        }

        if (studentData.balance() != null) {
            student.setBalance(studentData.balance());
        }
    }

    /**
     * Returns the provided balance or BigDecimal.ZERO if null
     *
     * @param balance The balance to check
     * @return The non-null balance value
     */
    private BigDecimal getBalanceOrDefault(BigDecimal balance) {
        return balance != null ? balance : BigDecimal.ZERO;
    }
}