package volkfm.KanBanan.db.Repositories;

import org.springframework.data.repository.CrudRepository;
import volkfm.KanBanan.db.Models.Card;

public interface CardRepository extends CrudRepository<Card, Long> {
}