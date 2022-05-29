package com.makiasake.reconhecimento_facial;

import static org.junit.Assert.assertTrue;

import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.junit.Test;

import com.makiasake.reconhecimento_facial.reconhecimento.Captura;
import org.bytedeco.javacv.FrameGrabber.Exception;

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
			captura.capture();	
		} catch (Exception e) {
			System.out.println("Erro");
		}
		
	}
}
