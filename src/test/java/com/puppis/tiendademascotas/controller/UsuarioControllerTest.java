package com.puppis.tiendademascotas.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.model.UsuarioModel;
import com.puppis.tiendademascotas.services.UsuarioServices;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioServices usuarioService;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
	}

	@DisplayName("Test para obtener lista de usuarios")
	@Test
	void testObtenerUsuarios() throws Exception {
		// given
		UsuarioModel usuario = new UsuarioModel();
		UsuarioModel usuario1 = new UsuarioModel();

		List<UsuarioModel> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		usuarios.add(usuario1);

		given(usuarioService.obtenerUsuarios()).willReturn(usuarios);

		// when
		ResultActions response = mockMvc.perform(get("/usuario"));

		// then
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.size()", is(usuarios.size())));

	}
	
	@DisplayName("Test para guardar un usuario")
	@Test
	void testGuardarUsuario() throws JsonProcessingException, Exception {
		// given 
		UsuarioModel usuario = new UsuarioModel();
		usuario.setId(1L);
		usuario.setEmail("test");
		usuario.setContrasenia("1234");
		given(usuarioService.guardarUsuario(any(UsuarioModel.class))).willReturn(usuario);
		// when 
	    ResultActions response = mockMvc.perform(post("/usuario").contentType(MediaType.APPLICATION_JSON)
	    		.content(objectMapper.writeValueAsString(usuario)));

		// then 
		response.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.email", is("test")))
				.andExpect(jsonPath("$.contrasenia", is("1234")));

	}
	
	
	  @DisplayName("Test para eliminar un producto") 
	  @Test void testEliminarProducto() throws Exception {   
	  //given 
	   long usuarioId= 1L; 
	  willDoNothing().given(usuarioService).eliminarUsuario(usuarioId);
	  
	  //when - 
	  ResultActions response = mockMvc.perform(delete("/usuario/{id}",usuarioId));
	  
	  //then - 
	  response.andExpect(status().isOk()) .andDo(print());
	  
	  }
	  
	  
	  @DisplayName("Test para buscar un usuario")
	  
	  @Test void testObtenerUsuario() throws Exception { 
		  //given 
		  MultiValueMap<String, String> usuario = new LinkedMultiValueMap<>();
			usuario.add("email","test");
			usuario.add("password","1234");
			
			UsuarioModel usuarioBd = new UsuarioModel();
			usuarioBd.setContrasenia("1234");
			usuarioBd.setEmail("test");
	  
	  given(usuarioService.buscarUsuario("test", "1234")).willReturn(usuarioBd);
	  
	  //when - 
	  ResultActions response = mockMvc.perform(get("/usuario/buscarUsuario").params(usuario));
	  //then - 
		response.andDo(print())
		.andExpect(jsonPath("$.email", is("test")))
		.andExpect(jsonPath("$.contrasenia", is("1234")));

	  }
	

}
