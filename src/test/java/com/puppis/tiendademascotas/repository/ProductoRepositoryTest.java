package com.puppis.tiendademascotas.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.services.ArchivoService;
import com.puppis.tiendademascotas.services.ProductoService;



@DataJpaTest 
public class ProductoRepositoryTest {


	@Autowired
	ProductoRepository productoRepository;
	ProductoModel producto;

	@BeforeEach
	void setUp() {
		producto = new ProductoModel();
		producto.setNombre("Pelota tenis");
		producto.setPrecio(30);
		producto.setStock(10);
		producto.setCategoria("juguetes_perros");
	}
	
	@DisplayName("Test para guardar un producto")
	@Test
	void testGuardarProducto() {
		//given - condicion previa o configuracion

		
		//when - accion o comportamiento se va a probar
		ProductoModel productoGuardado = productoRepository.save(producto);
		
		//then - verificacion
		assertThat(productoGuardado).isNotNull();
	}
	
	
	
	@DisplayName("Test para obtener lista de productos")
	@Test
	void testObtenerProductos() {
		//given - dado estos empleados guardados
		ProductoModel producto1 = new ProductoModel();
		producto1.setNombre("Hueso de goma");
		producto1.setPrecio(30);
		producto1.setStock(10);
		productoRepository.save(producto);
		productoRepository.save(producto1);
		
		//when - cuando pido la lista
		 List<ProductoModel> productos = productoRepository.findAll();
		
		//then - obtengo la lista 
		 assertThat(productos).isNotNull();
		 assertThat(productos.size()).isEqualTo(2);
	}
	
	@DisplayName("Test para obtener un producto por id")
	@Test
	void testObtenerPorId() {
		//given 
		productoRepository.save(producto);
		
		//when - cuando pido por id
		Optional<ProductoModel> productoId = productoRepository.findById(producto.getId());

		//then - obtengo la lista 
		assertThat(productoId).isNotNull();
	}
	
	@DisplayName("Test para actualizar un producto")
	@Test
	void testModificarProducto() {
		//given 
		productoRepository.save(producto);
		
		//when - 
		ProductoModel productoGuardado = productoRepository.findById(producto.getId()).get();
		productoGuardado.setNombre("rascador");
		productoGuardado.setPrecio(50);
		ProductoModel productoActualizado = productoRepository.save(productoGuardado);
		
		//then - 
		assertThat(productoActualizado.getNombre()).isEqualTo("rascador");
		assertThat(productoActualizado.getPrecio()).isEqualTo(50);

	}
	
	@DisplayName("Test para eliminar un producto")
	@Test
	void testEliminarProducto() {
		//given 
		productoRepository.save(producto);
		
		//when - 
		productoRepository.deleteById(producto.getId());
		Optional<ProductoModel> productoEliminado = productoRepository.findById(producto.getId());
		
		//then - 
		assertThat(productoEliminado).isEmpty();

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

		
		ProductoModel producto2 = new ProductoModel();
		producto2.setNombre("rascador");
		producto2.setPrecio(30);
		producto2.setStock(10);
		producto2.setCategoria("juguetes_gato");
		
		productoRepository.save(producto1);
		productoRepository.save(producto2);
		productoRepository.save(producto);
		
		//when - 
		List<ProductoModel> productosPorCategoria = productoRepository.findByCategoria("juguetes_perros");

		//then - 
		assertThat(productosPorCategoria.size()).isEqualTo(2);

	}
	
	
	
	@DisplayName("Test para obtener productos para reponer stock ")
	@Test
	void testObtenerParaReponerStock() {
		//given 
		List <ProductoModel> productosFaltanteStock = new ArrayList<>();
		ProductoModel producto1 = new ProductoModel();
		producto1.setNombre("Hueso de goma");
		producto1.setStock(5);
	
		ProductoModel producto2 = new ProductoModel();
		producto2.setNombre("rascador");
		producto2.setStock(2);
		
		productoRepository.save(producto1);
		productoRepository.save(producto2);
		productoRepository.save(producto);

		List<ProductoModel> productos = productoRepository.findAll();
		
		//when - 
		for(ProductoModel producto : productos){
			if(producto.getStock() <= 5) {
				productosFaltanteStock.add(producto);
			}
		}

		//then - 
		assertThat(productosFaltanteStock.size()).isEqualTo(2);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
