package com.makiasake.reconhecimento_facial;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

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

import com.makiasake.reconhecimento_facial.constants.Constants;
import com.makiasake.reconhecimento_facial.util.FileUtil;

public class Capture {

	private Integer personId;
	private Integer maxSamples = 25;
	private Path picturesDirectory = Constants.DEFAULT_PICTURES_PATH;
	private String frontalFaceClassifier = Constants.DEFAULT_FRONTAL_FACE_CLASSIFIER;
	private KeyEvent key = null;

	public Capture() {
	}

	public Capture(Integer personId, Integer maxSamples, Path picturesDirectory, String frontalFaceClassifier) {
		this.personId = personId;

		if (Objects.nonNull(maxSamples) && maxSamples > 0)
			this.maxSamples = maxSamples;

		if (Objects.isNull(picturesDirectory))
			this.picturesDirectory = picturesDirectory;

		if (Objects.isNull(frontalFaceClassifier))
			this.frontalFaceClassifier = frontalFaceClassifier;
	}

	public void run() throws Exception {
		try {
			this.queuePersonIdIfNotInformed();
			this.checkIfPersonIdAlreadyExists();
			this.capture();
		} catch (Exception e) {
			System.out.println("Error on capture module: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private void capture() throws Exception {

		OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
		CascadeClassifier frontalFaceDetectorClassifier = new CascadeClassifier(this.frontalFaceClassifier);
		CanvasFrame canvas = new CanvasFrame(Constants.DEFAULT_CAPTURE_CANVAS_FRAME,
				CanvasFrame.getDefaultGamma() / camera.getGamma());
		OpenCVFrameConverter.ToMat convertedMat = new OpenCVFrameConverter.ToMat();
		RectVector detectedFaces = new RectVector();
		Frame capturedFrame = null;

		Mat grayFrame = new Mat();
		Mat coloredFrame = new Mat();

		Integer currentSample = 1;

		try {
			camera.start();

			while ((capturedFrame = camera.grab()) != null) {

				coloredFrame = convertedMat.convert(capturedFrame);
				cvtColor(coloredFrame, grayFrame, COLOR_BGRA2GRAY);

				frontalFaceDetectorClassifier.detectMultiScale(grayFrame, detectedFaces, 1.1, 1, 0, new Size(150, 150),
						new Size(500, 500));

				this.getKetFromCanvas(canvas, 5);

				for (int i = 0; i < detectedFaces.size(); i++) {
					Rect faceData = detectedFaces.get(0);
					rectangle(coloredFrame, faceData, new Scalar(0, 0, 255, 0));

					Mat detectedFace = new Mat(grayFrame, faceData);
					resize(detectedFace, detectedFace, new Size(160, 160));

					this.getKetFromCanvas(canvas, 5);

					if (this.key != null) {
						if (this.key.getKeyChar() == 'q') {
							if (currentSample <= maxSamples) {
								imwrite("pictures/person." + this.personId + "." + currentSample + ".jpg",
										detectedFace);
								System.out.println("Picture " + currentSample + " taken!\n");
								currentSample++;
							}
						}
						this.key = null;
					}
				}

				this.getKetFromCanvas(canvas, 20);

				if (canvas.isVisible())
					canvas.showImage(capturedFrame);

				if (currentSample > maxSamples)
					break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			canvas.dispose();
			camera.stop();
			camera.close();
			frontalFaceDetectorClassifier.close();
			convertedMat.close();
			coloredFrame.close();
		}
	}

	private void queuePersonIdIfNotInformed() {

		if (Objects.isNull(personId)) {
			System.out.println("Type in your id: ");
			Scanner scanner = new Scanner(System.in);
			this.personId = scanner.nextInt();
			scanner.close();
		}
	}

	private void checkIfPersonIdAlreadyExists() throws Exception {
		List<File> pictures = Arrays.asList(this.picturesDirectory.toFile().listFiles(FileUtil.imageFilterJpgGifPng));

		if (Objects.isNull(pictures) || pictures.isEmpty())
			return;

		Optional<File> picture = pictures.stream()
				.filter(element -> Integer.parseInt(element.getName().split("\\.")[1]) == this.personId).findFirst();

		if (picture.isPresent())
			throw new Exception("Person's pictures already taken");
	}

	private void getKetFromCanvas(CanvasFrame canvas, int wait) throws InterruptedException {
		if (Objects.isNull(this.key)) {
			this.key = canvas.waitKey(wait);
		}
	}
}
