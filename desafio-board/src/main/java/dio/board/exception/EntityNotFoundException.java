package dio.board.exception;

public class EntityNotFoundException  extends RuntimeException {
    public EntityNotFoundException(final String mgs) {
        super(mgs);

    }
}