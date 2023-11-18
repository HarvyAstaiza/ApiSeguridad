package com.registraduria.security.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data     // permite gestionar los datos
@Document // esta clase se va almacenar en la bd de mongo

public class Permiso {
    @Id
    private String _id;
    private String url;
    private String metodo;

    //constructor
    public Permiso(String url, String metodo) {
        this.url = url;
        this.metodo = metodo;
    }

    public String get_id() {
        return _id;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}

