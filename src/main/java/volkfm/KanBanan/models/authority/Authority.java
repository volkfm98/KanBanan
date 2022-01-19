package volkfm.KanBanan.models.authority;

import volkfm.KanBanan.models.user.User;

import javax.persistence.*;

@Entity
@IdClass(AuthorityPK.class)
@Table(name = "authorities")
public class Authority {
    @Id
    private String username;

    @Id
    private String authority;

    @ManyToOne
    @JoinColumn(name="username", insertable = false, updatable = false)
    private User user;

    public Authority() {}

    public Authority(String username, Role authority) {
        this.username = username;
        this.authority = authority.toString();
    }
}
