package com.puppis.tiendademascotas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puppis.tiendademascotas.repository.UsuarioRepository;
import com.puppis.tiendademascotas.exceptions.NotFoundException;
import com.puppis.tiendademascotas.model.UsuarioModel;


@Service
public class UsuarioServices {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<UsuarioModel> obtenerUsuarios(){
		return usuarioRepository.findAll();
		}
	
	public UsuarioModel guardarUsuario(UsuarioModel usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public void eliminarUsuario(Long id) {
		try {
			usuarioRepository.deleteById(id);
		}catch(Exception e){
			throw new NotFoundException("ID invalido");			
		}
	}
	
	public UsuarioModel buscarUsuario(String email, String contra) {
		return usuarioRepository.findByEmailAndContrasenia(email, contra);
	}

}
