package com.puppis.tiendademascotas.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.services.ArchivoService;
import com.puppis.tiendademascotas.services.ProductoService;
import com.puppis.tiendademascotas.services.UsuarioServices;

@WebMvcTest
public class ProductoControllerTest {

	@Autowired
	//MockMvc para probar peticiones http
	private MockMvc mockMvc;
	
	
	//anotacion para agregar objetos simulados al contexto de la app 
	//el simulacro reemplaza cualquier bean existente del mismo tipo en el contexto de la app
	@MockBean
	private ProductoService productoService;
	
	@MockBean
	private ArchivoService archivoService;
	
	@MockBean
	private UsuarioServices usuarioService;
	
    @Autowired
    private ObjectMapper objectMapper;
	
	
//	@DisplayName("Test para guardar un producto")
//	@Test
//	void testGuardarProducto() throws JsonProcessingException, Exception {
//		//given - condicion previa o configuracion
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//		MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
//		map.add("categoria", "juguetes_perros");
//		map.add("nombre", "Pelota tenis");
//		map.add("precio", "30");
//		map.add("stock", "10");
//		map.add("file", "file");
//		
//		given(productoService.guardarProducto(any(ProductoModel.class)))
//		 		.willAnswer((invocation) -> invocation.getArgument(0));
//		
//		//when - accion o comportamiento se va a probar
//		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
//		        .multipart("/producto")
//		        .params(map));
//		
//		//then - verificacion
//		response.andDo(print())
//				//.andExpect(status().isCreated())
//				.andExpect(jsonPath("$.categoria", is("juguetes_perros")))
//				.andExpect(jsonPath("$.nombre", is("Pelota tenis")))
//				.andExpect(jsonPath("$.precio", is(30)))
//				.andExpect(jsonPath("$.stock", is(10)))
//				.andExpect(jsonPath("$.img", containsString("http://localhost:8080/img/")));
//				
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
