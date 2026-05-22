package dominio;

public class ABB<K extends Comparable<K>, T> {

    protected NodoABB<K, T> raiz;

    protected class NodoABB<K, T> {

        protected K clave;
        protected T dato;

        protected NodoABB<K, T> izq;
        protected NodoABB<K, T> der;

        public NodoABB(K clave, T dato) {
            this.clave = clave;
            this.dato = dato;
        }

        @Override
        public String toString() {
            return "[" + clave + " : " + dato + "]";
        }
    }

    public boolean insertar(K clave, T dato) {

        if (raiz == null) {
            raiz = new NodoABB<>(clave, dato);
            return true;
        }

        return insertar(raiz, clave, dato);
    }

    private boolean insertar(NodoABB<K, T> nodo, K clave, T dato) {

        int comparacion = clave.compareTo(nodo.clave);

        if (comparacion == 0) {
            return false;
        }

        if (comparacion < 0) {
            if (nodo.izq == null) {
                nodo.izq = new NodoABB<>(clave, dato);
                return true;
            }

            return insertar(nodo.izq, clave, dato);
        }

        if (nodo.der == null) {
            nodo.der = new NodoABB<>(clave, dato);
            return true;
        }

        return insertar(nodo.der, clave, dato);
    }

    public boolean existe(K clave) {
        return existe(raiz, clave);
    }

    private boolean existe(NodoABB<K, T> nodo, K clave) {

        if (nodo == null) {
            return false;
        }

        int comparacion = clave.compareTo(nodo.clave);

        if (comparacion == 0) {
            return true;
        }

        if (comparacion < 0) {
            return existe(nodo.izq, clave);
        }

        return existe(nodo.der, clave);
    }

    public RespuestaBusqueda<T> buscar(K clave) {
        return buscar(raiz, clave, 0);
    }

    private RespuestaBusqueda<T> buscar(NodoABB<K, T> nodo, K clave, int recorridos) {
        // no encontrado
        if (nodo == null) {
            return new RespuestaBusqueda<>(null, recorridos);
        }

        // contamos este nodo
        recorridos++;

        int comparacion = clave.compareTo(nodo.clave);

        // encontrado
        if (comparacion == 0) {
            return new RespuestaBusqueda<>(nodo.dato, recorridos);
        }

        // buscar izquierda
        if (comparacion < 0) {
            return buscar(nodo.izq, clave, recorridos);
        }

        // buscar derecha
        return buscar(nodo.der, clave, recorridos);
    }

}