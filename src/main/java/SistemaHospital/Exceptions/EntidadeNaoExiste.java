package SistemaHospital.Exceptions;

public class EntidadeNaoExiste extends HospitalException {
    public EntidadeNaoExiste(String entidade, Object id) {
        super("Não existe "+ entidade + " com código: " + id);
    }
}
