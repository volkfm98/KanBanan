package volkfm.KanBanan.db.Models;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<User, Boolean> permissions;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> components;

    @ManyToOne
    private Column column;

    @ManyToOne
    private User assignedBy;

    @ManyToMany
    private List<User> assignedTo;

    protected Card() {}

    public Card(String name) {
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

    public Map<String, String> getComponents() {
        return components;
    }

    void setComponents(Map<String, String> components) {
        this.components = components;
    }

    public Column getColumn() {
        return column;
    }

    void setColumn(Column column) {
        this.column = column;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public List<User> getAssignedTo() {
        return assignedTo;
    }

    void setAssignedTo(List<User> assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void addAssignedTo(User user) {
        this.assignedTo.add(user);
    }

    public void removeAssignedTo(User user) {
        this.assignedTo.remove(user);
    }
}
