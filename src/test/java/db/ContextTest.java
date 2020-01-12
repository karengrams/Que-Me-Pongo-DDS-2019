package db;

import static org.junit.Assert.*;
import domain.*;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import javax.persistence.EntityManager;
public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
	Guardarropa diavolo = new Guardarropa();
	EntityManager em = entityManager();
	
	@Test
	public void nuevoTest() {
	}
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}

}
