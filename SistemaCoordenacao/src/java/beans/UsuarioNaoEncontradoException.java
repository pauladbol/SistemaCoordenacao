package beans;

/**
 *
 * @author brunoscheltzke
 */
public class UsuarioNaoEncontradoException extends Exception {

    /**
     * Creates a new instance of <code>UsuarioNaoEncontradoException</code>
     * without detail message.
     */
    public UsuarioNaoEncontradoException() {
    }

    /**
     * Constructs an instance of <code>UsuarioNaoEncontradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UsuarioNaoEncontradoException(String msg) {
        super(msg);
    }
}
