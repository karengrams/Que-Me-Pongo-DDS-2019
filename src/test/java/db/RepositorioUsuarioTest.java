package db;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import domain.enums.*;
import domain.frecuenciasDeEventos.FrecuenciaDiaria;
import domain.frecuenciasDeEventos.FrecuenciaUnicaVez;
import domain.*;
import javax.persistence.EntityManager;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class RepositorioUsuarioTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
	EntityManager em = entityManager();
	Usuario bruno = new Usuario(TipoUsuario.PREMIUM, 25,"buccaratti","123");
	Usuario giorno = new Usuario(TipoUsuario.GRATUITO, 15,"giovanna","123");
	Guardarropa soyElGuarda = new Guardarropa();
	RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
	Evento evento = new Evento(new FrecuenciaDiaria(0), "Trabajo");

	
	@Before
	public void setUp(){
		giorno.agendarEvento(evento);
		bruno.agregarGuardarropa(soyElGuarda);
		withTransaction(()->{ 
		repo.agregar(bruno);
			});
		withTransaction(()->{ 
			repo.agregar(giorno);
				});
	}
	@After
	public void borrarUsers() {
		withTransaction(()->{ 
			repo.borrarUsuario(bruno.getId());
				});
			withTransaction(()->{ 
				repo.borrarUsuario(giorno.getId());			
				});
	}
	@Test
	public void testUsuario() throws Exception {
		repo = RepositorioDeUsuarios.getInstance();
		
		HashSet<Usuario> listaUsuariosPersistidos;
		listaUsuariosPersistidos = (HashSet<Usuario>) repo.usuarios();
		int cantidadUsuariosPers = listaUsuariosPersistidos.size();
		assertEquals(2,cantidadUsuariosPers);
	}

	@Test
	public void buscarPorNombre(){
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		Usuario juan = repo.buscarPorNombre("buccaratti");
		assertEquals(bruno.getId(),juan.getId() );
	}

}
