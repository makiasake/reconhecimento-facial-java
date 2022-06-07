package com.makiasake.reconhecimento_facial;

import static org.junit.Assert.assertTrue;

import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
	public void createEigenFaceRecognizer() {
		FaceRecognizer eigenFaceRecognizer = EigenFaceRecognizer.create();
	}

	@Test
	public void testCaptura() {
		Captura captura = new Captura();
		try {
//			captura.capture();	
		} catch (Exception e) {
			System.out.println("Erro");
		}		
	}
	
	@Test
	public void testTreinamento() {
		Treinamento treinamento = new Treinamento();
		try {
			treinamento.run();
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}
}
