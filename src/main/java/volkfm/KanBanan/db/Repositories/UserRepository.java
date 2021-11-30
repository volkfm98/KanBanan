package volkfm.KanBanan.db.Repositories;

import org.springframework.data.repository.CrudRepository;
import volkfm.KanBanan.db.Models.User;

public interface UserRepository extends CrudRepository<User, Long> {
}