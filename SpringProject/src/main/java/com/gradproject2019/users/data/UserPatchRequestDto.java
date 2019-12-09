package com.gradproject2019.users.data;

public class UserPatchRequestDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;
    private Long managerId;

    public UserPatchRequestDto() {
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

    public Long getManagerId() { return managerId;}


    public static final class UserPatchRequestDtoBuilder {
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String occupation;
        private Long managerId;

        private UserPatchRequestDtoBuilder() {
        }

        public static UserPatchRequestDtoBuilder anUserPatchRequestDto() {
            return new UserPatchRequestDtoBuilder();
        }

        public UserPatchRequestDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserPatchRequestDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserPatchRequestDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserPatchRequestDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserPatchRequestDtoBuilder withOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public UserPatchRequestDtoBuilder withManagerId(Long managerId) {
            this.managerId = managerId;
            return this;
        }

        public UserPatchRequestDto build() {
            UserPatchRequestDto userPatchRequestDto = new UserPatchRequestDto();
            userPatchRequestDto.firstName = this.firstName;
            userPatchRequestDto.username = this.username;
            userPatchRequestDto.occupation = this.occupation;
            userPatchRequestDto.email = this.email;
            userPatchRequestDto.lastName = this.lastName;
            userPatchRequestDto.managerId = this.managerId;
            return userPatchRequestDto;
        }
    }
}