package ro.codecamp.taskdashboard.connector.redmine;

import com.google.common.base.Function;
import com.taskadapter.redmineapi.bean.User;
import ro.codecamp.taskdashboard.connector.dto.UserInfo;

class UserToUserInfoConverter implements Function<User, UserInfo> {
    @Override
    public UserInfo apply(User input) {
        return UserInfo.builder()
                .withEmail(input.getMail())
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withUsername(input.getLogin())
                .withUserId(input.getId())
                .build();
    }
}
