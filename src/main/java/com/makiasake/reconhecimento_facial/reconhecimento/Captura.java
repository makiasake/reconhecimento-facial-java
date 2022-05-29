package com.makiasake.reconhecimento_facial.reconhecimento;

import java.awt.event.KeyEvent;

import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Captura {
	
	public void capture() throws Exception {
		KeyEvent tecla = null;
		OpenCVFrameConverter.ToMat convertedMat = new OpenCVFrameConverter.ToMat();
		
		OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
		camera.start();
		
		CanvasFrame canvas = new CanvasFrame("Preview",
				CanvasFrame.getDefaultGamma() / camera.getGamma());
		
		Frame capturedFrame = null;
		while ((capturedFrame = camera.grab()) != null) { // while is recording
			if (canvas.isVisible()) {
				canvas.showImage(capturedFrame);
			}
		}
		
		canvas.dispose();
		camera.stop();
	}
}
