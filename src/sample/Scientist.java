package sample;

public class Scientist {
    private String Name = "default";
    private String Faculty = "default";
    private String Department = "default";
    private String Position = "default";
    private int Salary = 0;
    private int TimeInOffice = 0;

    public Scientist(String Name, String Faculty, String Department, String Position, int Salary, int TimeInOffice) {
        this.Name = Name;
        this.Faculty = Faculty;
        this.Department = Department;
        this.Position = Position;
        this.Salary = Salary;
        this.TimeInOffice = TimeInOffice;
    }

    public Scientist() {

    }

    public String getName() {
        return Name;
    }

    public String getFaculty() {
        return Faculty;
    }

    public String getDepartment() {
        return Department;
    }

    public String getPosition() {
        return Position;
    }

    public int getSalary() {
        return Salary;
    }

    public int getTimeInOffice() {
        return TimeInOffice;
    }


    public void setName(String name) {
        Name = name;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public void setTimeInOffice(int timeInOffice) {
        TimeInOffice = timeInOffice;
    }
}
