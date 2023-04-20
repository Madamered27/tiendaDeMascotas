package com.puppis.tiendademascotas.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.model.UsuarioModel;
import com.puppis.tiendademascotas.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UsuarioServiceTest {
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioServices usuarioService;

	UsuarioModel usuario;
	
	@BeforeEach
	void setUp() {
		usuario = new UsuarioModel();
		usuario.setContrasenia("1234");
		usuario.setEmail("test@test.com");
	}
	
	@DisplayName("Test para guardar un usuario")
	@Test
	void testGuardarUsuario() {
		//given
		given(usuarioRepository.save(any(UsuarioModel.class))).willReturn(usuario);
		
		//when - accion o comportamiento se va a probar
		UsuarioModel usuarioGuardado = usuarioService.guardarUsuario(usuario);
		
		//then - verificacion
		assertThat(usuarioGuardado).isNotNull();
		}
	
	@DisplayName("Test para obtener lista de usuarios")
	@Test
	void testObtenerUsuarios() {
		//given 
		UsuarioModel usuario1 = new UsuarioModel();

		given(usuarioRepository.findAll()).willReturn(List.of(usuario,usuario1));
		
		//when 
		 List<UsuarioModel> usuarios = usuarioService.obtenerUsuarios();
		
		//then 
		 assertThat(usuarios).isNotNull();
		 assertThat(usuarios.size()).isEqualTo(2);
	}
	
	@DisplayName("Test para buscar usuario")
	@Test
	void testBuscarUsuario() {
    	//given 
		given(usuarioRepository.findByEmailAndContrasenia(usuario.getEmail(),usuario.getContrasenia())).willReturn(usuario);	
    	
		//when - cuando pido por id
		UsuarioModel usuarioEncontrado = usuarioService.buscarUsuario(usuario.getEmail(),usuario.getContrasenia());

		//then - obtengo la lista 
		 assertThat(usuarioEncontrado).isNotNull();
		 
		
	}
	
	@DisplayName("Test para eliminar un usuario")
	@Test
	void testEliminarUsuario() {
		//given 
		long usuarioId = 1L;
		given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(usuario));
		
		//se indica que no se va a retornar nada, solo se ejecuta la accion de eliminar
		willDoNothing().given(usuarioRepository).deleteById(usuarioId);
		
		//when - 
		usuarioService.eliminarUsuario(usuarioId);
		
		//then - 
		verify(usuarioRepository,times(1)).deleteById(usuarioId);

	}
	
	
	
	
	
}
