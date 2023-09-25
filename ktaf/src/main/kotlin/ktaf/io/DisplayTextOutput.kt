package ktaf.io

/**
 * Provides an interface for displaying text output.
 */
public interface DisplayTextOutput {
    /**
     * Display the specified [value] on the output.
     */
    public operator fun invoke(value: String)
}
