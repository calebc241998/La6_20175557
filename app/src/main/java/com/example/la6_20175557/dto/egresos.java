package com.example.la6_20175557.dto;

public class egresos {

    private  String titulo_egreso;
    private String monto_egreso;
    private String descripcion_egreso;

    private  String fecha_egreso;

    public String getTitulo_egreso() {
        return titulo_egreso;
    }

    public void setTitulo_egreso(String titulo_egreso) {
        this.titulo_egreso = titulo_egreso;
    }

    public String getMonto_egreso() {
        return monto_egreso;
    }

    public void setMonto_egreso(String monto_egreso) {
        this.monto_egreso = monto_egreso;
    }

    public String getDescripcion_egreso() {
        return descripcion_egreso;
    }

    public void setDescripcion_egreso(String descripcion_egreso) {
        this.descripcion_egreso = descripcion_egreso;
    }

    public String getFecha_egreso() {
        return fecha_egreso;
    }

    public void setFecha_egreso(String fecha_egreso) {
        this.fecha_egreso = fecha_egreso;
    }
}
