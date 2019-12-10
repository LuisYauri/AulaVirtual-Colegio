package pe.edu.savbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.savbackend.domain.tarea.EstadisticaDto;
import pe.edu.savbackend.domain.tarea.ExamenDto;
import pe.edu.savbackend.service.evaluacion.EvaluacionService;

/**
 * UsuariosController
 */

@RestController
@RequestMapping("/examenes")
public class ExamenController {

	@Autowired
	EvaluacionService evaluacionService;
	// listar examenes
	@RequestMapping("") // ?idEstudiante={idEstudiante}&fecha={fecha}
	public List<ExamenDto> filtrar(@RequestParam(required = false) Integer idEstudiante, @RequestParam(required = false) String fecha) {
		return evaluacionService.getLsExamenes(idEstudiante);// filtrar por estudiante
	}

	// listar preguntas de examen
	@RequestMapping("/{idExamen}/preguntas")
	public ExamenDto getPreguntasPorExamen(@PathVariable Integer idExamen) {

		return evaluacionService.getPreguntasPorExamen(idExamen);
	}
	
	@PostMapping(value = "/{idExamen}/estudiante/{idEstudiante}/finalizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadisticaDto finalizar(@PathVariable Integer idExamen, @PathVariable Integer idEstudiante, @RequestBody ExamenDto examenDto) {
		examenDto.setIdExamen(idExamen);
		examenDto.setIdEstudiante(idEstudiante);
		return evaluacionService.finalizarExamen(examenDto);

	}

}