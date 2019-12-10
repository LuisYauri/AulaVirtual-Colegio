package pe.edu.savbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.savbackend.domain.comentario.RecursoDto;
import pe.edu.savbackend.service.recurso.RecursoService;

/**
 * UsuariosController
 */

@RestController
@RequestMapping("/recursos")
public class RecursoController {

	@Autowired
	RecursoService recursoService;
	
	@RequestMapping("/{idRecurso}/estudiante/{idEstudiante}")
	public RecursoDto obtenerPorId(@PathVariable Integer idRecurso, @PathVariable Integer idEstudiante) {
		return recursoService.obtenerRecursoPorId(idRecurso, idEstudiante); // devolver un recurso con lista de alumnos
	}

	@RequestMapping("")//?idSubContenido={idSubContenido}
	public List<RecursoDto> filtrar(@RequestParam(required = false) Integer idSubContenido) {
		return recursoService.filtrarPorIdSubcontenido(idSubContenido);
	}
}