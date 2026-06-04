package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test11RedCentrosPorCantidadDeConexiones {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(4);
        s.registrarCentroLogistico("C001", "Centro 1", "Montevideo", "Dir 1");
        s.registrarCentroLogistico("C002", "Centro 2", "Canelones", "Dir 2");
        s.registrarCentroLogistico("C003", "Centro 3", "Maldonado", "Dir 3");
        s.registrarConexion("C001", "C002", 100, 60);
        s.registrarConexion("C002", "C003", 200, 120);
    }

    @Test
    void redCentrosPorCantidadDeConexionesOK() {
        retorno = s.redCentrosPorCantidadDeConexiones("C001", 2);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("C002;Centro 2;Canelones;Dir 2|" + "C003;Centro 3;Maldonado;Dir 3", retorno.getValorString());
    }

    @Test
    void redCentrosPorCantidadDeConexionesOKLimitaPorCantidad() {
        retorno = s.redCentrosPorCantidadDeConexiones("C001", 1);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("C002;Centro 2;Canelones;Dir 2", retorno.getValorString());
    }

    @Test
    void redCentrosPorCantidadDeConexionesError1CantidadNegativa() {
        retorno = s.redCentrosPorCantidadDeConexiones("C001", -1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void redCentrosPorCantidadDeConexionesError2CodigoVacio() {
        retorno = s.redCentrosPorCantidadDeConexiones("", 2);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void redCentrosPorCantidadDeConexionesError3OrigenNoExiste() {
        retorno = s.redCentrosPorCantidadDeConexiones("C004", 2);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
