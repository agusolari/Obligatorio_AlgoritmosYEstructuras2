package sistema;

import dominio.*;
import interfaz.*;

import java.util.ArrayList;
import java.util.List;

public class ImplementacionSistema implements Sistema  {

    private ABB <String, Mercaderia> mercaderiasPorId;
    private ABB <String, Mercaderia> mercaderiasPorCodigo;
    private Lista <Mercaderia>[] mercaderiaPorCategoria;

    private Grafo centrosLogisticos;

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

        centrosLogisticos = new Grafo(maxCentros);


        return Retorno.ok();
    }


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

        if (centrosLogisticos.getCantVertices() >= centrosLogisticos.getCantMaxVertices()){
            return Retorno.error1("Ya hay la cantidad maximas de centros logisticos registrados.");
        }
        if (codigo == null || codigo.isBlank() || nombre == null || nombre.isBlank() || departamento == null || departamento.isBlank() || direccion == null || direccion.isBlank()) {
            return Retorno.error2("Campos vacios o nulos");
        }

        CentroLogistico cl = new CentroLogistico (codigo, nombre, departamento, direccion);
        if (centrosLogisticos.existeCentroLogistico(cl)){
            return Retorno.error3("Ya existe un centro logistico registrado con ese codigo");
        }
        centrosLogisticos.agregarCentroLogistico(cl);

        return Retorno.ok("Se agrego el centro logistico correctamente");
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
