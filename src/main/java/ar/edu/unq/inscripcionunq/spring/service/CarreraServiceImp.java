package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.dao.CarreraDao;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteCarreraConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class CarreraServiceImp extends GenericServiceImp<Carrera> implements CarreraService {

	@Autowired
	CarreraDao carreraDaoImp;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void agregarNuevaCarrera(CarreraJson carreraJson) throws DescripcionInvalidaException,
			CodigoInvalidoException, EstadoInvalidoException, ExisteCarreraConElMismoCodigoException {
		Carrera nuevaCarrera = this.armarCarreraDesdeJson(carreraJson);
		try {
			this.save(nuevaCarrera);
		} catch (ConstraintViolationException e) {
			throw new ExisteCarreraConElMismoCodigoException();
		}
	}

	private void validarSiExisteCarreraConElMismoCodigo(String codigo) throws ExisteCarreraConElMismoCodigoException {
		Carrera carrera = carreraDaoImp.encontrarCarreraConElMismoCodigo(codigo);
		if (carrera != null) {
			throw new ExisteCarreraConElMismoCodigoException();
		}
	}

	@Override
	public void actualizarCarrera(CarreraJson carreraJson) throws DescripcionInvalidaException, CodigoInvalidoException,
			EstadoInvalidoException, ExisteCarreraConElMismoCodigoException, CarreraNoExisteException {
		Carrera carreraRecibida = this.armarCarreraDesdeJson(carreraJson);
		try {
			Carrera carreraActual = this.get(carreraJson.id);
			this.actualizarInformacionDeLaCarrera(carreraActual, carreraRecibida);
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new CarreraNoExisteException();
		}
	}

	private Carrera armarCarreraDesdeJson(CarreraJson carreraJson)
			throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException {
		Carrera carrera = new Carrera(carreraJson.codigo, carreraJson.descripcion);
		TipoEstado estado = carreraJson.habilitada ? TipoEstado.ENABLED : TipoEstado.DISABLED;
		carrera.setEstado(estado);
		Validacion.validarCarrera(carrera);
		return carrera;
	}

	private void actualizarInformacionDeLaCarrera(Carrera carreraActual, Carrera carreraRecibida)
			throws ExisteCarreraConElMismoCodigoException {
		if (!carreraActual.getCodigo().equals(carreraRecibida.getCodigo())) {
			validarSiExisteCarreraConElMismoCodigo(carreraRecibida.getCodigo());
		}
		carreraActual.actualizarInformacion(carreraRecibida);
		this.save(carreraActual);
	}

	@Override
	public List<CarreraJson> getCarrerasJson() {
		List<Carrera> carreras = this.list();
		return carreras.stream().map(c -> new CarreraJson(c)).collect(Collectors.toList());
	}

	@Override
	public Carrera getCarreraPorCodigo(String codigo) {
		return carreraDaoImp.encontrarCarreraConElMismoCodigo(codigo);
	}
}