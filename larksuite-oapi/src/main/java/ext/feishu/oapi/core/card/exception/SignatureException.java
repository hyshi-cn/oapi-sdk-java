package ext.feishu.oapi.core.card.exception;

public class SignatureException extends RuntimeException {
    public SignatureException() {
        super("card, signature error");
    }
}
