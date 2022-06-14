package com.makiasake.reconhecimento_facial;

import java.nio.file.Paths;
import java.util.Arrays;

import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FisherFaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.junit.Ignore;
import org.junit.Test;

import com.makiasake.reconhecimento_facial.auxilary_objects.RecognizerConfiguration;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testCaptura() {
		Capture captura = new Capture();
		try {
			captura.run();	
		} catch (Exception e) {
			System.out.println("Erro");
		}		
	}
	
	@Test
	@Ignore
	public void testTreinamento() {
		
		// yalefaces
		// FaceRecognizer eigenface = EigenFaceRecognizer.create(30, 0);
		// FaceRecognizer fisherface = FisherFaceRecognizer.create(30, 0);
		// FaceRecognizer lbph = LBPHFaceRecognizer.create(12, 10, 15, 15, 0);
		
		RecognizerConfiguration eugen = new RecognizerConfiguration(EigenFaceRecognizer.create(),
				Paths.get("resources/classifiers/classificadorEigenFaces.yml"));
		RecognizerConfiguration fisher = new RecognizerConfiguration(FisherFaceRecognizer.create(),
				Paths.get("resources/classifiers/classificadorFisherFaces.yml"));
		RecognizerConfiguration lbph = new RecognizerConfiguration(LBPHFaceRecognizer.create(2, 9, 9, 9, 1),
				Paths.get("resources/classifiers/classificadorLBPH.yml"));
		
		Training treinamento = new Training(Arrays.asList(eugen, fisher, lbph), Paths.get("fotos"));
		try {
			treinamento.run();
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testReconhecimentoEigen() {
		Reconhecimento reconhecimento = new Reconhecimento();
		try {
			reconhecimento.run(EigenFaceRecognizer.create(), "resources/classifiers/classificadorEigenFaces.yml");
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testReconhecimentoFisher() {
		Reconhecimento reconhecimento = new Reconhecimento();
		try {
			reconhecimento.run(FisherFaceRecognizer.create(), "resources/classifiers/classificadorFisherFaces.yml");
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testReconhecimentoLBPH() {
		Reconhecimento reconhecimento = new Reconhecimento();
		try {
			reconhecimento.run(LBPHFaceRecognizer.create(), "resources/classifiers/classificadorLBPH.yml");
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testRecognizer() {
		Teste teste = new Teste();
		try {
			teste.run(LBPHFaceRecognizer.create(), "resources/classifiers/classificadorLBPH.yml", "teste");
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}
}
