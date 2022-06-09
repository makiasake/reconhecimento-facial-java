package com.makiasake.reconhecimento_facial;

import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

import java.io.File;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;

public class Teste {

	public void run(FaceRecognizer reconhecedor, String classificatorsPath, String testImagesPath) {
		int totalAcertos = 0;
		double percentualAcerto = 0;
		double totalConfianca = 0;

		reconhecedor.read(classificatorsPath);

		File diretorio = new File(testImagesPath);
		File[] arquivos = diretorio.listFiles();

		for (File imagem : arquivos) {
			Mat foto = imread(imagem.getAbsolutePath(), IMREAD_GRAYSCALE);
			int classe = Integer.parseInt(imagem.getName().substring(7, 9));
			resize(foto, foto, new Size(160, 160));

			IntPointer rotulo = new IntPointer(1);
			DoublePointer confianca = new DoublePointer(1);
			reconhecedor.predict(foto, rotulo, confianca);
			int predicao = rotulo.get(0);
			System.out.println(classe + " foi reconhecido como " + predicao + " - " + confianca.get(0));
			if (classe == predicao) {
				totalAcertos++;
				totalConfianca += confianca.get(0);
			}
		}

		percentualAcerto = (totalAcertos / 30.0) * 100;
		totalConfianca = totalConfianca / totalAcertos;
		System.out.println("Percentual de acerto: " + percentualAcerto);
		System.out.println("Total confiança: " + totalConfianca);
	}
}
