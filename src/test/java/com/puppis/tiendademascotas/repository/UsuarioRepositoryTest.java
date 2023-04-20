package com.puppis.tiendademascotas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.puppis.tiendademascotas.model.UsuarioModel;

@DataJpaTest 
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;
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
		//given - condicion previa o configuracion

		
		//when - accion o comportamiento se va a probar
		UsuarioModel usuarioGuardado = usuarioRepository.save(usuario);
		
		//then - verificacion
		assertThat(usuarioGuardado).isNotNull();
	}
	
	@DisplayName("Test para eliminar un usuario")
	@Test
	void testEliminarUsuario() {
		//given 
		usuarioRepository.save(usuario);
		
		//when - 
		usuarioRepository.deleteById(usuario.getId());
		Optional<UsuarioModel> productoEliminado = usuarioRepository.findById(usuario.getId());
		
		//then - 
		assertThat(productoEliminado).isEmpty();

	}
	
	@DisplayName("Test para actualizar un usuario")
	@Test
	void testModificarUsuario() {
		//given 
		usuarioRepository.save(usuario);
		
		//when - 
		UsuarioModel usuarioGuardado = usuarioRepository.findById(usuario.getId()).get();
		usuarioGuardado.setEmail("mail");
		UsuarioModel usuarioctualizado = usuarioRepository.save(usuarioGuardado);
		
		//then - 
		assertThat(usuarioctualizado.getEmail()).isEqualTo("mail");

	}
	
	@DisplayName("Test para obtener un usuario por id")
	@Test
	void testObtenerUsuarioPorId() {
		//given 
		usuarioRepository.save(usuario);
		
		//when - cuando pido por id
		 Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(usuario.getId());

		//then - obtengo la lista 
		 assertThat(usuarioOptional.isPresent());
		 assertThat(usuarioOptional.get()).isEqualTo(usuario);
		}
	
	
	@DisplayName("Test para obtener un usuario vacio si id no existe")
	@Test
	void testObtenerUsuarioVacioPorId() {
		//given 
		usuarioRepository.save(usuario);
		
		//when 
		 Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(2L);

		//then
		 assertThat(usuarioOptional.isEmpty());
		}
	
	
	
	
	
	
	
	
}
