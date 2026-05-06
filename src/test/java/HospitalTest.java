import BancoDeDados.SistemaHospital.Exceptions.EntidadeNaoExiste;
import BancoDeDados.SistemaHospital.GerenciaHospital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {

    GerenciaHospital sistema;

    @BeforeEach
    void setUp(){
        this.sistema = new GerenciaHospital();
    }
    @Test
    void deveEstarVazio(){
        assertTrue(sistema.todosPacientes().isEmpty());
        assertThrows(EntidadeNaoExiste.class,() -> sistema.pesquisarPaciente(1));
    }
}
