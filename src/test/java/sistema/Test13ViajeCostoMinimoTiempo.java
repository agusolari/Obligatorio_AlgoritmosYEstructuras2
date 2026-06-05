package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test13ViajeCostoMinimoTiempo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(4);

        s.registrarCentroLogistico("C001", "Centro 1", "Montevideo", "Dir 1");
        s.registrarCentroLogistico("C002", "Centro 2", "Canelones", "Dir 2");
        s.registrarCentroLogistico("C003", "Centro 3", "Maldonado", "Dir 3");
        s.registrarCentroLogistico("C004", "Centro 4", "Rocha", "Dir 4");

        s.registrarConexion("C001", "C002", 100, 60);
        s.registrarConexion("C002", "C003", 50, 30);
        s.registrarConexion("C001", "C003", 200, 80);
    }

    @Test
    void viajeCostoMinimoTiempoOK() {

        retorno = s.viajeCostoMinimoTiempo("C001", "C003");

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        assertEquals(Integer.valueOf(80), retorno.getValorInteger());

        assertEquals("C001;Centro 1;Montevideo;Dir 1|" + "C003;Centro 3;Maldonado;Dir 3", retorno.getValorString()
        );
    }

    @Test
    void viajeCostoMinimoTiempoError1CamposVacios() {

        retorno = s.viajeCostoMinimoTiempo("", "C003");

        assertEquals(
                Retorno.Resultado.ERROR_1,
                retorno.getResultado()
        );
    }

    @Test
    void viajeCostoMinimoTiempoError2OrigenNoExiste() {

        retorno = s.viajeCostoMinimoTiempo("C999", "C003");

        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoTiempoError3DestinoNoExiste() {

        retorno = s.viajeCostoMinimoTiempo("C001", "C999");

        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoTiempoError4NoHayCamino() {

        retorno = s.viajeCostoMinimoTiempo("C001", "C004");

        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }
}