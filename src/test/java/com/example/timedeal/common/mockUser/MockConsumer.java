package com.example.timedeal.common.mockUser;

import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;

public class MockConsumer {

    private MockConsumer() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;

        private UserType userType;

        private String userName;

        private String password;

        private String address;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userType(String userType) {

            this.userType = UserType.CONSUMER;

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

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new Consumer(
                    id,
                    userName,
                    password,
                    userType,
                    address
            );
        }
    }
}
