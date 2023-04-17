package com.puppis.tiendademascotas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.services.ArchivoService;

@RestController
@RequestMapping("/imagen")
public class ImagenController {

	@Autowired
	private ArchivoService archivoService;
	
	@GetMapping()
	public List<ImagenModel> obtenerImagenes(){
		return archivoService.obtenerImagenes();
	}
	
	@DeleteMapping(path = "/{id}")
	public String eliminarImagen(@PathVariable("id") Long id) {
		archivoService.eliminarImagen(id);
		return "Imagen eliminada";
	}
	
}
