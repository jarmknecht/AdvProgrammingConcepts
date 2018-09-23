/**
 * Created by Jonathan on 1/12/18.
 */

public class Pixel {

    private int redValue;
    private int greenValue;
    private int blueValue;

    public Pixel() {
        this.redValue = 0;
        this.greenValue = 0;
        this.blueValue = 0;
    }
    public Pixel(int redNum, int greenNum, int blueNum) {
        this.redValue = redNum;
        this.greenValue = greenNum;
        this.blueValue = blueNum;
    }

    public int getRedValue() {
        return redValue;
    }

    public int getGreenValue() {
        return greenValue;
    }

    public int getBlueValue() {
        return blueValue;
    }

    public void setRedValue(int redValue) {
        this.redValue = redValue;
    }

    public void setGreenValue(int greenValue) {
        this.greenValue = greenValue;
    }

    public void setBlueValue(int blueValue) {
        this.blueValue = blueValue;
    }
}
