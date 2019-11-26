package com.gradproject2019.users.data;

import com.gradproject2019.users.persistance.User;

import static com.gradproject2019.users.persistance.User.UserBuilder.anUser;

public class UserPatchRequestDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;

    public UserPatchRequestDto() {
    }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    public String getOccupation() { return occupation; }


    public static final class UserPatchRequestDtoBuilder {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String occupation;

        private UserPatchRequestDtoBuilder() {
        }

        public static UserPatchRequestDtoBuilder anUserPatchRequestDto() {
            return new UserPatchRequestDtoBuilder();
        }

        public UserPatchRequestDtoBuilder withId(Long id) {
            this.id = id;
            return this;
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

        public UserPatchRequestDto build() {
            UserPatchRequestDto userPatchRequestDto = new UserPatchRequestDto();
            userPatchRequestDto.id = this.id;
            userPatchRequestDto.firstName = this.firstName;
            userPatchRequestDto.username = this.username;
            userPatchRequestDto.occupation = this.occupation;
            userPatchRequestDto.email = this.email;
            userPatchRequestDto.lastName = this.lastName;
            return userPatchRequestDto;
        }
    }

    public User from(Long userId, UserPatchRequestDto userPatchRequestDto) {
        return anUser()
                .withId(userId)
                .withFirstName(userPatchRequestDto.getFirstName())
                .withLastName(userPatchRequestDto.getLastName())
                .withUsername(userPatchRequestDto.getUsername())
                .withOccupation(userPatchRequestDto.getOccupation())
                .withEmail(userPatchRequestDto.getEmail())
                .build();
    }
}