package volkfm.KanBanan.models.column.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import volkfm.KanBanan.models.card.Card;
import volkfm.KanBanan.models.column.Column;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ColumnRepresentation {
    Long id;
    String name;
    Map.Entry<Long, String> board;

    Map<Long, String> cards = new HashMap<>();

    public ColumnRepresentation(Column column) {
        this.id = column.getId();
        this.name = column.getName();

        UriComponentsBuilder rootUriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("/api");

        this.board = new AbstractMap.SimpleEntry<>(
                column.getBoard().getId(),
                rootUriBuilder.cloneBuilder()
                .path("/boards/" + column.getBoard().getId()).build()
                .toUriString()
        );

        UriComponentsBuilder cardUriBuilder = rootUriBuilder.cloneBuilder()
                .path("/cards");

        this.cards = column.getCards().stream().collect(Collectors.toMap(
                Card::getId,
                card -> cardUriBuilder.path("/" + card.getId()).build().toUriString()
        ));
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

    public Map.Entry<Long, String> getBoard() {
        return board;
    }

    public void setBoard(Map.Entry<Long, String> board) {
        this.board = board;
    }

    public Map<Long, String> getCards() {
        return cards;
    }

    public void setCards(Map<Long, String> cards) {
        this.cards = cards;
    }
}
