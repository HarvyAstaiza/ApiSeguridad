package com.registraduria.security.Repositorios;
import com.registraduria.security.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepositorioRol extends MongoRepository<Rol,String>  {
}
