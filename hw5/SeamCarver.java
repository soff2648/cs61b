import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture p;
    private double[][] energyMemory;
    private int widthOfPicture;
    private int heightOfPicture;

    public SeamCarver(Picture picture) {
        p = picture;
        widthOfPicture = p.width();
        heightOfPicture = p.height();
        energyMemory = new double[heightOfPicture][widthOfPicture];
        for (int i = 0; i < heightOfPicture; i++) {
            for (int j = 0; j < widthOfPicture; j++) {
                energyMemory[i][j] = calculateEnergy(p, j, i);
            }
        }

    }

    private void updatePicture() {
        widthOfPicture = p.width();
        heightOfPicture = p.height();
        energyMemory = new double[heightOfPicture][widthOfPicture];
        for (int i = 0; i < heightOfPicture; i++) {
            for (int j = 0; j < widthOfPicture; j++) {
                energyMemory[i][j] = calculateEnergy(p, j, i);
            }
        }
    }

    private double calculateEnergy(Picture picture, int w, int h) {
        //calculate x
        Color leftX;
        Color rightX;
        if (widthOfPicture == 1) {
            leftX = picture.get(w, h);
            rightX = leftX;
        } else if (w == 0) {
            leftX = picture.get(widthOfPicture - 1, h);
            rightX = picture.get(w + 1, h);
        } else if (w == widthOfPicture - 1) {
            leftX = picture.get(w - 1, h);
            rightX = picture.get(0, h);
        } else {
            leftX = picture.get(w - 1, h);
            rightX = picture.get(w + 1, h);
        }

        //calculate y
        Color upperY;
        Color lowerY;
        if (heightOfPicture == 1) {
            upperY = picture.get(w, h);
            lowerY = upperY;
        } else if (h == 0) {
            upperY = picture.get(w, heightOfPicture - 1);
            lowerY = picture.get(w, h + 1);
        } else if (h == heightOfPicture - 1) {
            upperY = picture.get(w, h - 1);
            lowerY = picture.get(w, 0);
        } else {
            upperY = picture.get(w, h - 1);
            lowerY = picture.get(w, h + 1);
        }

        return gradient(leftX, rightX) + gradient(upperY, lowerY);
    }


    private double gradient(Color a, Color b) {
        int result = (a.getBlue() - b.getBlue()) * (a.getBlue() - b.getBlue());
        result += (a.getRed() - b.getRed()) * (a.getRed() - b.getRed());
        result += (a.getGreen() - b.getGreen()) * (a.getGreen() - b.getGreen());

        return result;
    }


    public Picture picture() {
        return new Picture(p);
    }

    public double energy(int w, int h) {
        if (w < 0 || w >= widthOfPicture || h < 0 || h >= heightOfPicture) {
            throw new IndexOutOfBoundsException();
        }

        return energyMemory[h][w];
    }

    public int width() {
        return widthOfPicture;
    }

    public int height() {
        return heightOfPicture;
    }

    private double[][] transpose(double[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;

        double[][] transposed = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                transposed[i][j] = energyMemory[j][i];
            }
        }

        return transposed;

    }

    public int[] findHorizontalSeam() {
        return getMinPath(transpose(energyMemory));
    }

    public int[] findVerticalSeam() {
        return getMinPath(energyMemory);
    }

    private int[] getMinPath(double[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        double[][] M = new double[height][width];


        for (int w = 0; w < width; w++) {
            M[0][w] = matrix[0][w];
        }

        for (int h = 1; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int minW = findMinVertical(w, h - 1, M);
                M[h][w] = matrix[h][w] + M[h - 1][minW];
            }
        }

        int[] result = new int[height];
        result[height - 1] = 0;
        double minM = M[height - 1][0];
        for (int w = 0; w < width; w++) {
            if (M[height - 1][w] < minM) {
                minM = M[height - 1][w];
                result[height - 1] = w;
            }
        }
        if (height == 1) {
            return result;
        }

        for (int h = height - 2; h >= 0; h--) {
            result[h] = findMinVertical(result[h + 1], h, M);
        }

        return result;
    }


    private int findMinVertical(int w, int h, double[][] matrix) {
        int width = matrix[0].length;
        if (width == 1) {
            return w;
        } else if (width == 2) {
            if (w == 0) {
                return matrix[h][w] < matrix[h][w + 1] ? w : w + 1;
            } else {
                return matrix[h][w] < matrix[h][w - 1] ? w : w - 1;
            }
        } else {
            if (w == 0) {
                return matrix[h][w] < matrix[h][w + 1] ? w : w + 1;
            } else if (w == width - 1) {
                return matrix[h][w] < matrix[h][w - 1] ? w : w - 1;
            } else {
                int minW = w;
                minW = matrix[h][w] < matrix[h][w - 1] ? w : w - 1;
                minW = matrix[h][minW] < matrix[h][w + 1] ? minW : w + 1;
                return minW;
            }
        }
    }
    public void removeHorizontalSeam(int[] seam) {
        checkSeam(seam);
        p = SeamRemover.removeHorizontalSeam(p, seam);
        updatePicture();

    }
    public void removeVerticalSeam(int[] seam) {
        checkSeam(seam);
        p = SeamRemover.removeVerticalSeam(p, seam);
        updatePicture();
    }

    private void checkSeam(int[] seam) {

        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] - seam[i + 1] > 1 || seam[i] - seam[i + 1] < -1) {
                throw new IllegalArgumentException();
            }
        }
    }
}

