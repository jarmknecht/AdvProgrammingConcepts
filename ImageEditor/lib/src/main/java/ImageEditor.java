import java.io.*;
import java.lang.*;
import java.util.Scanner;

/**
 * Created by Jonathan on 1/12/18.
 */

public class ImageEditor {

    private void InvertPhoto(Pixel[][] photo, int w, int h, int maxc) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Pixel p = photo[j][i];
                p.setRedValue(maxc - p.getRedValue());
                p.setGreenValue(maxc - p.getGreenValue());
                p.setBlueValue(maxc - p.getBlueValue());
            }
        }
    }

    private void GrayscalePhoto(Pixel[][] photo, int w, int h) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Pixel p = photo[j][i];
                int averageNum = ((p.getRedValue() + p.getGreenValue() + p.getBlueValue()) / 3);
                p.setRedValue(averageNum);
                p.setGreenValue(averageNum);
                p.setBlueValue(averageNum);
            }
        }
    }

    private void EmbossPhoto(Pixel[][] photo, int w, int h) { //not working
        for (int i = (h - 1); i >= 0; i--) {
            for (int j = (w - 1); j >= 0; j--) {
                Pixel p = photo[j][i];
                int maxDifference = 0;
                int v = 0;

                if (j > 0 && i > 0) {
                    Pixel p2 = photo[j - 1][i - 1];

                    int redDif = p.getRedValue() - p2.getRedValue();
                    int greenDif = p.getGreenValue() - p2.getGreenValue();
                    int blueDif = p.getBlueValue() - p2.getBlueValue();

                    int rabs = Math.abs(redDif);
                    int gabs = Math.abs(greenDif);
                    int babs = Math.abs(blueDif);

                    if (rabs >= gabs && rabs >= babs) {
                        maxDifference = redDif;
                    }
                    else if (gabs >= babs) {
                        maxDifference = greenDif;
                    }
                    else {
                        maxDifference = blueDif;
                    }
                }
                v = 128 + maxDifference;

                if (v < 0) {
                    v = 0;
                }
                if (v > 255) {
                    v = 255;
                }
                p.setRedValue(v);
                p.setGreenValue(v);
                p.setBlueValue(v);
            }
        }
    }

    private void MotionblurPhoto(Pixel[][] photo, int w, int h, int blurPower) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Pixel p = photo[j][i];

                int avgRed = 0;
                int avgGreen = 0;
                int avgBlue = 0;
                int count = 0;

                for (int k = 0; k < blurPower; k++) {
                    if (j + k < w) {
                        Pixel p2 = photo[(j + k)][i];
                        avgRed += p2.getRedValue();
                        avgGreen += p2.getGreenValue();
                        avgBlue += p2.getBlueValue();
                        count++;
                    }
                }
                avgRed = (avgRed / count);
                avgGreen = (avgGreen / count);
                avgBlue = (avgBlue / count);

                p.setRedValue(avgRed);
                p.setGreenValue(avgGreen);
                p.setBlueValue(avgBlue);
            }
        }
    }

    public static void main (String[] args) {

        int blurPower = -1;
        int transitionType = 0;

        ImageEditor ie = new ImageEditor();

        if (args.length < 3 || args.length > 4) {
            System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length) \n");
            System.exit(-1);
        }

        String transition = args[2];

        if (transition.equals("invert")) {
            //System.out.println("invert choice");
            transitionType = 1;
        }
        else if (transition.equals("grayscale")) {
            //System.out.println("grayscale choice");
            transitionType = 2;
        }
        else if (transition.equals("emboss")) {
            //System.out.println("emboss choice");
            transitionType = 3;
        }
        else if (transition.equals("motionblur")) {
           // System.out.println("motionblur");
            transitionType= 4;
            if (args.length != 4) {
                System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length) \n");
                System.exit(-1);
            }

            Scanner sc = new Scanner(args[3]);

            if (!sc.hasNextInt()) { //if 4 argument is not a number
                System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length) \n");
                System.exit(-1);
            }
            blurPower = sc.nextInt();

            //System.out.println("Blur by " + blurPower);

            sc.close();

        }
        else {
            System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length) \n");
            System.exit(-1);
        }

        try {

            Scanner sc = new Scanner(new File(args[0]));
            sc.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

            String p3 = sc.next();

            int width = sc.nextInt();
            int height = sc.nextInt();
            int maxColorVal = sc.nextInt();

            Pixel[][] photo = new Pixel[width][height];
            //Pixel[][] copyPhoto = new Pixel[width][height];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int rValue = sc.nextInt();
                    int gValue = sc.nextInt();
                    int bValue = sc.nextInt();

                    Pixel p = new Pixel(rValue, gValue, bValue);

                    photo[j][i] = p;

                    //copyPhoto[i][j] = new Pixel(rValue, gValue, bValue);
                }
            }
            sc.close();

            switch (transitionType) {
                case 1:
                    ie.InvertPhoto(photo, width, height, maxColorVal);
                    break;
                case 2:
                    ie.GrayscalePhoto(photo, width, height);
                    break;
                case 3:
                    ie.EmbossPhoto(photo, width, height);
                    break;
                case 4:
                    ie.MotionblurPhoto(photo, width, height, blurPower);
                    break;
                default:
                    System.out.println("Transition Not Set");
                    break;
            }

            StringBuilder s = new StringBuilder();

            s.append(p3 + "\n");
            s.append(width + " " + height + "\n");
            s.append(maxColorVal + "\n");

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Pixel p = photo[j][i];
                    s.append(p.getRedValue() + "\n");
                    s.append(p.getGreenValue() + "\n");
                    s.append(p.getBlueValue() + "\n");
                }
            }
            PrintWriter pw = new PrintWriter(new File(args[1]));
            pw.print(s);
            pw.close();
        }

        catch (FileNotFoundException e) {
            //nothing will be caught System.exit(-1)
        }
    }
}
