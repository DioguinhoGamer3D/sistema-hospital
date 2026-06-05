package SistemaHospital.Uteis.Exceptions;

public class ErroDeServidor extends RuntimeException {
    public ErroDeServidor(String mensagem) {
        super(mensagem);
    }
}