package com.siakad.service;

import com.siakad.exception.CourseNotFoundException;
import com.siakad.exception.StudentNotFoundException;
import com.siakad.model.Student;
import com.siakad.repository.CourseRepository;
import com.siakad.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * EnrollmentServiceStubTest
 *
 * Tujuan:
 * - Menguji logika sederhana yang bergantung pada nilai balik (data uji).
 * - Menggunakan STUB (melalui konfigurasi nilai pengembalian) tanpa memverifikasi perilaku.
 * - Fokus pada validasi batas kredit mahasiswa dan skenario error sederhana.
 */
public class EnrollmentServiceStubTest {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private NotificationService notificationService;
    private GradeCalculator gradeCalculator;
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        studentRepo = mock(StudentRepository.class);
        courseRepo = mock(CourseRepository.class);
        notificationService = mock(NotificationService.class);
        gradeCalculator = mock(GradeCalculator.class);
        enrollmentService = new EnrollmentService(studentRepo, courseRepo, notificationService, gradeCalculator);
    }

    // 1. Test validasi batas kredit (kasus valid)
    @Test
    void testValidateCreditLimit_Valid() {
        Student student = new Student("S007", "Fina", "fina@poltek.ac.id", "TI", 2, 3.2, "ACTIVE");

        when(studentRepo.findById("S007")).thenReturn(student);
        when(gradeCalculator.calculateMaxCredits(3.2)).thenReturn(24);

        boolean result = enrollmentService.validateCreditLimit("S007", 20);
        assertTrue(result);
    }

    // 2. Test validasi batas kredit (kasus tidak valid)
    @Test
    void testValidateCreditLimit_Invalid() {
        Student student = new Student("S008", "Gilang", "gilang@poltek.ac.id", "TI", 3, 2.0, "ACTIVE");

        when(studentRepo.findById("S008")).thenReturn(student);
        when(gradeCalculator.calculateMaxCredits(2.0)).thenReturn(15);

        boolean result = enrollmentService.validateCreditLimit("S008", 18);
        assertFalse(result);
    }

    // 3. Test student tidak ditemukan pada validasi kredit
    @Test
    void testValidateCreditLimit_StudentNotFound() {
        when(studentRepo.findById("S012")).thenReturn(null);
        assertThrows(StudentNotFoundException.class, () -> enrollmentService.validateCreditLimit("S012", 18));
    }

    // 4. Test drop course student tidak ditemukan
    @Test
    void testDropCourse_StudentNotFound() {
        when(studentRepo.findById("S010")).thenReturn(null);
        assertThrows(StudentNotFoundException.class, () -> enrollmentService.dropCourse("S010", "CS108"));
    }

    // 5. Test drop course mata kuliah tidak ditemukan
    @Test
    void testDropCourse_CourseNotFound() {
        Student student = new Student("S011", "Ira", "ira@poltek.ac.id", "TI", 3, 3.0, "ACTIVE");

        when(studentRepo.findById("S011")).thenReturn(student);
        when(courseRepo.findByCourseCode("CS109")).thenReturn(null);

        assertThrows(CourseNotFoundException.class, () -> enrollmentService.dropCourse("S011", "CS109"));
    }
}
