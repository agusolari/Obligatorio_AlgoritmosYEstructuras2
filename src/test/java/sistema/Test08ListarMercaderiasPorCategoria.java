package sistema;

import interfaz.Categoria;
import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test08ListarMercaderiasPorCategoria {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(10);
    }

    @Test
    void listarMercaderiasPorCategoriaOK() {

        s.registrarMercaderia("COD03", "CC-003-CCC333", "Descripcion 3", false, Categoria.TEXTIL);

        s.registrarMercaderia("COD01", "AA-001-AAA111", "Descripcion 1", false, Categoria.OTROS);

        s.registrarMercaderia("COD02", "BB-002-BBB222", "Descripcion 2", true, Categoria.TEXTIL);

        retorno = s.listarMercaderiasPorCategoria(Categoria.TEXTIL);

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        assertEquals(
                "COD02;BB-002-BBB222;Descripcion 2;true;Textil|" +
                        "COD03;CC-003-CCC333;Descripcion 3;false;Textil",
                retorno.getValorString()
        );
    }

    @Test
    void listarMercaderiasPorCategoriaVacio() {

        retorno = s.listarMercaderiasPorCategoria(Categoria.ALIMENTOS);

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        assertEquals("", retorno.getValorString());
    }
}
