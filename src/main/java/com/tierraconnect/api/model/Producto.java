package com.tierraconnect.api.model;

public class Producto {
    private String id;
    private String nombre;
    private String tipo;
    private Double cantidadKg;
    private String ubicacion;
    private String productor;
    private String estado; // DISPONIBLE, VENDIDO, RESERVADO

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(String id, String nombre, String tipo, Double cantidadKg,
                    String ubicacion, String productor, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidadKg = cantidadKg;
        this.ubicacion = ubicacion;
        this.productor = productor;
        this.estado = estado;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCantidadKg() {
        return cantidadKg;
    }

    public void setCantidadKg(Double cantidadKg) {
        this.cantidadKg = cantidadKg;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}