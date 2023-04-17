package com.puppis.tiendademascotas.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.puppis.tiendademascotas.exceptions.NotFoundException;
import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.repository.ImagenRepository;

@Service
public class ArchivoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ImagenRepository imagenRepository;
	
	@Value("${media.location}")
	private String localizacionArchivo;

	private Path rootLocation;

	@PostConstruct
	public void init() {
		rootLocation = Paths.get(localizacionArchivo);
	}

	public List<ImagenModel> obtenerImagenes(){
		return imagenRepository.findAll();
		}
	
	public String almacenarArchivo(MultipartFile archivo, String nombre) {
		
		try {

			String nombreArchivo = nombre;
			Path destinoArchivo = rootLocation.resolve(Paths.get(nombreArchivo))
					.normalize().toAbsolutePath();
			

			Files.copy(archivo.getInputStream(),destinoArchivo, StandardCopyOption.REPLACE_EXISTING);

		
			return nombreArchivo;
		}catch(IOException e) {
			throw new RuntimeException();
		}
	}
	
	
	public void borrarArchivo(List<ImagenModel> imagenesAEliminar) {
		
		//String nombreArchivo = archivo.substring(26);
		
		for(ImagenModel imagen : imagenesAEliminar) {
			
			Path destinoArchivo = rootLocation.resolve(Paths.get(imagen.getNombre()))
					.normalize().toAbsolutePath();
			try {
				Files.deleteIfExists(destinoArchivo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
        
    }
	
	public void eliminarImagen(Long id) {
		try {
			imagenRepository.deleteById(id);
		}catch(Exception e){
			throw new NotFoundException("");			
		}
	}
	
	
	public ImagenModel guardarImagenDB() {
		ImagenModel imagen = new ImagenModel();
		Date fechaCarga = new Date();
		
		imagen.setNombre("");
		imagen.setUbicacion(this.localizacionArchivo);
		imagen.setFechaCarga(fechaCarga);
		imagen.setIsEliminado(false);
		return imagenRepository.save(imagen);
	}
	
	
	public ImagenModel buscarImagen(String nombre) {
		return imagenRepository.findByNombre(nombre);
	}
	
	public void modificarImagen(String imagenEnBD) {
		String nombreImagenBD = imagenEnBD.substring(26);
		ImagenModel imagenBD = this.buscarImagen(nombreImagenBD);
		Date fechaEliminacion = new Date();
		imagenBD.setFechaEliminacion(fechaEliminacion);
		imagenBD.setIsEliminado(true);
		
		imagenRepository.save(imagenBD);
	}
	


	  public void borrarImagenes() {
		  List<ImagenModel> imagenesBD = this.obtenerImagenes();
		  List<ImagenModel> imagenesEliminadas = this.filtrarPorImagenesAEliminar(imagenesBD);
		  
		 this.borrarArchivo(imagenesEliminadas);  
		 
		  Date fechaActual = new Date();
		  imagenRepository.deleteByFechaCreacionBefore(fechaActual);		  

	  }
	  
	  
	  private List<ImagenModel> filtrarPorImagenesAEliminar( List<ImagenModel> imagenesBD){
		  List<ImagenModel> imagenesEliminadas = new ArrayList<>();
		  for(ImagenModel imagen : imagenesBD) {
			  if(imagen.getFechaEliminacion() != null) {
				  imagenesEliminadas.add(imagen);
			  }
		  }
		  
		  return imagenesEliminadas;
	  }
	
	
	

}
