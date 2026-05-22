package dominio;


import interfaz.ILista;

import java.util.Iterator;

public class Lista<T> implements ILista<T> {

    protected NodoLista<T> inicio;
    protected int largo;

    public Lista() {
        this.inicio = null;
        this.largo = 0;
    }

    public void insertarAlFinal(T dato) {
        NodoLista<T> nuevo = new NodoLista<>(dato);

        if (inicio == null) {
            inicio = nuevo;
            largo++;
            return;
        }
        NodoLista<T> aux = inicio;

        while (aux.getSig() != null) {
            aux = aux.getSig();
        }
        aux.setSig(nuevo);

        largo++;
    }

    public void insertarOrdenado(T dato) {

        NodoLista<T> nuevo = new NodoLista<>(dato);

        // lista vacía
        if (inicio == null) {
            inicio = nuevo;
            largo++;
            return;
        }

        Comparable<T> comparableDato =
                (Comparable<T>) dato;

        // insertar al inicio
        if (comparableDato.compareTo(inicio.getDato()) < 0) {
            nuevo.setSig(inicio);
            inicio = nuevo;
            largo++;
            return;
        }

        NodoLista<T> aux = inicio;

        while (aux.getSig() != null &&
                comparableDato.compareTo(aux.getSig().getDato()) > 0) {

            aux = aux.getSig();
        }

        nuevo.setSig(aux.getSig());
        aux.setSig(nuevo);

        largo++;
    }

    @Override
    public void insertar(T dato) {
        inicio = new NodoLista<T>(dato, inicio);
        largo++;
    }

    @Override
    public void borrar(T dato) {

    }

    @Override
    public int largo() {
        return largo;
    }

    @Override
    public boolean existe(T dato) {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return true;
            }
            aux = aux.getSig();
        }
        return false;
    }

    @Override
    public T recuperar(T dato) {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getDato();
            }
            aux = aux.getSig();
        }
        return null;
    }

    @Override
    public boolean esVacia() {
        return largo == 0;
    }

    @Override
    public boolean esLlena() {
        return false;
    }

    @Override
    public String imprimirDatos() {
        return imprimirDatosV2(inicio);
    }

    private String imprimirDatosV2(NodoLista<T> nodo) {
        if (nodo == null) {
            return "";
        }
        if (nodo.getSig() == null) {
            return nodo.getDato().toString();
        }
        return nodo.getDato().toString() + "|" +
                imprimirDatosV2(nodo.getSig());
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NodoLista<T> aux = inicio;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T dato = aux.dato;
                aux = aux.sig;
                return dato;
            }

            @Override
            public void remove() {
            }

        };
    }


    class NodoLista<T>{
        private T dato;
        private NodoLista<T> sig;

        public NodoLista(T dato) {
            this.dato = dato;
            this.sig = null;
        }

        public NodoLista(T dato, NodoLista<T> sig) {
            this.dato = dato;
            this.sig = sig;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public NodoLista<T> getSig() {
            return sig;
        }

        public void setSig(NodoLista<T> sig) {
            this.sig = sig;
        }

        @Override
        public String toString() {
            return dato.toString();
        }
    }


}
