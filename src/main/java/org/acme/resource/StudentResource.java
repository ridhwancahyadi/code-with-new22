package org.acme.resource;

import org.acme.dto.APIResponse;
import org.acme.dto.APIResponseWithData;
import org.acme.dto.StudentData;
import org.acme.service.StudentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST resource for managing student data
 */
@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService;

    @Inject
    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Retrieves all students
     * 
     * @return Response containing list of students
     */
    @GET
    @Path("all")
    public Response getStudents() {
        List<StudentData> students = studentService.getStudents();
        APIResponseWithData<List<StudentData>> response = new APIResponseWithData<>(
                true,
                "Student list fetched successfully",
                students);
        return Response.ok(response).build();
    }

    @POST
    @Path("/procedure")
    public Response addStudentWithProcedure(StudentData student) {
        studentService.addStudentWithProcedure(student);
        APIResponse response = new APIResponse(true, "Student created with procedure");
        return Response.ok(response).build();
    }

    @POST
    @Path("/function")
    public Response addStudentWithFunction(StudentData student) {
        int newId = studentService.addStudentWithFunction(student);

        if (newId != -1) {
            StudentData newStudent = new StudentData(
                    // student.id(),
                    student.name(),
                    student.jurusan(),
                    student.ipk(),
                    student.tanggalLahir(),
                    getBalanceOrDefault(student.balance()));
            APIResponseWithData<StudentData> response = new APIResponseWithData<>(
                    true,
                    "Student created successfully",
                    newStudent);
            return Response.ok(response).build();
        } else {
            APIResponse response = new APIResponse(
                    false,
                    "Failed to create student");
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") Long id) {
        boolean deleted = studentService.deleteStudent(id);

        if (deleted) {
            APIResponse response = new APIResponse(true, "Student deleted successfully");
            return Response.ok(response).build();
        } else {
            APIResponse response = new APIResponse(false, "Student with ID " + id + " not found");
            return Response.status(Status.NOT_FOUND).entity(response).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") Long id, StudentData studentData) {
        boolean updated = studentService.updateStudent(id, studentData);

        if (updated) {
            APIResponseWithData<StudentData> response = new APIResponseWithData<>(
                    true,
                    "Student updated successfully",
                    studentData);
            return Response.ok(response).build();
        } else {
            APIResponse response = new APIResponse(
                    false,
                    "Student with ID " + id + " not found");
            return Response.status(Status.NOT_FOUND).entity(response).build();
        }
    }

    private BigDecimal getBalanceOrDefault(BigDecimal balance) {
        return balance != null ? balance : BigDecimal.ZERO;
    }
}