package ro.codecamp.taskdashboard.connector.redmine;

import com.google.common.base.Function;
import com.taskadapter.redmineapi.bean.Project;

class RedmineProjectToProjectConverter implements Function<Project, ro.codecamp.taskdashboard.connector.dto.Project> {
    @Override
    public ro.codecamp.taskdashboard.connector.dto.Project apply(Project input) {
        return new ro.codecamp.taskdashboard.connector.dto.Project();
    }
}
