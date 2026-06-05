package SistemaHospital.Uteis.Exceptions;

public class RecursoNaoEncontrado extends RuntimeException {
    public RecursoNaoEncontrado(String recurso, Object id) {
        super(recurso + " não encontrado(a) com identificador: " + id);
    }
}