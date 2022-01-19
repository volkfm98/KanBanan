package volkfm.KanBanan.models.column;

import volkfm.KanBanan.models.card.Card;
import volkfm.KanBanan.models.board.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "kanban_column")
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @javax.persistence.Column(nullable = false)
    private String name;

    @ManyToOne
    private Board board;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Card> cards = new ArrayList<>();

    public Column() {}

    public Column(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        Optional.ofNullable(card).orElseThrow().setColumn(this);
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        Optional.ofNullable(card).orElseThrow().setColumn(null);
        this.cards.remove(card);
    }
}
