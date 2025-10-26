package com.siakad.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit Test untuk paket com.siakad.exception
 *
 * Deskripsi:
 * Menguji semua class exception agar seluruh konstruktor
 * (dengan dan tanpa cause) berfungsi dengan benar.
 *
 * Tujuan:
 * - Memastikan semua kelas exception dapat mengembalikan pesan kesalahan (message)
 *   dengan benar.
 * - Memastikan setiap exception mampu menyimpan penyebab (cause) dari error lain.
 * - Meningkatkan coverage hingga 100% untuk semua kelas exception.
 */
public class ExceptionTest {

    // 1. Test EnrollmentException konstruktor dengan message
    @Test
    public void testEnrollmentExceptionMessage() {
        EnrollmentException ex = new EnrollmentException("Enrollment error!");
        assertEquals("Enrollment error!", ex.getMessage());
        assertNull(ex.getCause()); // karena tidak ada cause
    }

    // 2. Test EnrollmentException konstruktor dengan message dan cause
    @Test
    public void testEnrollmentExceptionWithCause() {
        Throwable cause = new RuntimeException("Internal error");
        EnrollmentException ex = new EnrollmentException("Enrollment gagal!", cause);
        assertEquals("Enrollment gagal!", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // 3. Test StudentNotFoundException konstruktor dengan message
    @Test
    public void testStudentNotFoundExceptionMessage() {
        StudentNotFoundException ex = new StudentNotFoundException("Mahasiswa tidak ditemukan!");
        assertEquals("Mahasiswa tidak ditemukan!", ex.getMessage());
        assertNull(ex.getCause());
    }

    // 4. Test StudentNotFoundException konstruktor dengan message dan cause
    @Test
    public void testStudentNotFoundExceptionWithCause() {
        Throwable cause = new RuntimeException("Kesalahan database");
        StudentNotFoundException ex = new StudentNotFoundException("Data mahasiswa tidak ditemukan!", cause);
        assertEquals("Data mahasiswa tidak ditemukan!", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // 5. Test CourseNotFoundException konstruktor dengan message
    @Test
    public void testCourseNotFoundExceptionMessage() {
        CourseNotFoundException ex = new CourseNotFoundException("Mata kuliah tidak ditemukan!");
        assertEquals("Mata kuliah tidak ditemukan!", ex.getMessage());
        assertNull(ex.getCause());
    }

    // 6. Test CourseNotFoundException konstruktor dengan message dan cause
    @Test
    public void testCourseNotFoundExceptionWithCause() {
        Throwable cause = new RuntimeException("Kesalahan query database");
        CourseNotFoundException ex = new CourseNotFoundException("Kode mata kuliah salah!", cause);
        assertEquals("Kode mata kuliah salah!", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // 7. Test PrerequisiteNotMetException konstruktor dengan message
    @Test
    public void testPrerequisiteNotMetExceptionMessage() {
        PrerequisiteNotMetException ex = new PrerequisiteNotMetException("Prasyarat tidak terpenuhi!");
        assertEquals("Prasyarat tidak terpenuhi!", ex.getMessage());
        assertNull(ex.getCause());
    }

    // 8. Test PrerequisiteNotMetException konstruktor dengan message dan cause
    @Test
    public void testPrerequisiteNotMetExceptionWithCause() {
        Throwable cause = new RuntimeException("Data prasyarat hilang");
        PrerequisiteNotMetException ex = new PrerequisiteNotMetException("Tidak memenuhi prasyarat!", cause);
        assertEquals("Tidak memenuhi prasyarat!", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // 9. Test CourseFullException konstruktor dengan message
    @Test
    public void testCourseFullExceptionMessage() {
        CourseFullException ex = new CourseFullException("Kelas sudah penuh!");
        assertEquals("Kelas sudah penuh!", ex.getMessage());
        assertNull(ex.getCause());
    }

    // 10. Test CourseFullException konstruktor dengan message dan cause
    @Test
    public void testCourseFullExceptionWithCause() {
        Throwable cause = new RuntimeException("Maksimum kapasitas tercapai");
        CourseFullException ex = new CourseFullException("Tidak bisa menambah peserta!", cause);
        assertEquals("Tidak bisa menambah peserta!", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
