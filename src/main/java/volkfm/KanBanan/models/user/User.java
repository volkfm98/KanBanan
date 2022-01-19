package volkfm.KanBanan.models.user;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import volkfm.KanBanan.models.board.Board;
import volkfm.KanBanan.models.authority.Role;
import volkfm.KanBanan.models.authority.Authority;
import volkfm.KanBanan.models.authority.AuthorityRepository;

import javax.persistence.*;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;

    private String password;

    private Boolean enabled;

    @Transient
    /* Obsolete. Won't be removed for demonstration of work with IoC */
    /* Consider making it final */
    private static AuthorityRepository authRepo;

    @OneToMany(mappedBy = "owner")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Authority> authorities = new ArrayList<>();

    protected User() {}

    User(String username, String email, String password, Boolean enabled) {
        if (User.authRepo == null) {
            throw new UnsatisfiedDependencyException(
                    "", "volkfm.KanBanan.models.user.User",
                    "authRepo", "Authority repository not set");
        }

        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setEnabled(enabled);
    }

    static void setAuthRepo(AuthorityRepository authRepo) {
        User.authRepo = authRepo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Board> getBoards() {
        return boards;
    }

    /** Experimental. Might become obsolete */
    public void setBoards(List<Board> boards) {
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
