package com.space.backend.space.apis;

import com.space.backend.space.apis.models.authenticate.UserAuthenticate;
import com.space.backend.space.apis.models.users.CreateUserModel;
import com.space.backend.space.apis.models.users.User;
import com.space.backend.space.apis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public User getUser(@RequestParam UUID id) {
        return userService.getUser(id);
    }

    @PostMapping
    public void createUser(@RequestBody CreateUserModel user) throws Exception {
        userService.createUser(user);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody UserAuthenticate user) {
        Boolean result = userService.getUserAuthenticate(user);
        return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(result);
    }

    @PostMapping(value="random")
    public void createRandomUser() throws Exception {
        ArrayList<String> keywords = new ArrayList<String>() {
            {
                add("owo");
                add("xBoX");
                add("gAM3R");
                add("EM0");
                add("3M0");
                add("MCR");
                add("luv");
                add("l33t");
                add("420");
                add("stoner");
                add("weed");
                add("dope");
                add("rawr");
                add("x3");
                add("xD");
                add("grr");
                add("zim");
                add("owo");
                add("Inuyasha");
                add("Kagome");
                add("Light");
                add("Misa");
                add("Ryuk");
                add("PS3");
                add("onegai");
                add("sensei");
                add("fck");
                add("sh1t");
                add("666");
                add("69");
                add("123");
                add("dino");
                add("kitty");
                add("Atreyu");
            }
        };

        CreateUserModel user = new CreateUserModel();
        String username = getUsername(keywords);
        user.setUsername(username);
        user.setFirstName(username.substring(0, 5));
        user.setLastName(username.substring((username.length() / 2)));
        user.setPassword("Password1");
        user.setBirthday(new Date());

        userService.createUser(user);
    }

    private String getUsername(ArrayList<String> keywords) {
        String temp = "";
        boolean y = true;
        do {
            temp += (keywords.get(new Random().nextInt(keywords.size()) + 1));

            if (temp.length() == 12 || (temp.length() > 9 && temp.length() < 12)) {
                y = false;
            }

            if (temp.length() > 12) {
                temp = temp.substring(0, 11);
                y = false;
            }
        } while (y);

        return "xX" + temp + "Xx";
    }
}
