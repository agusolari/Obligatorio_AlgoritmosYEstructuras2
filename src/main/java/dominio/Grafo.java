package dominio;

public class Grafo {

    private final int cantMaxVertices;
    private int cantVertices;
    private final Conexion[][] conexiones;
    private final CentroLogistico[] centroLogisticos;


    public int getCantMaxVertices() {
        return cantMaxVertices;
    }

    public int getCantVertices() {
        return cantVertices;
    }

    public Grafo(int maxCentros) {
        this.cantMaxVertices = maxCentros;
        this.cantVertices = 0;
        this.conexiones = new Conexion[cantMaxVertices][cantMaxVertices];
        this.centroLogisticos = new CentroLogistico[cantMaxVertices];
    }

    public void agregarCentroLogistico(CentroLogistico cl) {
        if (cantVertices < cantMaxVertices) {
            int posLibre = obtenerPosLibre();
            centroLogisticos[posLibre] = cl;
            cantVertices++;
        }
    }

    public void borrarCentroLogistico(CentroLogistico cl) {
        int posABorrar = obtenerPos(cl);

        for (int i = 0; i < conexiones.length; i++) {
            conexiones[posABorrar][i] = null;
            conexiones[i][posABorrar] = null;
        }
    }

    public void agregarConexion(CentroLogistico clInicial, CentroLogistico clFinal, Conexion conexion) {
        int posVInicial = obtenerPos(clInicial);
        int posVFinal = obtenerPos(clFinal);

        conexiones[posVInicial][posVFinal] = conexion;
    }

    public void borrarConexion(CentroLogistico clInicial, CentroLogistico clFinal) {
        int posVInicial = obtenerPos(clInicial);
        int posVFinal = obtenerPos(clFinal);

        conexiones[posVInicial][posVFinal] = null;
    }

    public Conexion obtenerConexion(CentroLogistico clInicial, CentroLogistico clFinal) {
        int posVInicial = obtenerPos(clInicial);
        int posVFinal = obtenerPos(clFinal);

        return conexiones[posVInicial][posVFinal];
    }

    public boolean existeCentroLogistico(CentroLogistico cl) {
        int posABuscar = obtenerPos(cl);
        return posABuscar >= 0;
    }

    //Recorridas
    public void dfs(CentroLogistico cl) {
        int posV = obtenerPos(cl);
        boolean[] visitados = new boolean[cantMaxVertices];
        dfs(posV, visitados);
        System.out.println();
    }

    private void dfs(int posV, boolean[] visitados) {
        System.out.print(centroLogisticos[posV] + " ");
        visitados[posV] = true;
        for (int i = 0; i < conexiones.length; i++) {
            if (conexiones[posV][i]!= null && !visitados[i]) {
                dfs(i, visitados);
            }
        }
    }

    private int obtenerPosLibre() {
        for (int i = 0; i < centroLogisticos.length; i++) {
            if (centroLogisticos[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPos(CentroLogistico cl) {
        for (int i = 0; i < centroLogisticos.length; i++) {
            if (centroLogisticos[i] != null && centroLogisticos[i].getCodigo().equals(cl.getCodigo())) {
                return i;
            }
        }
        return -1;
    }


}
