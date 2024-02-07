package it.large.library.sales.exception;

/*
 * Classe personalizzata di eccezione.
 * RuntimeException è una sottoclasse di Exception in Java, appartenente alla gerarchia delle eccezioni controllate.
 * Tuttavia, a differenza delle eccezioni controllate, le eccezioni di tipo RuntimeException e le sue sottoclassi sono eccezioni non controllate.
 * Questo significa che non sono obbligatorie di essere dichiarate o catturate in un blocco try-catch.
 * Le eccezioni di runtime vengono automaticamente propagate fino alla cima dello stack delle chiamate se non vengono gestite.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException() {
    }
}
