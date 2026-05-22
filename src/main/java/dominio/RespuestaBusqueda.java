package dominio;

public class RespuestaBusqueda<T> {

    private T dato;
    private int cantidadRecorridos;

    public RespuestaBusqueda(T dato, int cantidadRecorridos) {
        this.dato = dato;
        this.cantidadRecorridos = cantidadRecorridos;
    }

    public T getDato() {
        return dato;
    }

    public int getCantidadRecorridos() {
        return cantidadRecorridos;
    }


}
