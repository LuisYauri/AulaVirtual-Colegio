package pe.edu.savbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.savbackend.domain.ContenidoDto;
import pe.edu.savbackend.service.contenido.ContenidoService;

/**
 * UsuariosController
 */

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/contenidos")
public class ContenidoController {
  
	@Autowired
	ContenidoService contenidoService;
	//gabriel
	@RequestMapping("")//"?idCriterio={idCriterio}&codigoGrado={codigoGrado}"
	public List<ContenidoDto> filtrar(@RequestParam(required = false) Integer idCriterio, @RequestParam(required = false) String codigoGrado) {
		return contenidoService.getLsContenido(idCriterio, codigoGrado);
	}


}