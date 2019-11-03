package pe.edu.savbackend.controller;

import java.util.Arrays;
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
import pe.edu.savbackend.domain.tarea.TareaDto;
import pe.edu.savbackend.service.evaluacion.EvaluacionService;

/**
 * UsuariosController
 */

@RestController
@RequestMapping("/tareas")
public class TareaController {

	@Autowired
	EvaluacionService evaluacionService;
	
	
	@RequestMapping("")//?idEstudiante={idEstudiante}&fecha={fecha}
	public List<TareaDto> filtrar(@RequestParam(required = false) Integer idEstudiante, @RequestParam(required = false) String fecha) {
		
		System.out.println("ID ESTDIANTES: "+idEstudiante);
		return evaluacionService.getLsTareas(idEstudiante);// filtrar por estudiante
	}

	@RequestMapping("/{idTareas}/preguntas")
	public TareaDto getPreguntasPorTarea(@PathVariable Integer idTareas) {
		return TareaDto.builder().build();
	}

	@PostMapping(value = "/{idTareas}/finalizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadisticaDto finalizar(@RequestBody TareaDto er) {
		System.out.println("Ingreso a post " + er);
		return EstadisticaDto.builder().build();
	}

}