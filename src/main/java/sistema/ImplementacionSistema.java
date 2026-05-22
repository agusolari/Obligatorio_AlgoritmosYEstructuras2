package sistema;

import dominio.*;
import interfaz.*;

import java.util.ArrayList;
import java.util.List;

public class ImplementacionSistema implements Sistema  {

    private ABB <String, Mercaderia> mercaderiasPorId;
    private ABB <String, Mercaderia> mercaderiasPorCodigo;
    private Lista <Mercaderia>[] mercaderiaPorCategoria;

    private CentroLogistico[] centros;
    private int cantidadCentros;
    private int centrosMax;

    @Override
    public Retorno inicializarSistema(int maxCentros) {

        if (maxCentros <= 3) {
            return Retorno.error1("La cantidad de centros debe ser mayor a 3");
        }

        mercaderiasPorId = new ABB<>();
        mercaderiasPorCodigo = new ABB<>();

        mercaderiaPorCategoria = new Lista[Categoria.values().length];

        for (int i = 0; i < mercaderiaPorCategoria.length; i++) {
            mercaderiaPorCategoria[i] = new Lista<>();
        }

        centros = new CentroLogistico[maxCentros];

        this.centrosMax = maxCentros;
        this.cantidadCentros = 0;

        return Retorno.ok();
    }

    //        02 - Registrar Mercadería
//        Descripción: Registra una mercadería con sus datos, el id y el código son únicos.
//        Restricción de eficiencia: Esta operación deberá realizarse en orden O(log n) promedio, siendo n
//        la cantidad total de mercaderías.
//                Retornos posibles
//        OK Si La mercadería fue registrada exitosamente.
//                ERROR 1. Si alguno de los parámetros es vacío o null.
//        2. Si codigo no tiene el formato válido.
//        3. Si ya existe una mercadería registrada con ese id.
//        4. Si ya existe una mercadería registrada con ese codigo.
//                NO_IMPLEMENTADA Cuando aún no se implementó.
//    Restricción: (Investigación) Se requiere el uso de expresiones regulares para lograr validar el
//    formato del código. El formato para validar es: AA-BBB-CCCCCC, donde AA son solo letras, BBB
//    es un número (3 dígitos) y CCCCCC es alfanumérico (solo letras y números)
//    Las categorías posibles son:
//• Electrónica
//• Alimentos
//• Documentación
//• Textil
//• Otros

    @Override
    public Retorno registrarMercaderia(String id, String codigo, String descripcion, boolean fragil, Categoria categoria) {

        if(id == null || id.isBlank() || codigo == null || codigo.isBlank()  || categoria == null || descripcion == null || descripcion.isBlank()){
            return Retorno.error1("Los campos no pueden ser vacios o nulos");
        }
        if (!esCodigoValido(codigo)){
            return Retorno.error2("El formato del codigo no es valido");
        }
        if(mercaderiasPorId.existe(id)){
            return Retorno.error3("Esta mercaderia ya existe con ese id");
        }
        if(mercaderiasPorCodigo.existe(codigo)){
            return Retorno.error4("Esta mercaderia ya existe con ese codigo");
        }

        Mercaderia mercaderia = new Mercaderia(id, codigo, descripcion, fragil, categoria);
        mercaderiasPorId.insertar(id, mercaderia);
        mercaderiasPorCodigo.insertar(codigo, mercaderia);
        mercaderiaPorCategoria[categoria.getIndice()].insertarOrdenado(mercaderia);

        return Retorno.ok();
    }

    @Override
    public Retorno buscarMercaderiaPorId(String id) {

        if (id == null || id.isBlank()) {
            return Retorno.error1("El id es vacío o null");
        }

        RespuestaBusqueda<Mercaderia> resultado =
                mercaderiasPorId.buscar(id);

        if (resultado.getDato() == null) {
            return Retorno.error2("No existe mercadería con ese id");
        }

        return Retorno.ok(resultado.getCantidadRecorridos(), resultado.getDato().toString());
    }
//    Descripción: Retorna en valorString los datos de todas las mercaderías registradas, ordenadas por
//    id en forma creciente separados por un |.
//    Restricción de eficiencia: Esta operación deberá realizarse en orden O(n), siendo n la cantidad
//    total de mercaderías.
//    Formato de retorno del valor String:
//    id1;codigo1;descripcion1;frágil1;categoría1|id2;codigo2;descripci
//            on2;frágil2;categoría2
//    Nota: Se debe cumplir que id1 es lexicográficamente menor a id2.
//    Por ejemplo:
//            12345;MN-001-ABC123;Batería de cocina;false;Otros|23456;SJ-003-
//    CDE345;Ropa de invierno;false;Textil


    @Override
    public Retorno listarMercaderiasPorIdAscendente() {
        return Retorno.ok(mercaderiasPorId.listarAscendente());
    }

    @Override
    public Retorno listarMercaderiasPorIdDescendente() {
        return Retorno.ok(mercaderiasPorId.listarDescendente());
    }

    @Override
    public Retorno buscarMercaderiaPorCodigo(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return Retorno.error1("El codigo es vacío o null");
        }

        RespuestaBusqueda<Mercaderia> resultado = mercaderiasPorCodigo.buscar(codigo);

        if (resultado.getDato() == null) {
            return Retorno.error2("No existe mercadería con ese codigo");
        }
        return Retorno.ok(resultado.getCantidadRecorridos(), resultado.getDato().toString());
    }

    @Override
    public Retorno listarMercaderiasPorCodigoAscendente() {
        return Retorno.ok(mercaderiasPorCodigo.listarAscendente());
    }

    @Override
    public Retorno listarMercaderiasPorCategoria(Categoria unaCategoria) {

        return Retorno.ok(mercaderiaPorCategoria[unaCategoria.getIndice()].imprimirDatos());
    }

    @Override
    public Retorno registrarCentroLogistico(String codigo, String nombre, String departamento, String direccion) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarConexion(String codigoOrigen, String codigoDestino, int distancia, int tiempo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno redCentrosPorCantidadDeConexiones(String codigoOrigen, int cantidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoDistancia(String codigoOrigen, String codigoDestino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoTiempo(String codigoOrigen, String codigoDestino) {
        return Retorno.noImplementada();
    }



    // ----------------------------------------METODOS AUXILIARES-------------------------------------------


    private boolean esCodigoValido(String codigo) {
        return codigo.matches("^[A-Za-z]{2}-\\d{3}-[A-Za-z0-9]{6}$");
    }
}
