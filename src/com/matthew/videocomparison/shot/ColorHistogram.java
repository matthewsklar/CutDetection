package com.matthew.videocomparison.shot;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

/**
 * <p>Creation and handling of Color Histograms</p>
 *
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 5/29/2016
 */
public class ColorHistogram {
	Mat src, histImage;

	Mat colorHist;

	int histWidth, histHeight;
	long binWidth;

	/**
	 * Converts the image into it's equivalent matrix
	 *
	 * @param fileName Location of the image
	 */
	public ColorHistogram(String fileName) {
		src = Highgui.imread(fileName);
	}

	/**
	 * Keeps the matrix
	 *
	 * @param srcImg A matrix of an image
	 */
	public ColorHistogram(Mat srcImg) {
		src = srcImg;
	}

	/**
	 * Creates the equivalent color histogram
	 * for the matrix
	 */
	public void CreateColorHistogram() {
		Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV);

		ArrayList<Mat> plane = new ArrayList<>();
		plane.add(src);

		MatOfInt histSize = new MatOfInt(256);
		final MatOfFloat histRange = new MatOfFloat(0f, 256f);

		boolean accumulate = false;

		colorHist = new Mat();

		Imgproc.calcHist(plane, new MatOfInt(0), new Mat(), colorHist, histSize, histRange, accumulate);

		histWidth = 512;
		histHeight = 600;
		binWidth = Math.round((double) histWidth / 256);

		histImage = new Mat(histHeight, histWidth, CvType.CV_8UC3);

		Core.normalize(colorHist, colorHist, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
	}

	//1696 + 1774
	// TODO: test different comparison types and improve fade to recognition and possibly test with HSL or HSV
	/**
	 * Compares the histogram with another to see if they are
	 * from the same or different scenes
	 *
	 * @param ch ColorHistogram to compare to
	 * @param threshold difference allowed
	 * @return if the color histograms are from different scenes
	 */
	public boolean CompareHistogram(ColorHistogram ch, double threshold) { // May be 3 too little for some reason
		double tCorrelation = Imgproc.compareHist(colorHist, ch.colorHist, 0);
		double t1Correlation = Imgproc.compareHist(colorHist, ch.colorHist, 1);
		double t2Correlation = Imgproc.compareHist(colorHist, ch.colorHist, 2);
		double t3Correlation = Imgproc.compareHist(colorHist, ch.colorHist, 3);

		if (tCorrelation < .8 || t3Correlation > .3) {
			System.out.println(ShotSegmentation.frameNumber);
		}

		//System.out.println(ShotSegmentation.frameNumber + " 0: " + tCorrelation + " 1: " + t1Correlation + " 2: " + t2Correlation + " 3: " + t3Correlation);

		return false;
	}
}