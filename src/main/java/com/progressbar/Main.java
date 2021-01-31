package com.progressbar;

import java.sql.Time;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * main class.
 */
public class Main {

  /**
   * main method.

   * @param args input arguments
   */
  public static void main(String[] args) {
    String[] arguments = new String[args.length];
    long startTime = System.currentTimeMillis();
    int delay = Integer.parseInt(args[1]) * 1000;
    int numberOfFiles = Integer.parseInt(args[0]);

    for (int i = 1; i <= numberOfFiles; i++) {
      try {
        Thread.sleep(delay);
        printBar(startTime, numberOfFiles, i);
      } catch (InterruptedException e) {
      }
    }
    System.out.println();
  }

  /**
   * method bulid string with progress.

   * @param startTime start time
   * @param numberOfFiles number of files
   * @param currentFile current file
   * @return string with progress
   */
  private static StringBuilder buildString(long startTime, int numberOfFiles, int currentFile) {
    StringBuilder progress = new StringBuilder();
    int percentCount = currentFile * 100 / numberOfFiles;
    progress
        .append('\r')
        .append(String.format("%d%%", percentCount))
        .append("[")
        .append(String.join("", Collections.nCopies(percentCount, "=")))
        .append(">")
        .append(String.join("", Collections.nCopies(100 - percentCount, " ")))
        .append("]")
        .append(String.format(" %d/%d", currentFile, numberOfFiles))
        .append(String.format(" ETA: %s", estimatedTime(startTime, numberOfFiles, currentFile)));

    return progress;
  }

  /**
   * method print progressbar.

   * @param startTime start time
   * @param numberOfFiles number of files
   * @param currentFile current file
   */
  private static void printBar(long startTime, int numberOfFiles, int currentFile) {
    System.out.print(buildString(startTime, numberOfFiles, currentFile));
  }

  /**
   * method count estimated time.

   * @param startTime start time
   * @param numberOfFiles number of files
   * @param currentFile current file
   * @return estimated time
   */
  private static String estimatedTime(long startTime, int numberOfFiles, int currentFile) {
    long estimTime;
    String timeHms;

    if (currentFile == 0) {
      estimTime = 0;
    } else {
      estimTime = (numberOfFiles - currentFile)
          * (System.currentTimeMillis() - startTime) / currentFile;
    }

    if (currentFile == 0) {
      timeHms = "N/A";
    } else {
      timeHms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(estimTime),
          TimeUnit.MILLISECONDS.toMinutes(estimTime) % TimeUnit.HOURS.toMinutes(1),
          TimeUnit.MILLISECONDS.toSeconds(estimTime) % TimeUnit.MINUTES.toSeconds(1));
    }

    return timeHms;
  }

  private static int takeRandom(int[] target) {
    int index = new Random().nextInt(target.length);
    return target[index];
  }

}
