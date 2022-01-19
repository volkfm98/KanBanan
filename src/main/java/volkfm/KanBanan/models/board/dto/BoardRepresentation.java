package volkfm.KanBanan.models.board.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import volkfm.KanBanan.models.board.Board;
import volkfm.KanBanan.models.column.Column;
import volkfm.KanBanan.models.column.ColumnRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardRepresentation {
    private Long id;

    private String name;
    private String privacyMode; // Should change type. Should think of its ues.

    private String owner; //TODO: Should change to URI instead of just username

    private Map<Long, String> columns = new HashMap<>();

    public BoardRepresentation(String name, String privacyMode) {
        this.name = name;
        this.privacyMode = privacyMode;
    }

    public BoardRepresentation(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.privacyMode = board.getPrivacyMode();
        this.owner = board.getOwner().getUsername();

        // I probably should've used HATEOAS here, but I was worried about strong coupling to another controller.
        UriComponentsBuilder rootUriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("/api");
        UriComponentsBuilder columnUriBuilder = rootUriBuilder.path("/columns/");

        this.columns = board.getColumns().stream().collect(Collectors.toMap(
                Column::getId,
                column -> columnUriBuilder.cloneBuilder().path(column.getId().toString()).build().toUriString())
        );
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<Long, String> getColumns() {
        return columns;
    }

    public void setColumns(Map<Long, String> columns) {
        this.columns = columns;
    }

    /* Dropped */
    /* public Board toBoard() {
        Board boardModel = new Board(
                this.name,
                this.privacyMode
        );

        // Some sort of heresy! Trying to resurrect dead objects.
        // Should get new ones from JPA repo or not do it here at all.
        for (Long key: this.columns.keySet()) {
            Column column = new Column();
            column.setId(key);

            boardModel.addColumn(column);
        }

        return boardModel;
    }*/
}
