package pe.edu.savbackend.domain;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contenido {
	
	private Integer idContenido;
	private Integer nombre; 
	private String urlImagen;
	
	//se utiliza solo para contenidoController @RequestMapping("?idCriterio={idCriterio}&codigoGrado={codigoGrado}")
	private Integer idCriterio;
}
