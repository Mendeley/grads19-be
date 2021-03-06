package gradproject2019.users.persistence;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String occupation;
    private Long managerId;

    public User() {
    }

    public User(Long id, String username, String firstName, String lastName, String email, String password, String occupation, Long managerId) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.occupation = occupation;
        this.managerId = managerId;
    }

    public User(String username, String firstName, String lastName, String email, String password, String occupation, Long managerId) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.occupation = occupation;
        this.managerId = managerId;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOccupation() {
        return occupation;
    }

    public Long getManagerId() {
        return managerId;
    }

    public static final class UserBuilder {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String occupation;
        private Long managerId;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public UserBuilder withManagerId(Long managerId) {
            this.managerId = managerId;
            return this;
        }

        public User build() {
            return new User(id, username, firstName, lastName, email, password, occupation, managerId);
        }
    }
}