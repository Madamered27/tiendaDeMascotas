package com.puppis.tiendademascotas.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.io.Files;
import com.puppis.tiendademascotas.model.ImagenModel;
import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.services.ArchivoService;
import com.puppis.tiendademascotas.services.ImagenService;
import com.puppis.tiendademascotas.services.ProductoService;


@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*")

public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ArchivoService archivoService;
	

	
	
	@GetMapping()
	public List<ProductoModel> obtenerProductos(){
		return productoService.obtenerProductos();
	}
	
	
	
	@GetMapping(path = "/{id}")
	public Optional<ProductoModel> obtenerProductoPorId(@PathVariable("id") Long id){
		return productoService.obtenerPorId(id);
	}
	
	//localhost:8080/producto/query?categoria=gato

	@GetMapping("/query")
	public List<ProductoModel> obtenerProductosPorCategoria(@RequestParam("categoria") String categoria){
		return productoService.obtenerPorCategoria(categoria);
	}
	
	
	@GetMapping("/faltanteStock")
	public List<ProductoModel> obtenerParaReponerStock(){
		return productoService.obtenerParaReponerStock();
	}
	
	
	
	//@RequestBody para definir el tipo de contenido del cuerpo de la solicitud	
	
	@PostMapping()
	public ProductoModel guardarProducto(@RequestParam("categoria") String categoria, @RequestParam("nombre") String nombre, @RequestParam("precio") Integer precio, 
			@RequestParam("stock") Integer stock,  @RequestParam("file") MultipartFile file){
		ProductoModel producto = new ProductoModel();
		producto.setCategoria(categoria);
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setStock(stock);
		ImagenModel img = archivoService.guardarImagenDB();
		img.setNombre("img" + img.getId());
		String path = archivoService.almacenarArchivo(file, img.getNombre());
		producto.setImg("http://localhost:8080/img/" + path);
		return productoService.guardarProducto(producto);
	}
	
	
	
	
	@PutMapping(path = "/{id}")
	public void actualizarProducto(@PathVariable("id") Long id, @RequestParam("categoria") String categoria, @RequestParam("nombre") String nombre, @RequestParam("precio") Integer precio, 
			@RequestParam("stock") Integer stock,  @RequestParam("file") MultipartFile file) {
		ProductoModel producto = new ProductoModel();
		producto.setCategoria(categoria);
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setStock(stock);
		producto.setId(id);
		ImagenModel img = archivoService.guardarImagenDB();
		img.setNombre("img" + img.getId());
		String path = archivoService.almacenarArchivo(file, img.getNombre());
		producto.setImg("http://localhost:8080/img/" + path);
		this.productoService.modificarProducto(producto);
		
	}
	


	@DeleteMapping(path = "/{id}")
	public String eliminarProducto(@PathVariable("id") Long id) {
		productoService.eliminarProducto(id);
		return "El producto ha sido eliminado";		
	}
	
	
	//Metodo para update no hace falta hacer ya que CrudRepository con el metodo save(), 
	//si se le envia el id lo toma como una modificacion en cambio si no tiene id sabe que es un producto nuevo

}
