package com.siakad.service;

import com.siakad.exception.*;
import com.siakad.model.Course;
import com.siakad.model.Enrollment;
import com.siakad.model.Student;
import com.siakad.repository.CourseRepository;
import com.siakad.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * EnrollmentServiceMockTest
 *
 * Tujuan:
 * - Menguji perilaku interaksi antar komponen (behavior test).
 * - Menggunakan MOCK untuk memastikan metode-metode penting benar dipanggil.
 * - Fokus pada proses pendaftaran dan pembatalan (drop) mata kuliah.
 */
public class EnrollmentServiceMockTest {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private NotificationService notificationService;
    private GradeCalculator gradeCalculator;
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        // Membuat objek mock untuk semua dependensi eksternal
        studentRepo = mock(StudentRepository.class);
        courseRepo = mock(CourseRepository.class);
        notificationService = mock(NotificationService.class);
        gradeCalculator = mock(GradeCalculator.class);

        // Inisialisasi service utama
        enrollmentService = new EnrollmentService(studentRepo, courseRepo, notificationService, gradeCalculator);
    }

    // 1. Test pendaftaran mata kuliah berhasil
    @Test
    void testEnrollCourse_Success() {
        Student student = new Student("S001", "Ani", "ani@poltek.ac.id", "Informatika", 3, 3.5, "ACTIVE");
        Course course = new Course("CS101", "Pemrograman Java", 3, 30, 10, "Dosen A");

        when(studentRepo.findById("S001")).thenReturn(student);
        when(courseRepo.findByCourseCode("CS101")).thenReturn(course);
        when(courseRepo.isPrerequisiteMet("S001", "CS101")).thenReturn(true);

        Enrollment result = enrollmentService.enrollCourse("S001", "CS101");

        assertNotNull(result);
        assertEquals("S001", result.getStudentId());
        assertEquals("CS101", result.getCourseCode());

        // Verifikasi perilaku mock
        verify(notificationService, times(1)).sendEmail(eq("ani@poltek.ac.id"), anyString(), anyString());
        verify(courseRepo, times(1)).update(any(Course.class));
    }

    // 2. Test mahasiswa tidak ditemukan
    @Test
    void testEnrollCourse_StudentNotFound() {
        when(studentRepo.findById("S002")).thenReturn(null);
        assertThrows(StudentNotFoundException.class, () -> enrollmentService.enrollCourse("S002", "CS101"));
    }

    // 3. Test mahasiswa SUSPENDED
    @Test
    void testEnrollCourse_SuspendedStudent() {
        Student student = new Student("S003", "Budi", "budi@poltek.ac.id", "TI", 4, 2.1, "SUSPENDED");
        when(studentRepo.findById("S003")).thenReturn(student);
        assertThrows(EnrollmentException.class, () -> enrollmentService.enrollCourse("S003", "CS101"));
    }

    // 4. Test mata kuliah tidak ditemukan
    @Test
    void testEnrollCourse_CourseNotFound() {
        Student student = new Student("S004", "Cici", "cici@poltek.ac.id", "TI", 3, 3.0, "ACTIVE");
        when(studentRepo.findById("S004")).thenReturn(student);
        when(courseRepo.findByCourseCode("CS404")).thenReturn(null);
        assertThrows(CourseNotFoundException.class, () -> enrollmentService.enrollCourse("S004", "CS404"));
    }

    // 5. Test mata kuliah penuh
    @Test
    void testEnrollCourse_CourseFull() {
        Student student = new Student("S005", "Dedi", "dedi@poltek.ac.id", "TI", 3, 3.0, "ACTIVE");
        Course course = new Course("CS105", "Database", 3, 30, 30, "Dosen B");

        when(studentRepo.findById("S005")).thenReturn(student);
        when(courseRepo.findByCourseCode("CS105")).thenReturn(course);

        assertThrows(CourseFullException.class, () -> enrollmentService.enrollCourse("S005", "CS105"));
    }

    // 6. Test prasyarat belum terpenuhi
    @Test
    void testEnrollCourse_PrerequisiteNotMet() {
        Student student = new Student("S006", "Eka", "eka@poltek.ac.id", "TI", 3, 3.0, "ACTIVE");
        Course course = new Course("CS106", "Algoritma", 3, 30, 10, "Dosen C");

        when(studentRepo.findById("S006")).thenReturn(student);
        when(courseRepo.findByCourseCode("CS106")).thenReturn(course);
        when(courseRepo.isPrerequisiteMet("S006", "CS106")).thenReturn(false);

        assertThrows(PrerequisiteNotMetException.class, () -> enrollmentService.enrollCourse("S006", "CS106"));
    }

    // 7. Test drop course berhasil
    @Test
    void testDropCourse_Success() {
        Student student = new Student("S009", "Hana", "hana@poltek.ac.id", "TI", 3, 3.5, "ACTIVE");
        Course course = new Course("CS107", "PBO", 3, 25, 5, "Dosen D");

        when(studentRepo.findById("S009")).thenReturn(student);
        when(courseRepo.findByCourseCode("CS107")).thenReturn(course);

        enrollmentService.dropCourse("S009", "CS107");

        // Verifikasi email dan update course dipanggil
        verify(notificationService, times(1))
                .sendEmail(eq("hana@poltek.ac.id"), contains("Drop"), contains("PBO"));
        verify(courseRepo, times(1)).update(course);
    }
}
