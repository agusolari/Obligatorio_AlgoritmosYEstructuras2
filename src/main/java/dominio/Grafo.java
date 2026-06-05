package dominio;

import cola.Cola;
import cola.ICola;

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

    public void agregarConexion(CentroLogistico clInicial, CentroLogistico clFinal, Conexion conexion) {
        int posVInicial = obtenerPos(clInicial);
        int posVFinal = obtenerPos(clFinal);

        conexiones[posVInicial][posVFinal] = conexion;
    }

    public boolean existeCentroLogistico(CentroLogistico cl) {
        int posABuscar = obtenerPos(cl);
        return posABuscar >= 0;
    }

    public boolean existeConexion(CentroLogistico clInicial, CentroLogistico clFinal) {
        int posVInicial = obtenerPos(clInicial);
        int posVFinal = obtenerPos(clFinal);

        return conexiones[posVInicial][posVFinal] != null;
    }

    public CentroLogistico obtenerCL(String codigo) {
        for (CentroLogistico cl : centroLogisticos) {
            if (cl != null && cl.getCodigo().equals(codigo)) {
                return cl;
            }
        }
        return null;
    }

    public boolean hayCamino(String codOrigen, String codDestino) {
        int posVOrigen = obtenerPos(obtenerCL(codOrigen));
        int posVDestino = obtenerPos(obtenerCL(codDestino));
        boolean[] visitados = new boolean[cantMaxVertices];
        return hayCamino(posVOrigen, posVDestino, visitados);
    }

    private boolean hayCamino(int posVOrigen, int posVDestino, boolean[] visitados) {
        if (posVOrigen == posVDestino) {
            return true;
        }
        visitados[posVOrigen] = true;
        for (int i = 0; i < conexiones.length; i++) {
            if (conexiones[posVOrigen][i] != null && !visitados[i]) {
                if (hayCamino(i, posVDestino, visitados)) {
                    return true;
                }
            }
        }
        return false;
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

    public String bfsConTupla(String codOrigen, int cantidad) {
        int posVSalida = obtenerPos(obtenerCL(codOrigen));
        boolean[] visitados = new boolean[cantMaxVertices];
        // ABB ordenado por código: nos da el orden creciente y el formato del valorString que necesitamos
        ABB<String, CentroLogistico> alcanzables = new ABB<>();

        ICola<Tupla<Integer, Integer>> cola = new Cola<>();
        visitados[posVSalida] = true;
        cola.encolar(new Tupla<>(posVSalida, 0));
        while (!cola.estaVacia()) {
            Tupla<Integer, Integer> tuplaV = cola.desencolar();
            int pos = tuplaV.getDato1();
            int dist = tuplaV.getDato2();

            // Se agrega como alcanzable sin incluir el origen (dist 0) y respetando el límite de conexiones
            if (dist > 0 && dist <= cantidad) {
                CentroLogistico cl = centroLogisticos[pos];
                alcanzables.insertar(cl.getCodigo(), cl);
            }

            // Solo seguimos expandiendo si aún no llegamos al límite de conexiones
            if (dist < cantidad) {
                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                        visitados[i] = true;
                        cola.encolar(new Tupla<>(i, dist + 1));
                    }
                }
            }
        }

        return alcanzables.listarAscendente();
    }

    public Tupla<Integer, String> dijkstra(String codOrigen, String codDestino, boolean esTiempo) {
        int posCLSalida = obtenerPos(obtenerCL(codOrigen));
        int posCLLlegada = obtenerPos(obtenerCL(codDestino));

        // Crear e inicializar estructuras
        boolean[] visitados = new boolean[cantMaxVertices];
        int[] costos = new int[cantMaxVertices];
        CentroLogistico[] vengo = new CentroLogistico[cantMaxVertices];
        for (int i = 0; i < cantMaxVertices; i++) {
            visitados[i] = false;
            costos[i] = Integer.MAX_VALUE;
            vengo[i] = null;
        }

        // Marcar el origen con costo cero
        costos[posCLSalida] = 0;

        for (int cl = 0; cl < cantVertices; cl++) {
            // 1) Obtengo el vertice no visitado de menor costo
            int posVertice = obtenerVerticeNoVisitadoDeMenorCosto(visitados, costos);

            if (posVertice != -1) {
                // 2) Marcarlo como visitado
                visitados[posVertice] = true;

                // 3) Para cada adyacente no visitado de posVertice
                for (int j = 0; j < cantMaxVertices; j++) {
                    if (conexiones[posVertice][j] != null && !visitados[j]) {
                        int peso = esTiempo ? conexiones[posVertice][j].tiempo : conexiones[posVertice][j].distancia;
                        if (costos[j] > costos[posVertice] + peso) {
                            costos[j] = costos[posVertice] + peso;
                            vengo[j] = centroLogisticos[posVertice];
                        }
                    }
                }
            }
        }

        int costoLlegada = costos[posCLLlegada];

        // Armando el camino
        int auxPos = posCLLlegada;
        String camino = centroLogisticos[auxPos].toString();
        while (vengo[auxPos] != null) {
            auxPos = obtenerPos(vengo[auxPos]);
            camino = centroLogisticos[auxPos] + "|" + camino;
        }

        return new Tupla<>(costoLlegada, camino);
    }

    private int obtenerVerticeNoVisitadoDeMenorCosto(boolean[] visitados, int[] costos) {
        int pos = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < cantMaxVertices; i++) {
            if (!visitados[i] && costos[i] < min) {
                min = costos[i];
                pos = i;
            }
        }
        return pos;
    }

}
