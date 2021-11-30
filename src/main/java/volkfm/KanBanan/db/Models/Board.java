package volkfm.KanBanan.db.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<User, Boolean> permissions;
    private String privacyMode; // Should change type. Should think of its ues.

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<Column> columns = new ArrayList<Column>();

    protected Board() {}

    public Board(String name) {
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

    public String getPrivacyMode() {
        return privacyMode;
    }

    public void setPrivacyMode(String privacyMode) {
        this.privacyMode = privacyMode;
    }

    public User getOwner() {
        return owner;
    }

    void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Column> getColumns() {
        return columns;
    }

    void setColumns(List<Column> columns) {
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
