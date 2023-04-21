package com.puppis.tiendademascotas.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.services.ArchivoService;

@WebMvcTest (ImagenController.class)
public class ImagenControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ArchivoService archivoService;
	
	  @DisplayName("Test para obtener lista de imagenes")	  
	  @Test void testObtenerImagenes() throws Exception { 
		  //given 
	  ImagenModel img = new ImagenModel(); 
	  
	  ImagenModel img1 = new ImagenModel();

	  
	  List<ImagenModel> imagenes = new ArrayList<>(); 
	  imagenes.add(img);
	  imagenes.add(img1);
	  
	  given(archivoService.obtenerImagenes()).willReturn(imagenes);
	  
	  //when 
	  ResultActions response = mockMvc.perform(get("/producto"));
	
	  }
	      
	  @DisplayName("Test para eliminar una imagen")
	  @Test void testEliminarImagen() throws Exception {   
	  //given 
	   long imgId= 1L; 
	  willDoNothing().given(archivoService).eliminarImagen(imgId);
	  
	  //when - 
	  ResultActions response = mockMvc.perform(delete("/imagen/{id}",imgId));
	  
	  //then - 
	  response.andExpect(status().isOk()) .andDo(print());
	  
	  }
	  
	  
	  
	  
	  
	  
	  
	  
}
