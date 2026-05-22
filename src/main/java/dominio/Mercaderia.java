package dominio;

import interfaz.Categoria;

public class Mercaderia {

    private String id;
    private String codigo;
    private String descripcion;
    private boolean fragil;
    private Categoria categoria;

    public Mercaderia(String id, String codigo, String descripcion,
                      boolean fragil, Categoria categoria) {

        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fragil = fragil;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isFragil() {
        return fragil;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return id + ";" +
                codigo + ";" +
                descripcion + ";" +
                fragil + ";" +
                categoria.getTexto();
    }
}
