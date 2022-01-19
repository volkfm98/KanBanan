package volkfm.KanBanan.models.board;

import org.springframework.data.repository.CrudRepository;
import volkfm.KanBanan.models.board.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
}