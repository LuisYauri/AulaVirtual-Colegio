package pe.edu.savbackend.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.savbackend.domain.CriterioDto;
import pe.edu.savbackend.service.contenido.ContenidoService;
import pe.edu.savbackend.service.criterio.CriterioService;

/**
 * UsuariosController
 */

@RestController
@RequestMapping("/criterios")
public class CriterioController {

	@Autowired
	CriterioService criterio;
	
	@RequestMapping("/")
	public List<CriterioDto> filtrar(@RequestParam(required = false) Integer idArea) {
		return criterio.getLsTareas(idArea);
	}

}