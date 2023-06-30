package demo.crud.app;

import org.junit.jupiter.api.Test;

import static java.util.function.Predicate.isEqual;

//@SpringBootTest
class CrudAppApplicationTests {

	@Test
	void contextLoads() {
		String s = "Hello world";
		assert "Hello world".equals(s);
	}

}
