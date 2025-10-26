package com.siakad.service;

import com.siakad.model.CourseGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test untuk GradeCalculator
 * Meliputi:
 * - Boundary Value Analysis (uji nilai batas)
 * - Path Coverage (uji semua jalur kode)
 * - Exception Handling (uji kasus error)
 */
public class GradeCalculatorTest {

    private GradeCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new GradeCalculator();
    }

    // -----------------------------
    // TEST untuk calculateGPA()
    // -----------------------------

    @Test
    void calculateGpa_simpleCase() {
        // Normal case: dua mata kuliah
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("Matematika", 3, 4.0),
                new CourseGrade("Fisika", 2, 3.0)
        );

        double result = calculator.calculateGPA(grades);
        assertEquals(3.6, result, 0.01);

        // Menjamin perhitungan rumus IPK (∑(gradepoint×sks)/∑sks)
    }

    @Test
    void calculateGpa_emptyList_shouldReturnZero() {
        double result = calculator.calculateGPA(Collections.emptyList());
        assertEquals(0.0, result);

        // Boundary Value Analysis: input kosong
    }

    @Test
    void calculateGpa_allE_shouldBeZero() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("AI", 3, 0.0),
                new CourseGrade("ML", 2, 0.0)
        );

        double result = calculator.calculateGPA(grades);
        assertEquals(0.0, result);

        // Edge case: semua nilai E → totalPoints = 0
    }

    @Test
    void calculateGpa_invalidGradePoint_shouldThrow() {
        List<CourseGrade> grades = List.of(new CourseGrade("Hacker101", 3, 5.0));

        assertThrows(IllegalArgumentException.class, () -> calculator.calculateGPA(grades));

        // Exception Handling: grade point di luar range [0,4]
    }

    // -----------------------------
    // TEST untuk determineAcademicStatus()
    // -----------------------------

    @Test
    void determineStatus_semester1_2_boundary() {
        assertEquals("ACTIVE", calculator.determineAcademicStatus(2.0, 1));
        assertEquals("PROBATION", calculator.determineAcademicStatus(1.99, 2));

        // Boundary Value Analysis: batas 2.0 antara ACTIVE dan PROBATION
    }

    @Test
    void determineStatus_semester3_4_paths() {
        assertEquals("ACTIVE", calculator.determineAcademicStatus(2.25, 3));
        assertEquals("PROBATION", calculator.determineAcademicStatus(2.10, 4));
        assertEquals("SUSPENDED", calculator.determineAcademicStatus(1.9, 4));

        // Path Coverage: semua cabang di semester 3–4 diuji
    }

    @Test
    void determineStatus_semester5plus_paths() {
        assertEquals("ACTIVE", calculator.determineAcademicStatus(3.0, 6));
        assertEquals("PROBATION", calculator.determineAcademicStatus(2.2, 5));
        assertEquals("SUSPENDED", calculator.determineAcademicStatus(1.5, 8));

        // Path Coverage: tiga jalur (ACTIVE, PROBATION, SUSPENDED)
    }

    @Test
    void determineStatus_invalidInput_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> calculator.determineAcademicStatus(-1, 1));
        assertThrows(IllegalArgumentException.class, () -> calculator.determineAcademicStatus(3.5, 0));

        // Exception Handling: input gpa negatif dan semester nol
    }

    // -----------------------------
    // TEST untuk calculateMaxCredits()
    // -----------------------------

    @Test
    void maxAllowedSks_variousIpks() {
        assertEquals(24, calculator.calculateMaxCredits(3.5)); // >=3.0
        assertEquals(21, calculator.calculateMaxCredits(2.75)); // 2.5–2.99
        assertEquals(18, calculator.calculateMaxCredits(2.2)); // 2.0–2.49
        assertEquals(15, calculator.calculateMaxCredits(1.9)); // <2.0

        // Path Coverage: setiap rentang IPK diuji
    }

    @Test
    void calculateMaxCredits_boundaryValues() {
        assertEquals(24, calculator.calculateMaxCredits(3.0)); // batas 3.0
        assertEquals(21, calculator.calculateMaxCredits(2.5)); // batas 2.5
        assertEquals(18, calculator.calculateMaxCredits(2.0)); // batas 2.0

        // Boundary Value Analysis: uji nilai batas setiap kategori
    }

    @Test
    void calculateMaxCredits_invalidInput_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateMaxCredits(-0.1));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateMaxCredits(4.5));

        // Exception Handling: gpa di luar 0–4
    }

}
