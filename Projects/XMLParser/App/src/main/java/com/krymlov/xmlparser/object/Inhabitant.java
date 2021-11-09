package com.krymlov.xmlparser.object;

//entity in database
public class Inhabitant {
    //name
    private String fullname;
    //age
    private String age;
    //faculty
    private String faculty;
    //cathedra
    private String cathedra;
    //grade
    private String grade;
    //home adress
    private String homeplace;
    //payment
    private String payment;

    //constructor
    private Inhabitant(String fullname, String age, String faculty,
                       String cathedra, String grade, String homeplace, String payment) {
        this.fullname = fullname;
        this.age = age;
        this.faculty = faculty;
        this.cathedra = cathedra;
        this.grade = grade;
        this.homeplace = homeplace;
        this.payment = payment;
    }

    //default constructor
    private Inhabitant(){}

    //factory method no params to get instance
    public static Inhabitant getInstance(){
        return new Inhabitant();
    }

    //factory method with params to get instance
    public static Inhabitant getInstance(String fullname, String age,
                                         String faculty, String cathedra,
                                         String grade, String homeplace, String payment){
        return new Inhabitant(fullname, age, faculty, cathedra, grade, homeplace, payment);
    }

    @Override
    public String toString(){
        return fullname + ", " + age + ", " + faculty + ", "
                + cathedra + ", " + grade + ", " + homeplace + ", " + payment;
    }

    //getters and setters
    public String getFullname() {
        return fullname;
    }

    public String getAge() {
        return age;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getCathedra() {
        return cathedra;
    }

    public String getGrade() {
        return grade;
    }

    public String getHomeplace() {
        return homeplace;
    }

    public String getPayment() {
        return payment;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setCathedra(String cathedra) {
        this.cathedra = cathedra;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setHomeplace(String homeplace) {
        this.homeplace = homeplace;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
