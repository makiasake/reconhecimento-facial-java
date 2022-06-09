package com.makiasake.reconhecimento_facial;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import static org.bytedeco.opencv.global.opencv_core.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

public class Treinamento {

	public void run() {
		File diretorio = new File("fotos");
		FilenameFilter filtroImagem = new FilenameFilter() {

			@Override
			public boolean accept(File arg0, String nome) {
				return nome.endsWith(".jpg") || nome.endsWith(".gif") || nome.endsWith(".png");
			}
		};

		File[] arquivos = diretorio.listFiles(filtroImagem);
		MatVector fotos = new MatVector(arquivos.length);
		Mat rotulos = new Mat(arquivos.length, 1, CV_32SC1);
		IntBuffer rotulosBuffer = rotulos.createBuffer();

		int contador = 0;
		for (File imagem : arquivos) {
			Mat foto = imread(imagem.getAbsolutePath(), IMREAD_GRAYSCALE);
			int classe = Integer.parseInt(imagem.getName().split("\\.")[1]);
//			int classe = Integer.parseInt(imagem.getName().substring(7,9)); yalefaces
//			System.out.println(classe);

			resize(foto, foto, new Size(160, 160));
			fotos.put(contador, foto);
			rotulosBuffer.put(contador, classe);
			contador++;
		}

		FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
		FaceRecognizer fisherfaces = FisherFaceRecognizer.create();
		FaceRecognizer lbph = LBPHFaceRecognizer.create(2, 9, 9, 9, 1);

		// yalefaces
//		FaceRecognizer eigenface = EigenFaceRecognizer.create(30, 0);            // antes: createEigenFaceRecognizer();
//      FaceRecognizer fisherface = FisherFaceRecognizer.create(30, 0);          // antes: createFisherFaceRecognizer();
//      FaceRecognizer lbph = LBPHFaceRecognizer.create(12, 10, 15, 15, 0);      // antes: createLBPHFaceRecognizer();

		eigenfaces.train(fotos, rotulos);
		eigenfaces.save("recursos/classificadorEigenFaces.yml");
		fisherfaces.train(fotos, rotulos);
		fisherfaces.save("recursos/classificadorFisherFaces.yml");
		lbph.train(fotos, rotulos);
		lbph.save("recursos/classificadorLBPH.yml");

	}

}
