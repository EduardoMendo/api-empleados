package ni.edu.uam.api_empleados.exceptions;

public class EmpleadoDuplicadoException extends RuntimeException {
    public EmpleadoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}