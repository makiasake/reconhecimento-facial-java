package com.makiasake.reconhecimento_facial.reconhecimento;

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

import java.awt.event.KeyEvent;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

public class Captura {

	public void capture() throws Exception {
		KeyEvent tecla = null;
		OpenCVFrameConverter.ToMat convertedMat = new OpenCVFrameConverter.ToMat();

		OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
		camera.start();

		CascadeClassifier detectorFace = new CascadeClassifier("haarcascade_frontalface_alt.xml");
		CanvasFrame canvas = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma() / camera.getGamma());

		Mat imagemColorida = new Mat();
		Frame capturedFrame = null;

		while ((capturedFrame = camera.grab()) != null) { // while is recording

			imagemColorida = convertedMat.convert(capturedFrame);
			Mat imagemCinza = new Mat();
			cvtColor(imagemColorida, imagemCinza, COLOR_BGRA2GRAY);

			RectVector facesDetectadas = new RectVector();
			
			try {
				detectorFace.detectMultiScale(imagemCinza, facesDetectadas, 1.1, 1, 0, new Size(150, 150),
						new Size(500, 500));
			} catch (Exception e) {
				System.out.println("error:" + e.getMessage());
			}

			for (int i = 0; i < facesDetectadas.size(); i++) {
				Rect dadosFace = facesDetectadas.get(0);
				rectangle(imagemColorida, dadosFace, new Scalar(0, 0, 255, 0));
			}

			if (canvas.isVisible()) {
				canvas.showImage(capturedFrame);
			}
		}

		canvas.dispose();
		camera.stop();
	}
}
