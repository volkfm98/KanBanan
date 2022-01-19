package volkfm.KanBanan.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import volkfm.KanBanan.models.board.Board;
import volkfm.KanBanan.models.board.BoardRepository;
import volkfm.KanBanan.models.card.Card;
import volkfm.KanBanan.models.card.CardRepository;
import volkfm.KanBanan.models.column.Column;
import volkfm.KanBanan.models.column.ColumnRepository;
import volkfm.KanBanan.models.column.dto.ColumnRepresentation;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("/api/columns")
public class ColumnController {
    ColumnRepository columnRepo;
    BoardRepository boardRepo;
    CardRepository cardRepo;

    protected ColumnController(ColumnRepository columnRepo, BoardRepository boardRepo,
                               CardRepository cardRepo) {
        this.columnRepo = columnRepo;
        this.boardRepo = boardRepo;
        this.cardRepo = cardRepo;
    }

    @GetMapping("/{id}")
    ColumnRepresentation getColumn(Authentication auth, @PathVariable Long id) {
        return new ColumnRepresentation(columnRepo.findById(id).orElseThrow());
    }

    @PostMapping("")
    void createColumn(Authentication auth, @RequestBody ColumnRepresentation columnRepresentation) {
        Column column = new Column();
        column.setName(columnRepresentation.getName());

        Board board = boardRepo.findById(columnRepresentation.getBoard().getKey()).orElseThrow();
        board.addColumn(column);

        columnRepo.save(column);

        Iterable<Card> cards = cardRepo.findAllById(columnRepresentation.getCards().keySet());
        cards.forEach(card -> {
            column.addCard(card);
            cardRepo.save(card);
        });
    }

    @PutMapping("/{id}")
    void replaceColumn(Authentication auth, @PathVariable Long id,
            @RequestBody ColumnRepresentation columnRepresentation) {
        Column oldColumn = columnRepo.findById(id).orElseThrow(EntityExistsException::new);
        Column newColumn = new Column();

        newColumn.setId(id);
        newColumn.setName(columnRepresentation.getName());

        Board newBoard = boardRepo.findById(columnRepresentation.getBoard().getKey())
                .orElseThrow(EntityExistsException::new);

        newBoard.addColumn(newColumn);

        Iterable<Card> newCards = cardRepo.findAllById(columnRepresentation.getCards().keySet());

        for (Card card: oldColumn.getCards()) {
            oldColumn.removeCard(card);
            cardRepo.save(card);
        }

        newCards.forEach(card -> {
            newColumn.addCard(card);
            cardRepo.save(card);
        });

        columnRepo.save(newColumn);
    }

    @DeleteMapping("/{id}")
    void deleteColumn(Authentication auth, @PathVariable Long id) {
        Column column = columnRepo.findById(id).orElseThrow();
        column.getBoard().removeColumn(column);

        columnRepo.save(column);
    }
}
