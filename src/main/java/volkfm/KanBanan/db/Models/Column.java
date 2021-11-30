package volkfm.KanBanan.db.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Table(name = "kanban_column")
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<User, Boolean> permissions;

    @ManyToOne
    private Board board;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Card> cards = new ArrayList<Card>();

    protected Column() {}

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

    public Map<User, Boolean> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<User, Boolean> permissions) {
        this.permissions = permissions;
    }

    public Board getBoard() {
        return board;
    }

    void setBoard(Board board) {
        this.board = board;
    }

    public List<Card> getCards() {
        return cards;
    }

    void setCards(List<Card> cards) {
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
