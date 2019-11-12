package com.gradproject2019.auth.persistance;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    private Long userId;
    private String token;

    public Token() {}

    public Token(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public static final class TokenBuilder {
        private Long userId;
        private String token;

        private TokenBuilder() {
        }

        public static TokenBuilder aToken() {
            return new TokenBuilder();
        }

        public TokenBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public TokenBuilder withToken(String token) {
            this.token = token;
            return this;
        }

        public Token build() {
            return new Token(userId, token);
        }
    }
}