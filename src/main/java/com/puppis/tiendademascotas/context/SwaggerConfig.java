package com.puppis.tiendademascotas.context;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2 
public class SwaggerConfig {
	
	
	//Docket es una clase de config que da opciones para personalizar la docu de swagger
	//Docket indica a swagger que tiene que tener en cuenta cuando crea la docu
	//swagger escanea todo lo que este en paquete controller, busca clases con @RestController, renderiza los metodos y va documentando la api
	//metodo apis para indicar que solo se deben documentar los controllers del paquete indicado y el metodo paths indica que se deben documentar todas las rutas de la api
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.puppis.tiendademascotas.controller")) 
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
 
    //ApiInfo es una clase que proporciona info adicional sobre la api (titulo, descripcion, version, terminos de uso y la info de contacto)
    //Contact(nombreEmpresa,sitioWeb,mail,infoLicencias, urlLicencias)
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Tienda de mascotas",
                "La API REST de Puppis",
                "v1",
                "Terminos de uso",
                new Contact("Puppis", "www.tiendamascotas.com", "tiendamascotas@puppis.com"),
                "Licencias API", "API license URL", Collections.emptyList());
    }
}
