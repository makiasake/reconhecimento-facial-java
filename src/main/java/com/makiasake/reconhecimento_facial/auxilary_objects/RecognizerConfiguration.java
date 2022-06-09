package com.makiasake.reconhecimento_facial.auxilary_objects;

import java.nio.file.Path;

import org.bytedeco.opencv.opencv_face.FaceRecognizer;

public class RecognizerConfiguration {

	private FaceRecognizer faceRecognizer;
	private Path classificatorYml;
	
	public RecognizerConfiguration() {}

	public RecognizerConfiguration(FaceRecognizer faceRecognizer, Path classificatorYml) {
		super();
		this.faceRecognizer = faceRecognizer;
		this.classificatorYml = classificatorYml;
	}

	public FaceRecognizer getFaceRecognizer() {
		return faceRecognizer;
	}

	public void setFaceRecognizer(FaceRecognizer faceRecognizer) {
		this.faceRecognizer = faceRecognizer;
	}

	public Path getClassificatorYml() {
		return classificatorYml;
	}

	public void setClassificatorYml(Path classificatorYml) {
		this.classificatorYml = classificatorYml;
	}
	
}
