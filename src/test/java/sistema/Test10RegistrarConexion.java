package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test10RegistrarConexion {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(4);
        s.registrarCentroLogistico("C001", "Centro 1", "Montevideo", "Dir 1");
        s.registrarCentroLogistico("C002", "Centro 2", "Canelones", "Dir 2");
    }

    @Test
    void registrarConexionOK() {
        retorno = s.registrarConexion("C001", "C002", 100, 60);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void registrarConexionError1CamposVacios() {
        retorno = s.registrarConexion("", "C002", 100, 60);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void registrarConexionError2OrigenNoExiste() {
        retorno = s.registrarConexion("C003", "C002", 100, 60);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarConexionError3DestinoNoExiste() {
        retorno = s.registrarConexion("C001", "C003", 100, 60);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void registrarConexionError4DistanciaInvalida() {
        retorno = s.registrarConexion("C001", "C002", 0, 60);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    @Test
    void registrarConexionError5TiempoInvalido() {
        retorno = s.registrarConexion("C001", "C002", 100, 0);
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());
    }

    @Test
    void registrarConexionError6YaExiste() {
        s.registrarConexion("C001", "C002", 100, 60);
        retorno = s.registrarConexion("C001", "C002", 200, 120);
        assertEquals(Retorno.Resultado.ERROR_6, retorno.getResultado());
    }
}
