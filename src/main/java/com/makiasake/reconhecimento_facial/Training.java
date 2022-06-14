package com.makiasake.reconhecimento_facial;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Size;

import com.makiasake.reconhecimento_facial.auxilary_objects.RecognizerConfiguration;
import com.makiasake.reconhecimento_facial.util.FileUtil;

public class Training {

	List<RecognizerConfiguration> recognizersToTrain = new ArrayList<>();
	Path picturesDirectory;

	public Training(List<RecognizerConfiguration> recognizersToTrain, Path picturesDirectory) {
		this.recognizersToTrain = recognizersToTrain;
		this.picturesDirectory = picturesDirectory;
	}

	public void run() throws Exception {

		List<File> pictures = Arrays.asList(this.picturesDirectory.toFile().listFiles(FileUtil.imageFilterJpgGifPng));

		if (pictures == null || pictures.isEmpty())
			throw new FileNotFoundException(
					"No pictures found on informed folder:" + this.picturesDirectory.toString());

		MatVector picturesVector = new MatVector(pictures.size());
		Mat labels = new Mat(pictures.size(), 1, CV_32SC1);
		IntBuffer labelsBuffer = labels.createBuffer();

		int counter = 0;
		for (File picture : pictures) {
			Mat pictureGrayScale = imread(picture.getAbsolutePath(), IMREAD_GRAYSCALE);

			// Please find a better name that is not class
			int kind = Integer.parseInt(picture.getName().split("\\.")[1]); // because the filename is:
																			// person.id.imagenumber.jpg. E.g:
																			// person.1.1.jpg

			resize(pictureGrayScale, pictureGrayScale, new Size(160, 160));
			picturesVector.put(counter, pictureGrayScale);
			labelsBuffer.put(counter, kind);
			counter++;
		}

		recognizersToTrain.forEach(recognizer -> {
			recognizer.getFaceRecognizer().train(picturesVector, labels);
			recognizer.getFaceRecognizer().save(recognizer.getClassificatorYml().toFile().getAbsolutePath());
		});
	}

}
