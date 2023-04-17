package com.puppis.tiendademascotas.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.puppis.tiendademascotas.exceptions.NotFoundException;
import com.puppis.tiendademascotas.model.ProductoModel;
import com.puppis.tiendademascotas.repository.ProductoRepository;

@Service
public class ProductoService {
	
	@Autowired 
	private ProductoRepository productoRepository;
	
	@Autowired
	private ArchivoService archivoService;
	

	public List<ProductoModel> obtenerProductos(){
		return  productoRepository.findAll();
	}
	
	
	
	public Optional<ProductoModel> obtenerPorId(Long id){
		Optional<ProductoModel> productoPorId = productoRepository.findById(id);
		return productoPorId;
	}
	
	public List<ProductoModel> obtenerPorCategoria(String categoria){
		List<ProductoModel> categorias = productoRepository.findByCategoria(categoria);
		verificarSiListaEstaVacia(categorias, "Categoria incorrecta");
		return categorias;			
		
	}
	
	public ProductoModel modificarProducto(ProductoModel producto) {
		ProductoModel productoDB = obtenerPorId(producto.getId()).get();
		if(!producto.getImg().equals(productoDB.getImg())) {
			archivoService.modificarImagen(productoDB.getImg());
		}
		productoDB.setNombre(producto.getNombre());
		productoDB.setCategoria(producto.getCategoria());
		productoDB.setStock(producto.getStock());
		productoDB.setPrecio(producto.getPrecio());
		productoDB.setImg(producto.getImg());
		
	
		return productoRepository.save(productoDB);
		
	}
	
	public ProductoModel guardarProducto(ProductoModel producto) {
		return productoRepository.save(producto);
	}
	
	//puede lanzar excepcion EmptyResultDataAccessException si se intenta eliminar una entidad que no existe en la bd
	public void eliminarProducto(Long id) {
		try {
			ProductoModel producto = this.obtenerPorId(id).get();
			
			productoRepository.deleteById(id);
		}catch(Exception e){
			throw new NotFoundException("ID invalido");			
		}
	}
	
	public List<ProductoModel> obtenerParaReponerStock(){
		List<ProductoModel> productos =  productoRepository.findAll();
		List <ProductoModel> productosFaltanteStock = new ArrayList<>();
		
		for(ProductoModel producto : productos){
			if(producto.getStock() <= 5) {
				productosFaltanteStock.add(producto);
			}
		}
		
		return productosFaltanteStock;
		
	}
	

	private void verificarSiListaEstaVacia(List<ProductoModel> resultado, String mensajeError) {
	    if (resultado.isEmpty()) {
	        throw new NotFoundException(mensajeError);
	    }
	}

	private void verificarSiOptionalEstaVacio(Optional<ProductoModel> resultado, String mensajeError) {
	    if (resultado.isPresent()) {
	        throw new NotFoundException(mensajeError);
	    }
	}

	
	
	
	
	
}
