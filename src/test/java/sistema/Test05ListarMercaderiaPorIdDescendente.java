package sistema;

import interfaz.Categoria;
import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test05ListarMercaderiaPorIdDescendente {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(10);
    }

    @Test
    void listarMercaderiasDescendenteOK() {

        s.registrarMercaderia("COD03", "AA-003-ABC123", "Descripcion 3", false, Categoria.TEXTIL);

        s.registrarMercaderia("COD01", "AA-001-AAA111", "Descripcion 1", false, Categoria.OTROS);

        s.registrarMercaderia("COD02", "AA-002-BBB222", "Descripcion 2", true, Categoria.ALIMENTOS);

        retorno = s.listarMercaderiasPorIdDescendente();

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        assertEquals(
                "COD03;AA-003-ABC123;Descripcion 3;false;Textil|" +
                        "COD02;AA-002-BBB222;Descripcion 2;true;Alimentos|" +
                        "COD01;AA-001-AAA111;Descripcion 1;false;Otros",
                retorno.getValorString()
        );
    }
}
