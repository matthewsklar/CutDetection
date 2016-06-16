package com.matthew.videocomparison;

import com.matthew.videocomparison.shot.ShotSegmentation;
import org.opencv.core.*;

/**
 * Starts the program
 *
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 5/25/2016
 */
public class Main {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //opencv_java2413

		ShotSegmentation ss = new ShotSegmentation("util/shotsegmentationtest.mp4");

		ss.AnalyzeVideo();
	}
}