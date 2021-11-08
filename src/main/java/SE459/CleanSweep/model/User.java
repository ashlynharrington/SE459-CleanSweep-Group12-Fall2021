package SE459.CleanSweep.model;

import org.springframework.context.annotation.Bean;

public class User {


    private Long cleanSweepId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Long getCleanSweepId() {
        return cleanSweepId;
    }

    public void setCleanSweepId(Long cleanSweepId) {
        this.cleanSweepId = cleanSweepId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(Long cleanSweepId, String firstName, String lastName, String email, String password) {
        this.cleanSweepId = cleanSweepId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

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
}
