package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test09RegistrarCentroLogistico {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(4);
    }

    @Test
    void registrarCentroLogisticoOK() {

        retorno = s.registrarCentroLogistico("C001", "Centro Montevideo", "Montevideo", "18 de Julio 1234");

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void registrarCentroLogisticoError1MaximoCentros() {

        s.registrarCentroLogistico("C001", "Centro 1", "Montevideo", "Dir 1");
        s.registrarCentroLogistico("C002", "Centro 2", "Canelones", "Dir 2");
        s.registrarCentroLogistico("C003", "Centro 3", "Maldonado", "Dir 3");
        s.registrarCentroLogistico("C004", "Centro 4", "Rocha", "Dir 4");

        retorno = s.registrarCentroLogistico("C005", "Centro 5", "colonia", "Dir 5");

        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void registrarCentroLogisticoError2CodigoNull() {

        retorno = s.registrarCentroLogistico(null, "Centro Montevideo", "Montevideo", "18 de Julio 1234");

        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarCentroLogisticoError2NombreVacio() {

        retorno = s.registrarCentroLogistico("C001", "", "Montevideo", "18 de Julio 1234");

        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarCentroLogisticoError3Duplicado() {

        s.registrarCentroLogistico("C001", "Centro Montevideo", "Montevideo", "18 de Julio 1234");

        retorno = s.registrarCentroLogistico("C001", "Otro Centro", "Canelones", "Otra direccion");

        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}