package com.puppis.tiendademascotas.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.repository.ProductoRepository;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductoServiceTest {
	
	
	//@Mock con esta anotacion se indica que se va a crear un simulacro
	@MockBean
	private ProductoRepository productoRepository;
	
	@MockBean
	private ArchivoService archivoService;
	
	//@InjectMocks crea instancia de la clase y se le inyectan los mocks
	@Autowired
    private ProductoService productoService;
	
	private ProductoModel producto;
    
	@BeforeEach
	void setUp() {
		producto = new ProductoModel();
		producto.setId(1L);
		producto.setNombre("Pelota tenis");
		producto.setPrecio(30);
		producto.setStock(10);
		producto.setImg("http");
		producto.setCategoria("juguetes_perros");
	}
    
    
	@DisplayName("Test para guardar un producto")
	@Test
	void testGuardarProducto() {
		//given - condicion previa o configuracion
		//metodo given configura comportamiento previo
		//simula metodo de productoRepository y se indica que va a devolver
		given(productoRepository.save(any(ProductoModel.class))).willReturn(producto);
		
		//when - accion o comportamiento se va a probar
		//cuando dentro de guardar producto se llame al metodo save del repository, se llama
		//al metodo save simulado
		ProductoModel productoGuardado = productoService.guardarProducto(producto);
		
		//then - verificacion
		assertThat(productoGuardado).isNotNull();
		}
    
	@DisplayName("Test para obtener lista de productos")
	@Test
	void testObtenerProductos() {
		//given 
		ProductoModel producto1 = new ProductoModel();
		producto1.setNombre("Hueso de goma");
		producto1.setPrecio(30);
		producto1.setStock(10);
		producto1.setId(2L);
		given(productoRepository.findAll()).willReturn(List.of(producto,producto1));
		
		//when 
		 List<ProductoModel> productos = productoService.obtenerProductos();
		
		//then 
		 assertThat(productos).isNotNull();
		 assertThat(productos.size()).isEqualTo(2);
	}
	
	@DisplayName("Test para obtener lista de productos vacia")
	@Test
	void testObtenerProductosListaVacia() {
		//given 
		given(productoRepository.findAll()).willReturn(Collections.emptyList());
		
		//when 
		 List<ProductoModel> productos = productoService.obtenerProductos();
		
		//then 
		 assertThat(productos).isEmpty();
		 assertThat(productos.size()).isEqualTo(0);
	}
	
    @DisplayName("Test para obtener un producto por id")
	@Test
	void queSePuedaObtenerUnProducto() {
    	//given 
		given(productoRepository.findById(anyLong())).willReturn(Optional.of(producto));	
    	
		//when - cuando pido por id
		Optional<ProductoModel> productoId = productoService.obtenerPorId(producto.getId());

		//then - obtengo la lista 
		 assertThat(productoId).isNotNull();
		 verify(productoRepository).findById(argThat(arg -> arg != null && arg.equals(1L)));
		 
		
	}
    
    @DisplayName("Test para actualizar un producto")
	@Test
	void testModificarProducto() {
		//given 
    	given(productoRepository.findById(anyLong())).willReturn(Optional.of(producto));
    	given(productoRepository.save(producto)).willReturn(producto);
    	producto.setNombre("rascador");
		
		//when - 
		ProductoModel productoActualizado = productoService.modificarProducto(producto);
		
		//then - 
		assertThat(productoActualizado.getNombre()).isEqualTo("rascador");

	}
	
	@DisplayName("Test para eliminar un producto")
	@Test
	void testEliminarProducto() {
		//given 
		long productoId = 1L;
		given(productoRepository.findById(anyLong())).willReturn(Optional.of(producto));
		
		//se indica que no se va a retornar nada, solo se ejecuta la accion de eliminar
		willDoNothing().given(productoRepository).deleteById(productoId);
		
		//when - 
		productoService.eliminarProducto(productoId);
		
		//then - 
		//con times se va que se haya ejecutado x cantidad de veces
		//como metodo eliminarProducto es void, se va a verificar que se realice la accion de eliminado
		verify(productoRepository,times(1)).deleteById(productoId);

	}
	
	@DisplayName("Test para obtener productos por categorias")
	@Test
	void testObtenerPorCategoria() {
		//given 
		ProductoModel producto1 = new ProductoModel();
		producto1.setNombre("Hueso de goma");
		producto1.setPrecio(30);
		producto1.setStock(10);
		producto1.setCategoria("juguetes_perros");

		given(productoRepository.findByCategoria("juguetes_perros")).willReturn(List.of(producto,producto1));
		
		//when - 
		List<ProductoModel> productosPorCategoria = productoService.obtenerPorCategoria("juguetes_perros");

		//then - 
		assertThat(productosPorCategoria.size()).isEqualTo(2);

	}
	
	
	
	@DisplayName("Test para obtener productos para reponer stock ")
	@Test
	void testObtenerParaReponerStock() {
		//given 
		ProductoModel producto1 = new ProductoModel();
		producto1.setNombre("Hueso de goma");
		producto1.setStock(5);
	
		ProductoModel producto2 = new ProductoModel();
		producto2.setNombre("rascador");
		producto2.setStock(2);
		
		given(productoRepository.findAll()).willReturn(List.of(producto2,producto1));
		
		//when - 
		List<ProductoModel> productosFaltanteStock = productoService.obtenerParaReponerStock();

		//then - 
		assertThat(productosFaltanteStock.size()).isEqualTo(2);

	}
	
	

}
