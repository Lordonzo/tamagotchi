package models;

public class Options {
    private double volume;
    private int resX, resY;
    private String language;

    public Options(double _volume, int _resX, int _resY,String _language) {
        this.volume = _volume;
        this.resX = _resX;
        this.resY = _resY;
        this.language = _language;
    }

   // public Options() {} //TODO useless ?


    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
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
