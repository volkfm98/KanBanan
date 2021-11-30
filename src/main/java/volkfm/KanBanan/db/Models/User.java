package volkfm.KanBanan.db.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "kanban_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Board> boards = new ArrayList<Board>();

    protected User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Board> getBoards() {
        return boards;
    }

    void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void addBoard(Board board) {
        Optional.ofNullable(board).orElseThrow().setOwner(this);
        this.boards.add(board);
    }

    public void removeBoard(Board board) {
        Optional.ofNullable(board).orElseThrow().setOwner(null);
        this.boards.remove(board);
    }
}
