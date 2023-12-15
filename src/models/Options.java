package models;

public class Options {
    private double volume;
    private int resX, resY;

    public Options(double _volume, int _resX, int _resY) {
        this.volume = _volume;
        this.resX = _resX;
        this.resY = _resY;
    }

    public Options() {}


    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getResX() {
        return this.resX;
    }

    public void setResX(int resX) {
        this.resX = resX;
    }

    public int getResY() {
        return this.resY;
    }

    public void setResY(int resY) {
        this.resY = resY;
    }


}
