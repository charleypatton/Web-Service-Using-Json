package edu.tntech.csc2310.dashboard;

public class Faculty implements Comparable<Faculty> {

    private String firstname;
    private String lastname;

    public Faculty(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }



    @Override
    public int compareTo(Faculty f){

        Faculty y = f;

        int result = 0;

        if (this.lastname.compareTo(y.getLastname()) < 0)
            result = -1;
        else if (this.lastname.compareTo(y.getLastname()) > 0)
            result = 1;
        else if (this.firstname.compareTo(y.getFirstname()) < 0)
            result = -1;
        else if (this.firstname.compareTo(y.getFirstname()) > 0)
            result = 1;

        return result;
    }
}

