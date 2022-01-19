package volkfm.KanBanan.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import volkfm.KanBanan.models.authority.Authority;
import volkfm.KanBanan.models.authority.AuthorityRepository;
import volkfm.KanBanan.models.authority.Role;
import volkfm.KanBanan.models.user.User;
import volkfm.KanBanan.models.user.UserFactory;
import volkfm.KanBanan.models.user.UserRepository;
import volkfm.KanBanan.models.user.dto.UserRegInfo;

@RestController
@RequestMapping("/api/register")
public class RegisterUserController {
    private UserRepository userRepo;
    private AuthorityRepository authRepo;
    private UserFactory userFactory;
    private PasswordEncoder passwordEncoder;

    RegisterUserController(UserRepository userRepo, AuthorityRepository authRepo,
                           UserFactory userFactory, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;

        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("")
    public void createUser(@RequestBody UserRegInfo userInfo) {
        User newUser = userFactory.createUser(
                userInfo.username, userInfo.email,
                passwordEncoder.encode(userInfo.password), true);
        Authority newUserAuthority = new Authority(newUser.getUsername(), Role.USER);

        userRepo.save(newUser);
        authRepo.save(newUserAuthority);
    }
}
