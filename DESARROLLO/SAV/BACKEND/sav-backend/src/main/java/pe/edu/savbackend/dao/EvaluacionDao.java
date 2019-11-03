package pe.edu.savbackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.edu.savbackend.domain.tarea.TareaDto;
import pe.edu.savbackend.entity.Evaluacion;

/**
 * EvaluacionDao
 */
public interface EvaluacionDao extends JpaRepository<Evaluacion, Integer>{

    //select b.fname, b.lname from Users b JOIN Groups c where c.groupName = :groupName
//    @Query(value = "select new pe.edu.savbackend.domain.tarea.Tarea(e.titulo, h.idEstudiante) from Evaluacion e join Historial h on e.id = h.idEvaluacion where h.idEstudiante = ?1")
//    List<Tarea> ejemplo(Integer idEstudiante);
    
	@Query(value = "select new pe.edu.savbackend.domain.tarea.TareaDto(e.id,e.titulo) from Evaluacion e join EstudianteEvaluacion ee on e.id = ee.idEvaluacion where ee.idEstudiante = ?1")
	List<TareaDto> getLsTareas(Integer idEstudiante);
	
	

}