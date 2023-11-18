package com.registraduria.security.Controladores;
import com.registraduria.security.Modelos.Rol;
import com.registraduria.security.Modelos.Usuario;
import com.registraduria.security.Repositorios.RepositorioRol;
import com.registraduria.security.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    //Endpoint para obtener una lista con todos los usuarios
    @Autowired
    private RepositorioRol miRepositorioRol;


    @GetMapping("")
    public List<Usuario> index() {
        return this.miRepositorioUsuario.findAll();
    }

    //Crear Usuario
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Usuario create(@RequestBody Usuario infoUsuario) {
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return this.miRepositorioUsuario.save(infoUsuario);

    }

    //Listar por Id
    @GetMapping("{id}")
    public Usuario show(@PathVariable String id) {
        Usuario usuarioActual = this.miRepositorioUsuario.findById(id).orElse(null);
        return usuarioActual;
    }

    //Actualizar
    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id, @PathVariable String id_rol) {
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        Rol rolActual = this.miRepositorioRol
                .findById(id_rol)
                .orElse(null);
        if (usuarioActual != null && rolActual != null) {
            usuarioActual.setRol(rolActual);
            return this.miRepositorioUsuario.save(usuarioActual);
        } else {
            return null;
        }
    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes()); //Array de bytes
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));//convertir byte a hexadecimal
        }
        return sb.toString();
    }

    @PostMapping("/validar")
    public Usuario validate(@RequestBody Usuario infoUsuario,
                            final HttpServletResponse response) throws
            IOException {
        Usuario usuarioActual = this.miRepositorioUsuario
                .getUserByEmail(infoUsuario.getCorreo());
        if (usuarioActual != null &&
                usuarioActual.getContrasena().equals(convertirSHA256(infoUsuario.getContrasena()))) {
            usuarioActual.setContrasena("");
            return usuarioActual;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
}

