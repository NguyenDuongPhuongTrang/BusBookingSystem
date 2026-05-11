package prj.projectjavawebapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.projectjavawebapplication.model.entity.User;
import prj.projectjavawebapplication.model.entity.UserProfile;
import prj.projectjavawebapplication.repository.UserProfileRepository;
import prj.projectjavawebapplication.service.UserProfileService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile getByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElse(null);
    }

    @Override
    public UserProfile updateProfile(UserProfile profile, User user) {
        profile.setUser(user);
        profile.setUserId(user.getId());

        Optional<UserProfile> existing = userProfileRepository.findByUserId(user.getId());

        existing.ifPresent(value -> profile.setUserId(value.getUserId()));

        return userProfileRepository.save(profile);
    }
}