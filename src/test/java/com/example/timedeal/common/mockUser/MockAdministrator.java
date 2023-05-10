package com.example.timedeal.common.mockUser;

import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;

public class MockAdministrator {

    private MockAdministrator() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;

        private UserType userType;

        private String userName;

        private String password;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userType(String userType) {

            this.userType = UserType.ADMINISTRATOR;

            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new Administrator(
                    id,
                    userName,
                    password,
                    userType
            );
        }
    }
}
