package SE459.CleanSweep.webapp.model;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cleanSweepID;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCleanSweepID() {
        return cleanSweepID;
    }

    public void setCleanSweepID(String cleanSweepID) {
        this.cleanSweepID = cleanSweepID;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cleanSweepID='" + cleanSweepID + '\'' +
                '}';
    }
}