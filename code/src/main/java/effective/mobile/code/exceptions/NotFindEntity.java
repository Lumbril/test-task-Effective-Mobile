package effective.mobile.code.exceptions;

public class NotFindEntity extends Exception {
    public NotFindEntity() {
        super("Not found entity");
    }

    public NotFindEntity(String message) {
        super("Not found entity: in table " + message);
    }
}
