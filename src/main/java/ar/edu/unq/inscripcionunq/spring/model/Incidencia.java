package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;

@Entity(name = "Incidencia")
public class Incidencia extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@OneToOne(fetch = FetchType.LAZY)
	private TipoIncidencia tipoIncidencia;
	@Column(length = 1000)
	private String descripcion;
	private String emailDelReportante;
	private String fechaDeCreacion;

	private TipoEstadoIncidencia tipoEstadoIncidencia;

	public Incidencia() {
	
	}

	public Incidencia(TipoIncidencia tipoIncidencia, String descripcion, String emailDelReportante) {
		this.tipoIncidencia = tipoIncidencia;
		this.descripcion = descripcion;
		this.emailDelReportante = emailDelReportante;
		this.tipoEstadoIncidencia = TipoEstadoIncidencia.ABIERTA;
		LocalDateTime fechaActual = LocalDateTime.now();
		this.fechaDeCreacion = 	fechaActual.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
	} 

	public TipoIncidencia getTipoIncidencia() {
		return this.tipoIncidencia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void cambiarEstadoIncidencia(TipoEstadoIncidencia tipoEstadoIncidencia) {
		this.tipoEstadoIncidencia = tipoEstadoIncidencia;
	}

	public TipoEstadoIncidencia getTipoEstadoIncidencia() {
		return tipoEstadoIncidencia;
	}

	public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	public void setTipoEstadoIncidencia(TipoEstadoIncidencia tipoEstadoIncidencia) {
		this.tipoEstadoIncidencia = tipoEstadoIncidencia;
	}
	
	public String getEmailDelReportante() {
		return emailDelReportante;
	}

	public void setEmailDelReportante(String emailDelReportante) {
		this.emailDelReportante = emailDelReportante;
	}	
	
	public String getFechaDeCreacion() {
		return fechaDeCreacion;
	}
}