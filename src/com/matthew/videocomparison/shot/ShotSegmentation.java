package com.matthew.videocomparison.shot;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import java.util.ArrayList;

/**
 * <p>Directs separation of videos into shots</p>
 *
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 5/29/2016
 */
public class ShotSegmentation {
	public static int frameNumber = 1;

	private String fileName;
	ArrayList<ColorHistogram> histogramList = new ArrayList<ColorHistogram>();

	public ShotSegmentation(String fileName) {
		this.fileName = fileName;
	}

	public void AnalyzeVideo() {
		VideoCapture vid = new VideoCapture(fileName);

		if (vid.isOpened()) {
			Mat frame = new Mat();

			System.out.println("Began reading frames");
			for (int i = 0; i < vid.get(7); i++) { // 7 gives number of frames in video
				vid.read(frame);
				ColorHistogram ch = new ColorHistogram(frame);
				ch.CreateColorHistogram();
				histogramList.add(ch);
			}
			System.out.println("Ended reading frames");

			histogramList.forEach(
					ch -> {
						if (frameNumber < vid.get(7)) histogramList.get(frameNumber).CompareHistogram(ch, .8);
						frameNumber++;
					});
		} else {
			System.out.println("Video failed to open");
		}
	}
}
