package ext.feishu.oapi.core.card.exception;

public class NotFoundHandlerException extends RuntimeException {
    public NotFoundHandlerException() {
        super("card, not found handler");
    }
}
