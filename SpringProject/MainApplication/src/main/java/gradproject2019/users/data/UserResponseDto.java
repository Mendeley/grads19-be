package gradproject2019.users.data;

import gradproject2019.users.persistence.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class UserResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String occupation;

    private Long managerId;

    public UserResponseDto() {
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

    public String getOccupation() {
        return occupation;
    }

    public Long getManagerId() { return managerId; }

    public UserResponseDto from(User user) {
        return UserResponseDtoBuilder.anUserResponseDto()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withEmail(user.getEmail())
                .withOccupation(user.getOccupation())
                .withManagerId(user.getManagerId())
                .build();
    }

    public static final class UserResponseDtoBuilder {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String occupation;
        private Long managerId;

        private UserResponseDtoBuilder() {
        }

        public static UserResponseDtoBuilder anUserResponseDto() {
            return new UserResponseDtoBuilder();
        }

        public UserResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserResponseDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserResponseDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserResponseDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserResponseDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserResponseDtoBuilder withOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public UserResponseDtoBuilder withManagerId(Long managerId) {
            this.managerId = managerId;
            return this;
        }

        public UserResponseDto build() {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.id = this.id;
            userResponseDto.email = this.email;
            userResponseDto.username = this.username;
            userResponseDto.firstName = this.firstName;
            userResponseDto.lastName = this.lastName;
            userResponseDto.occupation = this.occupation;
            userResponseDto.managerId = this.managerId;
            return userResponseDto;
        }
    }
}