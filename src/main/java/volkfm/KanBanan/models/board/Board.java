package volkfm.KanBanan.models.board;

import volkfm.KanBanan.models.column.Column;
import volkfm.KanBanan.models.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String privacyMode; // Should change type. Should think of its ues.

    @ManyToOne
    private User owner;

    // TODO: Check cascade type and add orphan removal.
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Column> columns = new ArrayList<>();

    protected Board() {}

    public Board(String name) {
        this.name = name;
    }

    public Board(String name, String privacyMode) {
        this.name = name;
        this.privacyMode = privacyMode;
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

    public String getPrivacyMode() {
        return privacyMode;
    }

    public void setPrivacyMode(String privacyMode) {
        this.privacyMode = privacyMode;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        Optional.ofNullable(column).orElseThrow().setBoard(this);
        this.columns.add(column);
    }

    public void removeColumn(Column column) {
        Optional.ofNullable(column).orElseThrow().setBoard(null);
        this.columns.remove(column);
    }
}
