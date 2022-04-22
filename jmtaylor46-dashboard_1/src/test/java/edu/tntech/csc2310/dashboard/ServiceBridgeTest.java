package edu.tntech.csc2310.dashboard;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class ServiceBridgeTest {

    private ServiceBridge bridge;

    @Test
    public void coursesExist() {
        ServiceBridge bridge = new ServiceBridge("202210", "CSC");
        assertTrue( "courses exist",bridge.getCourses().length > 0);
        bridge = new ServiceBridge("202210", "csc");
        assertTrue( "courses exist",bridge.getCourses().length > 0);
        bridge = new ServiceBridge("202210", "cSc");
        assertTrue( "courses exist", bridge.getCourses().length > 0);
    }

    @Test
    public void coursesDontExist(){
        ServiceBridge bridge = new ServiceBridge("202210", "Jerry");
        CourseInstance[] ci = bridge.getCourses();
        assertEquals("course does not exist", 0, ci.length);
    }

    @Test
    public void courseFound(){
        ServiceBridge bridge = new ServiceBridge("202180", "UNIV");
        CourseInstance[] ci = bridge.getCourses();
        boolean found = false;
        for (CourseInstance co: ci){
            if (co.getCOURSE().contentEquals("1030"))
                found = true;
        }
        assertTrue("course found", found);
    }

    @Test
    public void termDoesNotExist(){
        ServiceBridge bridge = new ServiceBridge("202380", "UNIV");
        CourseInstance[] ci = bridge.getCourses();
        assertEquals("term does not exist",0, ci.length);
    }

    @Before
    public void setUp() throws Exception {
        bridge = new ServiceBridge("202210", "CSC");
    }

    @Test
    public void serviceB() {
        CourseInstance[] result = bridge.ServiceB("202210", "CSC");
        assertTrue("Course instance array created", result.length > 0);

        result = bridge.ServiceB("202210", "csC");
        assertTrue("Course instance array created", result.length > 0);

        result = bridge.ServiceB("202210", "csc");
        assertTrue("Course instance array created", result.length > 0);

        result = bridge.ServiceB("195508", "JAZZ");
        assertTrue("Unhappy path", result.length == 0);

    }

    @Test
    public void getCourses() {
        CourseInstance[] schedule = bridge.ServiceB("202180", "");
        SubjectTerm subjectTerm = new SubjectTerm("202180", "ALL");
        SemesterSchedule s = new SemesterSchedule(subjectTerm, schedule);
    }

    @Test
    public void allcourses() {
        CourseInstance[] schedule = bridge.ServiceB("202180", "");
        SubjectTerm subjectTerm = new SubjectTerm("202180", "ALL");
        SemesterSchedule s = new SemesterSchedule(subjectTerm, schedule);
        assertTrue("Allcourses is proper number of courses.", s.getSchedule().length > 3700 && s.getSchedule().length < 4000);

        schedule = bridge.ServiceB("201310", "");
        subjectTerm = new SubjectTerm("201310", "ALL");
        s = new SemesterSchedule(subjectTerm, schedule);
        assertTrue("Allcourses is proper number of courses.", s.getSchedule().length > 3250 && s.getSchedule().length < 3400);

        schedule = bridge.ServiceB("Megadeth", "Slayer");
        subjectTerm = new SubjectTerm("Megadeth", "Slayer");
        s = new SemesterSchedule(subjectTerm, schedule);
        assertTrue("Unhappy path.", s.getSchedule().length == 0);
    }

    @Test
    public void coursesbysubject() {
        CourseInstance[] schedule = bridge.ServiceB("202210", "csc");
        SubjectTerm subjectTerm = new SubjectTerm("202210", "csc");
        SemesterSchedule s = new SemesterSchedule(subjectTerm, schedule);
        assertTrue("Terms match.", s.getSubjectTerm().getSubject().equalsIgnoreCase("csc"));
        assertTrue("Courses by subject has adequate length.", s.getSchedule().length > 110 && s.getSchedule().length < 140);

        schedule = bridge.ServiceB("Metallica", "Judas_Priest");
        subjectTerm = new SubjectTerm("Metallica", "Judas_Priest");
        s = new SemesterSchedule(subjectTerm, schedule);
        assertTrue("Unhappy Path.", s.getSchedule().length == 0);

    }

    @Test
    public void coursesbyfaculty() {
        assertTrue(bridge.coursesbyfaculty("CSC", "202210", "Gannod", "Gerald").length == 8);
        assertTrue(bridge.coursesbyfaculty("CSc", "202080", "bruMMett", "Travis").length == 10);

        assertTrue("Unhappy Path", bridge.coursesbyfaculty("Black_Sabbath", "Travis_Tritt", "Manson", "Charles").length == 0);
    }

    @Test
    public void coursebysection() {
        assertTrue(bridge.coursebysection("CSC", "202210", "003", "3710").getFaculty().getLastname().equals("Elizandro"));
        assertTrue(bridge.coursebysection("MatH", "202210", "001", "1010").getFaculty().getLastname().equals("Bryant"));

        assertNull("Unhappy Path.", bridge.coursebysection("Bluegrass", "195110", "501", "Monroe"));
    }


    @Test
    public void schbydepartment() {
        assertTrue(bridge.schbydepartment("CSC", "201910").getCreditHoursGenerated() >= 4000 && bridge.schbydepartment("CSC", "201910").getCreditHoursGenerated() <= 5800);
        assertTrue(bridge.schbydepartment("CSC", "202210").getCreditHoursGenerated() >= 5400 && bridge.schbydepartment("CSC", "202210").getCreditHoursGenerated() <= 6000);

        assertTrue("Unhappy path.", bridge.schbydepartment("Pearl_Jam", "Soundgarden").getCreditHoursGenerated() == 0);
    }

    @Test
    public void schbyfaculty() {
        assertTrue(bridge.schbyfaculty("CSC", "202210", "Gannod", "Gerald").getCreditHoursGenerated() >= 330 && bridge.schbyfaculty("CSC", "202210", "Gannod", "Gerald").getCreditHoursGenerated() <= 360);
        assertTrue(bridge.schbyfaculty("CSC", "202180", "ElizandrO", "daviD").getCreditHoursGenerated() >= 165 && bridge.schbyfaculty("CSC", "202180", "Elizandro", "David").getCreditHoursGenerated() <= 200);

        assertTrue(bridge.schbyfaculty("Waylon", "Carter", "Vern", "George").getCreditHoursGenerated() == 0);
    }
}