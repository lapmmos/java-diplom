package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {
    final int maxBright = 256;
    final char[] chars = new char[]{'#', '$', '@', '%', '*', '+', '-', '\''};
    final int preIndex = maxBright / chars.length;

    @Override
    public char convert(int color) {
//        if (color >= 0 && color < 30) {
//            return '#';
//        } else if (color >= 30 && color < 60) {
//            return '$';
//        } else if (color >= 60 && color < 90) {
//            return '@';
//        } else if (color >= 90 && color < 120) {
//            return '%';
//        } else if (color >= 120 && color < 150) {
//            return '*';
//        } else if (color >= 150 && color < 180) {
//            return '+';
//        } else if (color >= 180 && color < 210) {
//            return '-';
//        } else {
//            return ' ';
//        }
//        int maxBright = 256;
//        char[] chars = new char[]{'#', '$', '@', '%', '*', '+', '-', '\''};
//        int preIndex = maxBright / chars.length;
        return chars[color/preIndex];
    }
}
