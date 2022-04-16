 package com.kifiyapro.bunchi.modle;

public class Photos {
    private String small;
    private String medium;
    private String large;

    public Photos() {
    }

    public Photos(String small, String medium, String large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

//    @Override
//    public String toString() {
//        return "Photos{" +
//                "small='" + small + '\'' +
//                ", medium='" + medium + '\'' +
//                ", large='" + large + '\'' +
//                '}';
//    }
}
