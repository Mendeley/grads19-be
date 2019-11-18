package com.gradproject2019.auth.persistance;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    private Long userId;
    @Type(type="uuid-char")
    private UUID token;

    public Token() {}

    public Token(Long userId, UUID token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public UUID getToken() {
        return token;
    }

    public static final class TokenBuilder {
        private Long userId;
        private UUID token;

        private TokenBuilder() {
        }

        public static TokenBuilder aToken() {
            return new TokenBuilder();
        }

        public TokenBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public TokenBuilder withToken(UUID token) {
            this.token = token;
            return this;
        }

        public Token build() {
            return new Token(userId, token);
        }
    }
}