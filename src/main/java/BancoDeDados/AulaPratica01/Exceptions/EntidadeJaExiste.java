package BancoDeDados.AulaPratica01.Exceptions;

public class EntidadeJaExiste extends HospitalException {
    public EntidadeJaExiste(String entidade, Object id) {
        super(entidade + "Já existe" + id);
    }
}
