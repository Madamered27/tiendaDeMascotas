package com.puppis.tiendademascotas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.puppis.tiendademascotas.model.UsuarioModel;
import com.puppis.tiendademascotas.services.UsuarioServices;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {
	
	@Autowired
	private UsuarioServices usuarioService;
	
	@GetMapping()
	public List<UsuarioModel> obtenerProductos(){
		return usuarioService.obtenerUsuarios();
	}
	
	@PostMapping()
	public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
		return usuarioService.guardarUsuario(usuario);
	}
	
	@DeleteMapping(path = "/{id}")
	public String eliminarProducto(@PathVariable("id") Long id) {
		usuarioService.eliminarUsuario(id);
		return "El usuario ha sido eliminado";		
	}
	
	@GetMapping("/buscarUsuario")
	public UsuarioModel buscarUsuario(@RequestParam("email")String email, @RequestParam("password")String contra) {
		return usuarioService.buscarUsuario(email, contra);
	}

}
