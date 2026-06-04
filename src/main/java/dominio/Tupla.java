package dominio;

public class Tupla<Q, T> {
    private final Q dato1;
    private final T dato2;

    public Tupla(Q dato1, T dato2) {
        this.dato1 = dato1;
        this.dato2 = dato2;
    }

    public Q getDato1() {
        return dato1;
    }

    public T getDato2() {
        return dato2;
    }

    @Override
    public String toString() {
        return "[" + dato1 +  ", " + dato2 + ']';
    }
}
