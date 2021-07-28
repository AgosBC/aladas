package ar.com.ada.api.aladas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.services.VueloService;

@SpringBootTest
class AladasApplicationTests {

	@Autowired
	VueloService vueloService;

	@Test //nueva notacion
	void vueloTestPrecioNegativo(){

		Vuelo vueloConPrecioNegaivo = new Vuelo();
		vueloConPrecioNegaivo.setPrecio(new BigDecimal(-100));

		//assert: afirmar
		//assertFalse: afirmar que sea negativo (yo quiero que este de TRUE, que el valor es neg)

		assertFalse(vueloService.validarPrecio(vueloConPrecioNegaivo));

	}

	void vuelTestPrecioOk(){
		Vuelo vueloPrecioOk = new Vuelo();
		vueloPrecioOk.setPrecio(new BigDecimal(100));

		// afirmar quie sea verdadero: assertTrue
		assertTrue(vueloService.validarPrecio(vueloPrecioOk));
	}
}
