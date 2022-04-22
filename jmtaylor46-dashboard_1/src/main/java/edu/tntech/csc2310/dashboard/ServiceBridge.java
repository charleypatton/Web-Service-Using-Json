package edu.tntech.csc2310.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ServiceBridge {


    private static final String apiKey = "44603498-EE17-4271-ADAC-2CC86FFDC2F3";
    private static final String urlString = "https://portapit.tntech.edu/express/api/unprotected/getCourseInfoByAPIKey.php?Subject=%s&Term=%s&Key=%s";

    private CourseInstance[] courses;

    public ServiceBridge(){}

    public ServiceBridge(String term, String subject){

        Gson gson = new Gson();

        try {
            String urlFmt = String.format(urlString, subject.toUpperCase(), term, apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr, CourseInstance[].class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public CourseInstance[] ServiceB(String term, String subject) {
        Gson gson = new Gson();

        try {
            String urlFmt = String.format(urlString, subject.toUpperCase(), term, apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr, CourseInstance[].class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public CourseInstance[] getCourses() {
        return courses;
    }

    @GetMapping("/allcourses")
    public SemesterSchedule allcourses(
            @RequestParam(value = "term", defaultValue = "na") String term){
        CourseInstance[] schedule = ServiceB(term, "");
        SubjectTerm subjectTerm = new SubjectTerm(term, "ALL");
        SemesterSchedule s = new SemesterSchedule(subjectTerm, schedule);
        return s;
    }

    @GetMapping("/coursesbysubject")
    public SemesterSchedule coursesbysubject(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "term", defaultValue =  "na") String term){
        CourseInstance[] schedule = ServiceB(term, subject.toUpperCase());
        SubjectTerm subjectTerm = new SubjectTerm(term, subject.toUpperCase());
        SemesterSchedule s = new SemesterSchedule(subjectTerm, schedule);
        return s;
    }

    @GetMapping("/coursesbyfaculty")
    public CourseInstance [] coursesbyfaculty(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "term", defaultValue =  "na") String term,
            @RequestParam(value = "lastname", defaultValue = "na") String lastname,
            @RequestParam(value = "firstname", defaultValue = "na") String firstname){

        String fi = firstname.substring(0, 1).toUpperCase() + firstname.substring(1, firstname.length()).toLowerCase();
        String la = lastname.substring(0, 1).toUpperCase() + lastname.substring(1, lastname.length()).toLowerCase();

        CourseInstance[] schedule = ServiceB(term, subject.toUpperCase());
        SubjectTerm subjectTerm = new SubjectTerm(term, subject.toUpperCase());
        int al = 0;

        for (int i = 0; i < schedule.length; i++){
            schedule[i].setSubjectTerm(subjectTerm);
            schedule[i].setFaculty();

            if(fi.equals(String.valueOf(schedule[i].getFaculty().getFirstname())) && la.equals(String.valueOf(schedule[i].getFaculty().getLastname()))){
                al++;
            }
        }

        CourseInstance[] array = new CourseInstance[al];

        int nal = 0;
        for (int i = 0; i < schedule.length; i++){
            if(fi.equals(String.valueOf(schedule[i].getFaculty().getFirstname())) && la.equals(String.valueOf(schedule[i].getFaculty().getLastname()))){
                array[nal] = schedule[i];
                nal++;
            }
        }

        return array;

    }

    @GetMapping("/coursebysection")
    public CourseInstance coursebysection(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "term", defaultValue =  "na") String term,
            @RequestParam(value = "section", defaultValue = "na") String section,
            @RequestParam(value = "course", defaultValue = "na") String course){;
        CourseInstance[] schedule = ServiceB(term, subject.toUpperCase());
        SubjectTerm subjectTerm = new SubjectTerm(subject.toUpperCase(), term);
        CourseInstance c = null;
        for (int i = 0; i < schedule.length; i++){
            schedule[i].setSubjectTerm(subjectTerm);
            schedule[i].setFaculty();

            if (section.equals(String.valueOf(schedule[i].getSECTION())) && course.equals(String.valueOf(schedule[i].getCOURSE()))){
                c = schedule[i];
            }
        }
        return c;
    }

    @GetMapping("/schbydepartment")
    public SubjectCreditHours schbydepartment(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "term", defaultValue =  "na") String term)
    {
        CourseInstance[] schedule = ServiceB(term, subject.toUpperCase());
        SubjectTerm subjectTerm = new SubjectTerm(term, subject.toUpperCase());
        int chg = 0;
        for (int i = 0; i < schedule.length; i++) {
            chg = chg + schedule[i].getCREDITS() * schedule[i].getSTUDENTCOUNT();
        }

        SubjectCreditHours subjectCreditHours = new SubjectCreditHours(chg, subjectTerm);
        return subjectCreditHours;
    }

    @GetMapping("/schbyfaculty")
    public FacultyCreditHours schbyfaculty(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "term", defaultValue =  "na") String term,
            @RequestParam(value = "lastname", defaultValue = "na") String lastname,
            @RequestParam(value = "firstname", defaultValue = "na") String firstname){

        String fi = firstname.substring(0, 1).toUpperCase() + firstname.substring(1, firstname.length()).toLowerCase();
        String la = lastname.substring(0, 1).toUpperCase() + lastname.substring(1, lastname.length()).toLowerCase();

        Faculty faculty = new Faculty(fi, la);
        SubjectTerm subjectTerm = new SubjectTerm(term, subject.toUpperCase());
        CourseInstance[] schedule = ServiceB(term, subject);

        int chg = 0;
        for (int i = 0; i < schedule.length; i++){

            schedule[i].setSubjectTerm(subjectTerm);

            schedule[i].setFaculty();

            if (fi.equals(String.valueOf(schedule[i].getFaculty().getFirstname())) && la.equals(String.valueOf(schedule[i].getFaculty().getLastname()))){
                chg = chg + schedule[i].getCREDITS() * schedule[i].getSTUDENTCOUNT();
            }
        }
        FacultyCreditHours facultyCreditHours = new FacultyCreditHours(faculty, chg, subjectTerm);
        return facultyCreditHours;
    }

}
