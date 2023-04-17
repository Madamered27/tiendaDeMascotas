package com.puppis.tiendademascotas.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.puppis.tiendademascotas.model.ImagenModel;

public interface ImagenRepository extends JpaRepository<ImagenModel, Long>{
	
	public abstract ImagenModel findByNombre(String nombre);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM ImagenModel i WHERE i.fechaEliminacion <= :fecha")
	void deleteByFechaCreacionBefore(@Param("fecha") Date fecha);

}
