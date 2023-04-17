package com.puppis.tiendademascotas.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.puppis.tiendademascotas.services.ArchivoService;

@EnableScheduling
@Component
public class TareaProgramadaBorrado {

	@Autowired
	private ArchivoService archivoService;
	
	
	
	// Para ejecuta todos los dias, o sea cada 24 hs  86400000 milisegundos
	@Scheduled(fixedDelay = 200000)
	  public void borradoDefinitivoImagenes() {
	    archivoService.borrarImagenes();
	  }
	
}
