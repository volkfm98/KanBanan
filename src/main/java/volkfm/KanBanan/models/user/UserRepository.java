package volkfm.KanBanan.models.user;

import org.springframework.data.repository.CrudRepository;
import volkfm.KanBanan.models.user.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String name);
}