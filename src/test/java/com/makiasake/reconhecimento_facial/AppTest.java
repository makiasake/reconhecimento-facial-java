package com.makiasake.reconhecimento_facial;

import static org.junit.Assert.assertTrue;

import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	@Ignore
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
	@Ignore
	public void createEigenFaceRecognizer() {
		FaceRecognizer eigenFaceRecognizer = EigenFaceRecognizer.create();
	}

	@Test
	@Ignore
	public void testCaptura() {
		Captura captura = new Captura();
		try {
			captura.capture();	
		} catch (Exception e) {
			System.out.println("Erro");
		}		
	}
	
	@Test
	@Ignore
	public void testTreinamento() {
		Treinamento treinamento = new Treinamento();
		try {
			treinamento.run();
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReconhecimento() {
		Reconhecimento reconhecimento = new Reconhecimento();
		try {
			reconhecimento.run();
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}
}
