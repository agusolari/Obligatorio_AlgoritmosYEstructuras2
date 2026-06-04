package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test12ViajeCostoMinimoDistancia {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(4);
        s.registrarCentroLogistico("C001", "Centro 1", "Montevideo", "Dir 1");
        s.registrarCentroLogistico("C002", "Centro 2", "Canelones", "Dir 2");
        s.registrarCentroLogistico("C003", "Centro 3", "Maldonado", "Dir 3");
        s.registrarCentroLogistico("C004", "Centro 4", "Rocha", "Dir 4");
        // Camino directo C001 -> C003 (200 km) y camino indirecto C001 -> C002 -> C003 (150 km)
        s.registrarConexion("C001", "C002", 100, 60);
        s.registrarConexion("C002", "C003", 50, 30);
        s.registrarConexion("C001", "C003", 200, 90);
        // C004 queda sin conexiones de entrada -> inalcanzable
    }

    @Test
    void viajeCostoMinimoDistanciaOK() {
        retorno = s.viajeCostoMinimoDistancia("C001", "C003");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // El camino más eficiente es el indirecto: 100 + 50 = 150 km
        assertEquals(Integer.valueOf(150), retorno.getValorInteger());
        assertEquals(
                "C001;Centro 1;Montevideo;Dir 1|" +
                        "C002;Centro 2;Canelones;Dir 2|" +
                        "C003;Centro 3;Maldonado;Dir 3",
                retorno.getValorString()
        );
    }

    @Test
    void viajeCostoMinimoDistanciaError1CamposVacios() {
        retorno = s.viajeCostoMinimoDistancia("", "C003");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoDistanciaError2OrigenNoExiste() {
        retorno = s.viajeCostoMinimoDistancia("C999", "C003");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoDistanciaError3DestinoNoExiste() {
        retorno = s.viajeCostoMinimoDistancia("C001", "C999");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void viajeCostoMinimoDistanciaError4NoHayCamino() {
        retorno = s.viajeCostoMinimoDistancia("C001", "C004");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }
}
