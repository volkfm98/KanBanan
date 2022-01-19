package volkfm.KanBanan.models.authority;

public enum Role {
    ADMIN("ADMIN"),
    LOCAL_ADMIN("LOCAL_ADMIN"),
    USER("USER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
