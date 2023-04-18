package com.puppis.tiendademascotas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.services.ArchivoService;
import com.puppis.tiendademascotas.services.ProductoService;
import com.puppis.tiendademascotas.services.UsuarioServices;

@WebMvcTest
public class ProductoControllerTest {

	@Autowired
	// MockMvc para probar peticiones http
	private MockMvc mockMvc;

	// anotacion para agregar objetos simulados al contexto de la app
	// el simulacro reemplaza cualquier bean existente del mismo tipo en el contexto
	// de la app
	@MockBean
	private ProductoService productoService;

	@MockBean
	private ArchivoService archivoService;

	@MockBean
	private UsuarioServices usuarioService;

	@Autowired
	private ObjectMapper objectMapper;
	
	 @InjectMocks
	 private ProductoController productoController;

	
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

	
	  @DisplayName("Test para obtener lista de productos")
	  
	  @Test void testObtenerProductos() throws Exception { 
		  //given 
	  ProductoModel producto = new ProductoModel(); producto.setId(1L);
	  producto.setNombre("Pelota tenis"); producto.setPrecio(30);
	  producto.setStock(10); producto.setImg("http");
	  producto.setCategoria("juguetes_perros");
	  
	  ProductoModel producto1 = new ProductoModel();
	  producto1.setNombre("Hueso de goma"); producto1.setPrecio(30);
	  producto1.setStock(10); producto1.setId(2L);
	  
	  List<ProductoModel> productos = new ArrayList<>(); 
	  productos.add(producto);
	  productos.add(producto1);
	  
	  given(productoService.obtenerProductos()).willReturn(productos);
	  
	  //when 
	  ResultActions response = mockMvc.perform(get("/producto"));
	  
	  //then 
	  response.andExpect(status().isOk()) .andDo(print())
	  .andExpect(jsonPath("$.size()", is(productos.size()))); 
	  
	  }
	  
	  @DisplayName("Test para obtener un producto por id")
	  
	  @Test void queSePuedaObtenerUnProducto() throws Exception { 
		  //given 
	  Long productoId = 1L; 
	  ProductoModel producto = new ProductoModel();
	  producto.setNombre("Pelota tenis"); producto.setPrecio(30);
	  producto.setStock(10); producto.setImg("http");
	  producto.setCategoria("juguetes_perros");
	   
	  given(productoService.obtenerPorId(productoId)).willReturn(Optional.of(
	  producto));
	  
	  //when - cuando pido por id 
	  ResultActions response = mockMvc.perform(get("/producto/{id}", productoId));
	  
	  //then - obtengo la lista 
	  response.andExpect(status().isOk()) .andDo(print())
	  .andExpect(jsonPath("$.nombre", is(producto.getNombre())))
	  .andExpect(jsonPath("$.precio", is(producto.getPrecio())))
	  .andExpect(jsonPath("$.stock", is(producto.getStock())))
	  .andExpect(jsonPath("$.categoria", is(producto.getCategoria())))
	  .andExpect(jsonPath("$.img", is(producto.getImg())));
	  
	  }
	  
	  @DisplayName("Test para eliminar un producto")
	  
	  @Test void testEliminarProducto() throws Exception { 
	  
	  //given 
	   long productoId= 1L; 
	  willDoNothing().given(productoService).eliminarProducto(productoId);
	  
	  //when - 
	  ResultActions response = mockMvc.perform(delete("/producto/{id}",
	  productoId));
	  
	  //then - 
	  response.andExpect(status().isOk()) .andDo(print());
	  
	  }
	  
	  
	  @DisplayName("Test para obtener productos por categorias")
	  
	  @Test void testObtenerPorCategoria() throws Exception { 
		  //given 
	  ProductoModel producto1 = new ProductoModel(); 
	  producto1.setNombre("Hueso de goma");
	  producto1.setPrecio(30); producto1.setStock(10);
	  producto1.setCategoria("juguetes_perros");
	  
	  given(productoService.obtenerPorCategoria(producto1.getCategoria())).willReturn(List.of(producto1));
	  
	  //when - 
	  ResultActions response = mockMvc.perform(get("/producto/query?categoria={categoria}", producto1.getCategoria()));
	  
	  //then - 
	  response.andExpect(status().isOk()) .andDo(print())
	  .andExpect(jsonPath("$.size()", is(1)));
	  
	  }
	  
	  @DisplayName("Test para obtener productos para reponer stock ")
	  
	  @Test void testObtenerParaReponerStock() throws Exception { 
		  //given
	  ProductoModel producto1 = new ProductoModel();
	  producto1.setNombre("Hueso de goma"); 
	  producto1.setStock(5);
	  
	  ProductoModel producto2 = new ProductoModel();
	  producto2.setNombre("rascador"); 
	  producto2.setStock(2);
	  
	  given(productoService.obtenerParaReponerStock()).willReturn(List.of(producto2,producto1));
	  
	  //when - 
	  ResultActions response = mockMvc.perform(get("/producto/faltanteStock"));
	  
	  
	  
	  //then - 
	  response.andExpect(status().isOk()) 
	  .andDo(print())
	  .andExpect(jsonPath("$.size()", is(2)));
	  
	  }
	  
	  
	 

	@DisplayName("Test para guardar un producto")
	@Test
	void testGuardarProducto() throws JsonProcessingException, Exception {
		// given - condicion previa o configuracion
		MultiValueMap<String, String> producto = new LinkedMultiValueMap<>();
		MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

		producto.add("categoria", "juguetes_perros");
		producto.add("nombre", "Pelota tenis");
		producto.add("precio", "30");
		producto.add("stock", "10");

		ImagenModel imagen = new ImagenModel();
		Date fechaCarga = new Date();

		imagen.setNombre("");
		imagen.setUbicacion("");
		imagen.setFechaCarga(fechaCarga);
		imagen.setIsEliminado(false);

		given(productoService.guardarProducto(any(ProductoModel.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));
		given(archivoService.guardarImagenDB()).willReturn(imagen);

		// when - accion o comportamiento se va a probar
		ResultActions response = mockMvc
				.perform(MockMvcRequestBuilders
				.fileUpload("/producto")
				.file(file).params(producto));

		// then - verificacion
		response.andDo(print()).andExpect(jsonPath("$.categoria", is("juguetes_perros")))
				.andExpect(jsonPath("$.nombre", is("Pelota tenis"))).andExpect(jsonPath("$.precio", is(30)))
				.andExpect(jsonPath("$.stock", is(10)))
				.andExpect(jsonPath("$.img", containsString("http://localhost:8080/img/")));

	}
	
	
	@DisplayName("Test para actualizar un producto")
	@Test
	void testModificarProducto() throws JsonProcessingException, Exception {
		Long productoId = 1L;
		MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
		ProductoModel productoGuardado = new ProductoModel();
		productoGuardado.setNombre("Pelota tenis");
		productoGuardado.setId(1L);
		productoGuardado.setImg("http");
		productoGuardado.setPrecio(30);
		productoGuardado.setStock(10);
		productoGuardado.setCategoria("juguetes_perros");
		
		ProductoModel productoActualizado = new ProductoModel();
		productoActualizado.setCategoria("juguetes_perros");
		productoActualizado.setNombre("Hueso de goma");
		productoActualizado.setPrecio(30);
		productoActualizado.setStock(10);
		    

		    given(archivoService.guardarImagenDB()).willReturn(new ImagenModel());
		    given(productoService.obtenerPorId(productoId)).willReturn(Optional.of(productoGuardado));
			given(productoService.modificarProducto(any(ProductoModel.class)))
			.willAnswer((invocation) -> invocation.getArgument(0));


		    // when
		    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/producto/{id}", productoId)
		            .file(file)
		            .param("categoria", productoActualizado.getCategoria())
		            .param("nombre", productoActualizado.getNombre())
		            .param("precio", productoActualizado.getPrecio().toString())
		            .param("stock", productoActualizado.getStock().toString())
		            .with(request -> {
		                request.setMethod("PUT");
		                return request;
		            }));

		    // then
		    result.andExpect(status().isOk())
            	.andExpect(content().string(""));
		    verify(productoService).modificarProducto(any(ProductoModel.class));

	}

}
