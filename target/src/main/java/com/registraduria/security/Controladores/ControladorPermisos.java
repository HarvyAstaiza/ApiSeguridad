package com.registraduria.security.Controladores;

import com.registraduria.security.Modelos.Permiso;
import com.registraduria.security.Repositorios.RepositorioPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //permite hacer transacciones al servidor desde el mismo computador,
@RestController //esta clase servirá como puerta de entrada al servidor para llevar acabo todas las tareas del CRUD
/*Define la sub-ruta de acceso la cual se utilizará
para activar los métodos programados en la presente clase desde el servidor.*/
@RequestMapping("/permisos")
public class ControladorPermisos {
    @Autowired //permite la inyeccion de dependencias
    private RepositorioPermiso miRepositorioPermiso;

    @GetMapping("")//enpoint para obtener la lista de todos los permisos
    public List<Permiso> index() {
        return this.miRepositorioPermiso.findAll();
    }

    //CREAR PERMISO
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")//permite definir que allí está el método que se debe activar cuando se realice una petición a la ruta
    public Permiso create(@RequestBody Permiso infoPermisos) {
        return this.miRepositorioPermiso.save(infoPermisos);

    }

    @GetMapping("{id}")//devuelve un permisos por id especificado.
    public Permiso show(@PathVariable String id) {
        Permiso permisoActual = this.miRepositorioPermiso.findById(id).orElse(null);
        return permisoActual;
    }

    @PutMapping("{id}")//actualiza el permiso por id especificado
    public Permiso update(@PathVariable String id, @RequestBody Permiso infoPermiso) {
        Permiso permisoActual = this.miRepositorioPermiso.findById(id).orElse(null);
        if (permisoActual != null) {
            permisoActual.setMetodo(infoPermiso.getMetodo());
            permisoActual.setUrl(infoPermiso.getUrl());

            return this.miRepositorioPermiso.save(permisoActual);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")//elimina el permiso por id especificado
    public void delete(@PathVariable String id) {
        Permiso permisoActual = this.miRepositorioPermiso.findById(id).orElse(null);
        if (permisoActual != null) {
            this.miRepositorioPermiso.delete(permisoActual);
        }
    }

}
