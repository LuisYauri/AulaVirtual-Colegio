package pe.edu.savbackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Docente
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "docentes")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Docente {
    @Id
    private Integer id;
	@Column(name="id_personas")
	private Integer id_persona;
    @Column(name="username")
    private String username;
}