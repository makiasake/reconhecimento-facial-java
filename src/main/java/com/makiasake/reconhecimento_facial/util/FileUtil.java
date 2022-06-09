package com.makiasake.reconhecimento_facial.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileUtil {

	public static FilenameFilter imageFilterJpgGifPng = new FilenameFilter() {
		@Override
		public boolean accept(File file, String name) {
			return name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".png");
		}
	};
}
