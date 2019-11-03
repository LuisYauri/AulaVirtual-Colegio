package pe.edu.savbackend.domain.tarea;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tarea {
	private Integer idTarea;
	private String titulo;
	private String cantidadPreguntas;
	private String fechaLimite;
	private String tiempoLimite;
	private String contenido;

	private Date fechaSolucion;
	private Integer idEstudiante;
	private List<PreguntaDto> lsPreguntas;

	private EstadisticaDto estadistica;

	
	public Tarea (String titulo, Integer idEstudiante)
	{
		this.titulo = titulo;
		this.idEstudiante = idEstudiante;
	}
}
