package pe.edu.savbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.savbackend.domain.EstudianteDto;
import pe.edu.savbackend.domain.PROFESOR.ProEstudianteDto;
import pe.edu.savbackend.domain.PROFESOR.ProEstudiantePorMatricularDto;
import pe.edu.savbackend.entity.Estudiante;
import pe.edu.savbackend.service.estudiante.EstudianteService;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

	//cambios
	@Autowired
	private EstudianteService estudianteService;
    
    @RequestMapping("")
	public List<EstudianteDto> listaEstudiantes() {
		return estudianteService.getLsEstudiantes().stream().sorted((c1,c2)-> c2.getIdEstudiante().compareTo(c1.getIdEstudiante())).collect(Collectors.toList()); 
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Estudiante registrarEstudiante(@RequestBody EstudianteDto estudiante) {
//		return null;
		return estudianteService.registrarEstudiante(estudiante);
	}

	@PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Estudiante actualizarEstudiante(@RequestBody EstudianteDto estudiante) {
		return estudianteService.actualizarEstudiante(estudiante);
	}

	@PatchMapping(value = "/{idEstudiante}")
	public Boolean eliminarEstudiante(@PathVariable Integer idEstudiante) {
		return estudianteService.eliminarEstudiante(idEstudiante);
	}
	
	@RequestMapping("/{idAula}") //estudiantes matriculados en el aula idAula
	public List<ProEstudianteDto> listaFiltrada(@PathVariable Integer idAula) {
		return estudianteService.filtrar(idAula).stream().sorted((c1,c2)-> c2.getIdEstudiante().compareTo(c1.getIdEstudiante())).collect(Collectors.toList()); 
	}
	
	 @RequestMapping("/disponibles/{idAula}") //estudiantes disponibles para matricularse en el aula idAula
		public List<ProEstudiantePorMatricularDto> listaAlumnosDisponibles(@PathVariable Integer idAula) {
			return estudianteService.listaAlumnosDisponibles(idAula).stream().sorted((c1,c2)-> c2.getIdEstudiante().compareTo(c1.getIdEstudiante())).collect(Collectors.toList()); 
		}
}