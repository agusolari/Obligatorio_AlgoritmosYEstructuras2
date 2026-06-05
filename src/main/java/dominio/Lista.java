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

    public void insertarOrdenado(T dato) {

        NodoLista<T> nuevo = new NodoLista<>(dato);

        // lista vacía
        if (inicio == null) {
            inicio = nuevo;
            largo++;
            return;
        }

        Comparable<T> comparableDato = (Comparable<T>) dato;

        // insertar al inicio
        if (comparableDato.compareTo(inicio.getDato()) < 0) {
            nuevo.setSig(inicio);
            inicio = nuevo;
            largo++;
            return;
        }

        NodoLista<T> aux = inicio;

        while (aux.getSig() != null && comparableDato.compareTo(aux.getSig().getDato()) > 0) {

            aux = aux.getSig();
        }
        nuevo.setSig(aux.getSig());
        aux.setSig(nuevo);

        largo++;
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
