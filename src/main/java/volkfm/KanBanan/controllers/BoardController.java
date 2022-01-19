package volkfm.KanBanan.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import volkfm.KanBanan.models.board.Board;
import volkfm.KanBanan.models.board.dto.BoardRepresentation;
import volkfm.KanBanan.models.column.Column;
import volkfm.KanBanan.models.column.ColumnRepository;
import volkfm.KanBanan.models.user.User;
import volkfm.KanBanan.models.user.UserRepository;
import volkfm.KanBanan.models.board.BoardRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    UserRepository userRepo;
    BoardRepository boardRepo;
    ColumnRepository columnRepo;

    protected BoardController(UserRepository userRepo, BoardRepository boardRepo,
                              ColumnRepository columnRepo) {
        this.userRepo = userRepo;
        this.boardRepo = boardRepo;
        this.columnRepo = columnRepo;
    }

    // Username is currently has no use. Add permissions check later
    @GetMapping("")
    List<BoardRepresentation> getBoards(Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        return user.getBoards().stream().map(BoardRepresentation::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    BoardRepresentation getBoard(Authentication auth, @PathVariable(name = "id") Long id) {
        Board board = boardRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return new BoardRepresentation(board);
    }

    @PostMapping("/")
    void addBoard(Authentication auth, @RequestBody BoardRepresentation newBoardRepresentation) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        Board newBoard = new Board(newBoardRepresentation.getName(), newBoardRepresentation.getPrivacyMode());
        user.addBoard(newBoard);
        boardRepo.save(newBoard);
    }

    @DeleteMapping("/{id}")
    void deleteBoard(Authentication auth, @PathVariable(name = "id") Long id) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        boardRepo.deleteById(id);
    }

    @PutMapping("/{id}")
    void updateBoard(Authentication auth,
                     @PathVariable Long id,
                     @RequestBody BoardRepresentation newBoardRepresentation) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        Board oldBoard = boardRepo.findById(id).orElseThrow(EntityNotFoundException::new);

        Board newBoard = new Board(newBoardRepresentation.getName(), newBoardRepresentation.getPrivacyMode());
        newBoard.setId(id);

        User newOwner = userRepo.findByUsername(newBoardRepresentation.getOwner())
                .orElseThrow(EntityNotFoundException::new);
        newBoard.setOwner(newOwner);

        // Cashing new columns
        Iterable<Column> newColumns = columnRepo.findAllById(newBoardRepresentation.getColumns().keySet());

        // TODO: This feels not right. Do something with this
        // Unlinking old columns with board. Some of them will be deleted coz of orphan removal
        for (Column column: oldBoard.getColumns()) {
            oldBoard.removeColumn(column);
            columnRepo.save(column);
        }

        // Linking new columns with board. Some of previously deleted columns will be restored coz we cashed them
        newColumns.forEach(column -> {
            newBoard.addColumn(column);
            columnRepo.save(column);
        });

        boardRepo.save(newBoard);
    }
}