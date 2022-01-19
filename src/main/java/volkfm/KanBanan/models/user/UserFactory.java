package volkfm.KanBanan.models.user;

import volkfm.KanBanan.models.authority.AuthorityRepository;

public class UserFactory {
    public UserFactory(AuthorityRepository authRepo) {
        User.setAuthRepo(authRepo);
    }

    public User createUser(String username, String email, String password, Boolean enabled) {
        return new User(username, email, password, enabled);
    }
}
