package powerservice.core.license;

public enum LicenseState {
    OK(0),
    EXPIRED(1),
    NOT_FOUND(2),
    INVAID_KEY(3),
    INVALID_IP(4),
    INVALID_USER_COUNT(5),
    INVALID_CENTER_COUNT(6),
    INVALID_SITE(7);

    private int stateCode;

    private LicenseState(int state) {
        this.stateCode = state;
    }

    public int value() {
        return this.stateCode;
    }
}
