package prj.projectjavawebapplication.service;

import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.model.entity.UserProfile;

public interface UserProfileService {
    UserProfile getByUserId(Long userId);
    UserProfile updateProfile(UserProfile profile, User user);
}