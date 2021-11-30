package volkfm.KanBanan.db.Repositories;

import org.springframework.data.repository.CrudRepository;
import volkfm.KanBanan.db.Models.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
}