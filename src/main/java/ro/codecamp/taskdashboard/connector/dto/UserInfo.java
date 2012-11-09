package ro.codecamp.taskdashboard.connector.dto;

public final class UserInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Integer userId;

    public Integer getUserId() {
        return userId;
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

    public String getUsername() {
        return username;
    }

    public static Builder builder() {
        return Builder.create();
    }

    public static class Builder {
        private UserInfo userInfo;

        private Builder() {
            userInfo = new UserInfo();
        }

        public Builder withFirstName(String firstName) {
            userInfo.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            userInfo.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            userInfo.email = email;
            return this;
        }

        public Builder withUsername(String username) {
            userInfo.username = username;
            return this;
        }

        public Builder withUserId(Integer userId) {
            userInfo.userId = userId;
            return this;
        }

        public static Builder create() {
            return new Builder();
        }

        public UserInfo build() {
            return userInfo;
        }
    }
}
