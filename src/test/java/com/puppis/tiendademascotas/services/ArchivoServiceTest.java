package com.puppis.tiendademascotas.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.repository.ImagenRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ArchivoServiceTest {
	
	@MockBean
	private ImagenRepository imagenRepository;
	
	@Autowired
	private ArchivoService archivoService;
	
	private ImagenModel img;
	
	@BeforeEach
	void setUp() {
		img = new ImagenModel();
		img.setNombre("pelotaTenis.jpg");
		img.setUbicacion("");
		img.setIsEliminado(false);
		img.setFechaCarga(new Date());
		img.setFechaEliminacion(new Date());
	}
	
	
	
	@DisplayName("Test para guardar un producto")
	@Test
	void testGuardarImagen() {
		//given - condicion previa o configuracion
		given(imagenRepository.save(any(ImagenModel.class))).willReturn(img);
		
		//when - accion o comportamiento se va a probar
		ImagenModel imagenGuardada = archivoService.guardarImagenDB();
		
		//then - verificacion
		assertThat(imagenGuardada).isNotNull();
		}
	
	
	@DisplayName("Test para obtener lista de imagenes")
	@Test
	void testObtenerImagenes() {
		//given 
		ImagenModel img1 = new ImagenModel();

		given(imagenRepository.findAll()).willReturn(List.of(img1,img));
		
		//when 
		 List<ImagenModel> imagenes = archivoService.obtenerImagenes();
		
		//then 
		 assertThat(imagenes).isNotNull();
		 assertThat(imagenes.size()).isEqualTo(2);
	}
	
	
	@DisplayName("Test para obtener un imagen por nombre")
	@Test
	void testObtenerPorNombre() {
		//given 
		given(imagenRepository.findByNombre(img.getNombre())).willReturn(img);
		
		//when - cuando pido por id
		ImagenModel imagen = archivoService.buscarImagen(img.getNombre());

		//then - obtengo la lista 
		assertThat(imagen).isNotNull();
	}
	
	
	@DisplayName("Test para eliminar una imagen")
	@Test
	void testEliminarImagen() {
		//given 
		given(imagenRepository.findByNombre(img.getNombre())).willReturn(img);
		willDoNothing().given(imagenRepository).deleteById(1L);;
		
		//when - 
		archivoService.eliminarImagen(1L);
		
		//then - 
		verify(imagenRepository,times(1)).deleteById(1L);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
