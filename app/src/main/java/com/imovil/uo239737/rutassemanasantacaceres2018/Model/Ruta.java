package com.imovil.uo239737.rutassemanasantacaceres2018.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rodry on 16/03/2018.
 */

@SuppressWarnings("serial")
public class Ruta implements Serializable{
    private String Uri;
    private String Nombre;
    private Date Fecha_salida;
    private String Lugar_salida;
    private String Lugar_llegada;
    private String Dia;
    private ArrayList<String> Pasos_Asociados;
    private ArrayList<Punto> Trazado;

    public Ruta(){
        Uri = "";
        Nombre = "";
        Fecha_salida = new Date();
        Lugar_salida = "";
        Lugar_llegada = "";
        Dia = "";
        Pasos_Asociados = new ArrayList<>();
        Trazado = new ArrayList<>();
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Date getFecha_salida() {
        return Fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        Fecha_salida = fecha_salida;
    }

    public String getLugar_salida() {
        return Lugar_salida;
    }

    public void setLugar_salida(String lugar_salida) {
        Lugar_salida = lugar_salida;
    }

    public String getLugar_llegada() {
        return Lugar_llegada;
    }

    public void setLugar_llegada(String lugar_llegada) {
        Lugar_llegada = lugar_llegada;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }

    public ArrayList<String> getPasos_Asociados() {
        return Pasos_Asociados;
    }

    public void setPasos_Asociados(ArrayList<String> pasos_Asociados) {
        Pasos_Asociados = pasos_Asociados;
    }

    public ArrayList<Punto> getTrazado() {
        return Trazado;
    }

    public void setTrazado(ArrayList<Punto> trazado) {
        Trazado = trazado;
    }

    public void addTrazado(double lat, double lng){
        Punto p = new Punto(lat, lng);
        Trazado.add(p);
    }
}
