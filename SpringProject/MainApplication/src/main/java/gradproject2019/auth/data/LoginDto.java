package gradproject2019.auth.data;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class LoginDto {
    @NotNull(message = "Invalid entry in login username field.")
    private String username;

    @NotNull(message = "Invalid entry in login password field.")
    private String password;

    public LoginDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static final class LoginDtoBuilder {
        private String username;
        private String password;

        private LoginDtoBuilder() {
        }

        public static LoginDtoBuilder aLoginDto() {
            return new LoginDtoBuilder();
        }

        public LoginDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public LoginDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginDto build() {
            LoginDto loginDto = new LoginDto();
            loginDto.password = this.password;
            loginDto.username = this.username;
            return loginDto;
        }
    }
}