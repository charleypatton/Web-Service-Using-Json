package edu.tntech.csc2310.dashboard;

public class CourseInstance {

    private String DEPARTMENT;
    private int STUDENTCOUNT;
    private int CREDITS;
    private String CRN;
    private String COURSE;
    private String SECTION;
    private String ISTIMEDETERMINED;
    private String STARTTIME;
    private String STARTAM_PM;
    private String ENDTIME;
    private String ENDAM_PM;
    private String CLASSDAYS;
    private String ISLOCDETERMINED;
    private String BUILDING;
    private String ROOM;
    private String ISONLINE;
    private String PROF;
    private String MAXIMUMSTUDENTS;
    private String ISOPEN;
    private String TITLE;
    private SubjectTerm subjectTerm;
    private Faculty faculty;

    public CourseInstance(SubjectTerm subjectTerm, Faculty faculty){
        this.subjectTerm = subjectTerm;
        this.faculty = faculty;

    }

    public void setSubjectTerm(SubjectTerm newSubjectTerm){
        this.subjectTerm = newSubjectTerm;
    }
    public void setFaculty(){
        if(String.valueOf(PROF).contains(", ")) {
            String[] s = String.valueOf(PROF).split(", ");
            String[] ln = s[1].split(" ");
            this.faculty = new Faculty(ln[0], s[0]);
        }else if(String.valueOf(PROF).equals(null)){
            String.valueOf(PROF).equals(null);
        }else
        {
            this.faculty = new Faculty("na", "na");
        }
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public SubjectTerm getSubjectTerm() {
        return subjectTerm;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public int getSTUDENTCOUNT() {
        return STUDENTCOUNT;
    }

    public int getCREDITS() {
        return CREDITS;
    }

    public String getCRN() {
        return CRN;
    }

    public String getCOURSE() {
        return COURSE;
    }

    public String getSECTION() {
        return SECTION;
    }

    public String getISTIMEDETERMINED() {
        return ISTIMEDETERMINED;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public String getSTARTAM_PM() {
        return STARTAM_PM;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public String getENDAM_PM() {
        return ENDAM_PM;
    }

    public String getCLASSDAYS() {
        return CLASSDAYS;
    }

    public String getISLOCDETERMINED() {
        return ISLOCDETERMINED;
    }

    public String getBUILDING() {
        return BUILDING;
    }

    public String getROOM() {
        return ROOM;
    }

    public String getISONLINE() {
        return ISONLINE;
    }

    public String getPROF() {
        return PROF;
    }

    public String getMAXIMUMSTUDENTS() {
        return MAXIMUMSTUDENTS;
    }

    public String getISOPEN() {
        return ISOPEN;
    }

    public String getTITLE() {
        return TITLE;
    }

    public String toString(){
        return DEPARTMENT + " " + COURSE + "-" + SECTION + " (" + PROF + ") " +
                CLASSDAYS + "\t\t" + STARTTIME + STARTAM_PM + " - " + ENDTIME + ENDAM_PM + "\t" + STUDENTCOUNT + "/" + MAXIMUMSTUDENTS;
    }
}
