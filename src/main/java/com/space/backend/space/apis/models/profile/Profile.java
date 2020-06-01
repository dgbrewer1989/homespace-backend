package com.space.backend.space.apis.models.profile;

import com.space.backend.space.apis.models.users.User;

import java.util.UUID;

public class Profile {
    public UUID profileId;
    public User user;

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
