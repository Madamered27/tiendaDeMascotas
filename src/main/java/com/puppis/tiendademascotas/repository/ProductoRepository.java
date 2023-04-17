package com.puppis.tiendademascotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puppis.tiendademascotas.model.ProductoModel;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long>{
	// Metodos basicos como save(), saveAll(), deleteById(), deleteAll() findAll(), findById() y count() ya tienen implementacion predeterminada
	//Se usa clase os mproductoModel como entidad JPA
	//CrudRepository<TipoDeDato, TipoDeID>
	
	
	
	//spring data JPA infiere que se esta buscando por la prop desea de la entidad X y genera la consulta apropiada en tiempo de ejecucion
	public abstract List<ProductoModel>findByCategoria(String categoria);
	public abstract List<ProductoModel>findByNombre(String nombre);
	
	
}
