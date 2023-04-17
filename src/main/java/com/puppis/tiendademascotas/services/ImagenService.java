package com.puppis.tiendademascotas.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.puppis.tiendademascotas.exceptions.NotFoundException;
import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.repository.ImagenRepository;

@Service
public class ImagenService {

	@Autowired
	private ImagenRepository imagenRepository;
	
	@Value("${media.location}")
	private String localizacionCarpeta;
	
	public List<ImagenModel> obtenerImagenes(){
		return imagenRepository.findAll();
		}
	
	public ImagenModel guardarImagen(String nombre) {
		ImagenModel imagen = new ImagenModel();
		Date fechaCarga = new Date();
		imagen.setNombre(nombre);
		imagen.setUbicacion(this.localizacionCarpeta);
		imagen.setFechaCarga(fechaCarga);
		imagen.setIsEliminado(false);
		return imagenRepository.save(imagen);
	}
	
	
	public void eliminarImagen(Long id) {
		try {
			imagenRepository.deleteById(id);
		}catch(Exception e){
			throw new NotFoundException("");			
		}
	}
	
	public ImagenModel buscarImagen(String nombre) {
		return imagenRepository.findByNombre(nombre);
	}
	
	public void modificarImagen(String nombreImagen) {
		ImagenModel imagenBD = this.buscarImagen(nombreImagen);
		Date fechaEliminacion = new Date();
		imagenBD.setFechaEliminacion(fechaEliminacion);
		imagenBD.setIsEliminado(true);
		
		imagenRepository.save(imagenBD);
	}
	
	
}
