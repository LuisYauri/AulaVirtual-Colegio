package pe.edu.savbackend.service.evaluacion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import pe.edu.savbackend.dao.AlternativaDao;
import pe.edu.savbackend.dao.EstudianteEvaluacionDao;
import pe.edu.savbackend.dao.EvaluacionDao;
import pe.edu.savbackend.dao.EvaluacionDetalleDao;
import pe.edu.savbackend.dao.HistorialDao;
import pe.edu.savbackend.dao.PreguntaDao;
import pe.edu.savbackend.domain.tarea.EstadisticaDto;
import pe.edu.savbackend.domain.tarea.ExamenDto;
import pe.edu.savbackend.domain.tarea.PreguntaDto;
import pe.edu.savbackend.domain.tarea.TareaDto;
import pe.edu.savbackend.domain.tarea.TipoResultadoDto;
import pe.edu.savbackend.entity.Evaluacion;
import pe.edu.savbackend.entity.Historial;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

	@Autowired
	private EvaluacionDao evaluacionDao;
	@Autowired
	private EvaluacionDetalleDao evaluacionDetalleDao;
	@Autowired
	private PreguntaDao preguntaDao;	
	@Autowired
	private AlternativaDao alternativaDao;	
	@Autowired
	private HistorialDao historialDao;	
	@Autowired
	private EstudianteEvaluacionDao estudianteEvaluacionDao;	
	
	Gson gson = new Gson();

	@Override
	public List<TareaDto> getLsTareas(Integer idEstudiante) {
		//contar las tareas formatear la fecha 
		List<TareaDto> lsTarea = evaluacionDao.getLsTareas(idEstudiante);
		lsTarea.forEach(e->{
			LocalDateTime ldt = evaluacionDao.getOne(e.getIdTarea()).getFechaInicio();
			e.setCantidadPreguntas(evaluacionDetalleDao.cantidadPregunta(e.getIdTarea()).toString());
			e.setFechaLimite((ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
			e.setTiempoLimite((ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
		});
		return lsTarea;
	}

	@Override
	public List<ExamenDto> getLsExamenes(Integer idEstudiante) {
		//contar las tareas formatear la fecha 
		List<ExamenDto> lsExamenes = evaluacionDao.getLsExamenes(idEstudiante);
		lsExamenes.forEach(e->{
			LocalDateTime ldt = evaluacionDao.getOne(e.getIdExamen()).getFechaInicio();
			e.setCantidadPreguntas(evaluacionDetalleDao.cantidadPregunta(e.getIdExamen()).toString());

			e.setFechaInico((ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
			e.setHoraInicio((ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
		});
		return lsExamenes;
	}
	
	@Override
	public TareaDto getPreguntasPorTarea(Integer idTarea) {
		Evaluacion tarea = evaluacionDao.getOne(idTarea);
		TareaDto tareaDto = new TareaDto(tarea.getIdEvaluacion(), tarea.getTitulo());
		List<PreguntaDto> lsPreguntas = preguntaDao.obtenerPreguntasPorIdTarea(idTarea);
		
		lsPreguntas.forEach(preg -> {
			List<String> lsAlternativas = alternativaDao.obtenerAlternativasPorIdPregunta(preg.getIdPregunta())
			.stream()
			.map(e->e.getDescripcion().toString()).collect(Collectors.toList());
			lsAlternativas.add(preg.getRespuestaCorrecta());
			Collections.shuffle(lsAlternativas);
			preg.setAlternativas(
					lsAlternativas.toArray(new String[lsAlternativas.size()])
				);
			
		});
		tareaDto.setLsPreguntas(lsPreguntas);
		return tareaDto;
	}
	
	//vabriables auxiliares para calcular las estadisticas
	private Integer correcto, incorrecto, vacio;
	@Override
	public EstadisticaDto finalizarTarea(TareaDto tareaDto) {
		correcto = 0; incorrecto = 0; vacio = 0;
		tareaDto.setFechaSolucion(LocalDateTime.now(ZoneId.of("America/Lima")));
		tareaDto.setCantidadPreguntas(tareaDto.getLsPreguntas().size() + "");
		
		tareaDto.getLsPreguntas().forEach(pregunta->{
			String rptaCorrecta = preguntaDao.getOne(pregunta.getIdPregunta()).getRespuestaCorrecta();
			if(pregunta.getRespuestaEstudiante() == null){
				vacio++;
			} else if (pregunta.getRespuestaEstudiante().equals(rptaCorrecta)) {
				correcto++;
			} else if(!pregunta.getRespuestaEstudiante().equals(rptaCorrecta)) {
				incorrecto++;
			}
			pregunta.setRespuestaCorrecta(rptaCorrecta);
		});
		
		Integer total = correcto+ incorrecto + vacio;
		List<TipoResultadoDto> lsTipoResultado =Arrays.asList(
				 TipoResultadoDto.builder().tipo("Correcto").porcentaje((int)Math.round(correcto*100.0/total)).cantidad(correcto).build()
				,TipoResultadoDto.builder().tipo("Incorrecto").porcentaje((int)Math.round(incorrecto*100.0/total)).cantidad(incorrecto).build()
				,TipoResultadoDto.builder().tipo("Sin responder").porcentaje((int)Math.round(vacio*100.0/total)).cantidad(vacio).build());
		
		EstadisticaDto estadistica = new EstadisticaDto();
		if(lsTipoResultado.get(0).getPorcentaje()>60){
			estadistica.setNota("A");
		} else if(lsTipoResultado.get(0).getPorcentaje()>40) {
			estadistica.setNota("B");
		} else {
			estadistica.setNota("C");
		}
		estadistica.setLsTiposResultados(lsTipoResultado);
		estadistica.setTotalPreguntas(tareaDto.getCantidadPreguntas());
		estadistica.setIdEstudiante(tareaDto.getIdEstudiante());
		estadistica.setIdEvaluacion(tareaDto.getIdTarea());
		estadistica.setTipo("T");
		
		Historial historial = new Historial();
		historial.setIdEstudiante(tareaDto.getIdEstudiante());
		historial.setIdEvaluacion(tareaDto.getIdTarea());
		historial.setJsonEstadistica(gson.toJson(estadistica));
		historial.setJsonCompleto(gson.toJson(tareaDto));
		historial.setFechaFinalizacion(tareaDto.getFechaSolucion());
		historial.setTipoEvaluacion("T");
		System.out.println(historial);
		historialDao.save(historial);
		return estadistica;
	}

	

}
