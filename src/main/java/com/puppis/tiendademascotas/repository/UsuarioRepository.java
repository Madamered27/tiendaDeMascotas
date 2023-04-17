package com.puppis.tiendademascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puppis.tiendademascotas.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{
	
	public abstract UsuarioModel findByEmailAndContrasenia(String email, String contrasenia);

}
