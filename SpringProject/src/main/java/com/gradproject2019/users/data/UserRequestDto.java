package com.gradproject2019.users.data;

import com.gradproject2019.users.persistence.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import static com.gradproject2019.users.persistence.User.UserBuilder.anUser;

@Validated
public class UserRequestDto {
    @NotNull(message = "Invalid entry in username field.")
    private String username;

    @NotNull(message = "Invalid entry in first name field.")
    private String firstName;

    @NotNull(message = "Invalid entry in last name field.")
    private String lastName;

    @NotNull(message = "Invalid entry in email field.")
    private String email;

    @NotNull(message = "Invalid entry in password field.")
    private String password;

    @NotNull(message = "Invalid entry in occupation field.")
    private String occupation;

    private Long managerId;

    public UserRequestDto() {
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

    public String getOccupation() {
        return occupation;
    }

    public Long getManagerId() {
        return managerId;
    }

    public static User from(UserRequestDto userRequestDto) {
        return anUser()
                .withUsername(userRequestDto.getUsername())
                .withFirstName(userRequestDto.getFirstName())
                .withLastName(userRequestDto.getLastName())
                .withEmail(userRequestDto.getEmail())
                .withPassword(userRequestDto.getPassword())
                .withOccupation(userRequestDto.getOccupation())
                .withManagerId(userRequestDto.getManagerId())
                .build();
    }

    public static final class UserRequestDtoBuilder {
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String occupation;
        private Long managerId;

        private UserRequestDtoBuilder() {
        }

        public static UserRequestDtoBuilder anUserRequestDto() {
            return new UserRequestDtoBuilder();
        }

        public UserRequestDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserRequestDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserRequestDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserRequestDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserRequestDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserRequestDtoBuilder withOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public UserRequestDtoBuilder withManagerId(Long managerId) {
            this.managerId = managerId;
            return this;
        }

        public UserRequestDto build() {
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.password = this.password;
            userRequestDto.email = this.email;
            userRequestDto.username = this.username;
            userRequestDto.lastName = this.lastName;
            userRequestDto.occupation = this.occupation;
            userRequestDto.firstName = this.firstName;
            userRequestDto.managerId = this.managerId;
            return userRequestDto;
        }
    }
}
