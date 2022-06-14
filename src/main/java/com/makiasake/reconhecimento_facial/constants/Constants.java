package com.makiasake.reconhecimento_facial.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

	public static final Path DEFAULT_PICTURES_PATH = Paths.get("pictures");
	public static final String DEFAULT_FRONTAL_FACE_CLASSIFIER = "resources/haarcascade_frontalface_alt.xml";
	public static final String DEFAULT_CAPTURE_CANVAS_FRAME = "Preview";
}
