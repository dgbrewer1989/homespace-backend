package com.space.backend.space.apis;

import com.space.backend.space.apis.models.profile.Profile;
import com.space.backend.space.apis.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("profile")
public class UserProfileController {
    @Autowired
    private ProfileService service;

    @GetMapping
    public Profile getProfile(@RequestParam String username) throws Exception {
        return service.getProfile(username);
    }

    @CrossOrigin
    @GetMapping(value = "/all")
    public List<Profile> getAllProfiles() {
        return service.getAllProfiles();
    }
}
