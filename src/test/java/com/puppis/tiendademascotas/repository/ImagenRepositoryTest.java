package com.puppis.tiendademascotas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.model.ProductoModel;

@DataJpaTest 
public class ImagenRepositoryTest {

	@Autowired
	ImagenRepository imagenRepository;
	ImagenModel img;
	
	
	@BeforeEach
	void setUp() {
		img = new ImagenModel();
		img.setNombre("pelotaTenis.jpg");
		img.setUbicacion("");
		img.setIsEliminado(false);
		img.setFechaCarga(new Date());
		img.setFechaEliminacion(new Date());
	}
	
	@DisplayName("Test para guardar una imagen")
	@Test
	void testGuardarImagen() {
		//given - condicion previa o configuracion

		
		//when - accion o comportamiento se va a probar
		ImagenModel imagenGuardada = imagenRepository.save(img);
		
		//then - verificacion
		assertThat(imagenGuardada).isNotNull();
	}
	
	@DisplayName("Test para obtener lista de imagenes")
	@Test
	void testObtenerImagenes() {
		//given - dado estos empleados guardados
		ImagenModel img1 = new ImagenModel();
		imagenRepository.save(img);
		imagenRepository.save(img1);
		
		//when - cuando pido la lista
		 List<ImagenModel> imagenes = imagenRepository.findAll();
		
		//then - obtengo la lista 
		 assertThat(imagenes).isNotNull();
		 assertThat(imagenes.size()).isEqualTo(2);
	}
	
	@DisplayName("Test para obtener un producto por nombre")
	@Test
	void testObtenerPorNombre() {
		//given 
		imagenRepository.save(img);
		
		//when - cuando pido por id
		ImagenModel imagen = imagenRepository.findByNombre(img.getNombre());

		//then - obtengo la lista 
		assertThat(imagen).isNotNull();
	}
	
	
	
	@DisplayName("Test para actualizar una imagen")
	@Test
	void testModificarImagen() {
		//given 
		imagenRepository.save(img);
		
		//when - 
		ImagenModel imagenBd = imagenRepository.findByNombre(img.getNombre());
		imagenBd.setNombre("rascadorCuadrado.jpg");
		ImagenModel imagenActualizada = imagenRepository.save(imagenBd);
		
		//then - 
		assertThat(imagenActualizada.getNombre()).isEqualTo("rascadorCuadrado.jpg");

	}
	
	@DisplayName("Test para eliminar una imagen")
	@Test
	void testEliminarImagen() {
		//given 
		imagenRepository.save(img);
		
		//when - 
		imagenRepository.deleteByFechaCreacionBefore(new Date());
		ImagenModel imagenEliminada = imagenRepository.findByNombre(img.getNombre());
		
		//then - 
		assertThat(imagenEliminada).isNull();

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
