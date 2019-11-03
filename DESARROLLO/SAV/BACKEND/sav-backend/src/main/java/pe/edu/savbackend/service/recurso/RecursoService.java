package pe.edu.savbackend.service.recurso;

import java.util.List;

import pe.edu.savbackend.domain.comentario.ComentarioResponse;
import pe.edu.savbackend.domain.comentario.RecursoDto;

public interface RecursoService {

	RecursoDto obtenerRecursoPorId(Integer idRecurso, Integer idEstudiante);
	List<ComentarioResponse> ob(Integer idRecurso);
	List<RecursoDto> filtrarPorIdSubcontenido(Integer idSubContenido);
}