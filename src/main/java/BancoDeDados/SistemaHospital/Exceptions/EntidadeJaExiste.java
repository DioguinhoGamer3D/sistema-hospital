package BancoDeDados.SistemaHospital.Exceptions;

public class EntidadeJaExiste extends HospitalException {
    public EntidadeJaExiste(String entidade, Object id) {
        super(entidade + "Já existe" + id);
    }
}
