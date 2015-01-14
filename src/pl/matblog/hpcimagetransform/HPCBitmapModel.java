package pl.matblog.hpcimagetransform;

import java.awt.*;

/**
 * HPCBitmapModel with pixel shaders applying methods
 *
 *
 * The source code distributed under the MIT License
 *
 * Details: LICENSE file
 *
 *
 * Created by Mateusz_Mierzwinski on 16.11.14.
 */
public class HPCBitmapModel {

    /**
     * Int describing bitmapId for mapping to name proposes.
     */
    private int bitmapId;

    /**
     * Point describing width and height of the bitmap, as it's AWT.Color two dimmensional array
     */
    private Point bitmapSize;

    /**
        AWT.Color for better transform as it's have RGBA structure and special getters/setters
        for R,G,B and Alpha channels.
     */
    private Color[][] bitmapPixelData;

    /**
     * Bitmap constructor for fast object build
     *
     * @param bitmapId          - bitmap ID mapped from name
     * @param bitmapSize        - bitmap size in pixels
     * @param bitmapPixelData   - bitmap Pixel data as AWT.Color array
     */
    public HPCBitmapModel(int bitmapId, Point bitmapSize, Color[][] bitmapPixelData) {

        this.bitmapId = bitmapId;
        this.bitmapSize = bitmapSize;
        this.bitmapPixelData = bitmapPixelData;

    }

    /**
     * Return pixel color as AWT.Color object from specified coordinates (X,Y) of the bitmap
     *
     * @param pixelCoordinates                  - pixel coordinates (X, Y)
     * @return                                  - returned pixel color
     * @throws ArrayIndexOutOfBoundsException   - coordinates out of bitmapPixelData bounds
     * @throws NullPointerException             - null at pixelCoordinates, bitmapPixelData or pixelColor in the array.
     */
    public Color getPixelColor(Point pixelCoordinates) throws ArrayIndexOutOfBoundsException,NullPointerException {

            try {
                return this.bitmapPixelData[pixelCoordinates.x][pixelCoordinates.y];
            } catch (ArrayIndexOutOfBoundsException exception) {
                throw exception;
            } catch (NullPointerException exception) {
                throw exception;
            }

    }

    /**
     * Pixel color set
     *
     * @param pixelCoordinates                  - pixel coordinates (X, Y)
     * @param pixelColor                        - pixel color (AWT.Color)
     * @throws ArrayIndexOutOfBoundsException   - coordinates out of bitmapPixelData bounds
     * @throws NullPointerException             - null at pixelCoordinates, bitmapPixelData or pixelColor
     */
    public void setPixelColor(Point pixelCoordinates, Color pixelColor) throws ArrayIndexOutOfBoundsException,NullPointerException {

        try {
            bitmapPixelData[pixelCoordinates.x][pixelCoordinates.y] = pixelColor;
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw exception;
        } catch (NullPointerException exception) {
            throw exception;
        }

    }

    /**
     * Application of Pixel Shader on pixel data
     *
     * @param shader
     * @throws ArrayIndexOutOfBoundsException
     * @throws NullPointerException
     */
    public void applyPixelShader(HPCPixelShader shader) throws ArrayIndexOutOfBoundsException,NullPointerException{

        try {
            int pixelShaderRadius = shader.getPixelShaderRadius();

            for (int y=0+pixelShaderRadius; y<(this.bitmapSize.y-pixelShaderRadius); y++) {
                for (int x=0+pixelShaderRadius; x<(this.bitmapSize.x-pixelShaderRadius); x++) {
                    shader.applyPixelShader(this.bitmapPixelData, new Point(x, y));
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw exception;
        } catch (NullPointerException exception) {
            throw exception;
        }

    }

    /**
     * Simple getter for pixel data
     *
     * @return  - two dimmensional array of pixel data
     */
    public Color[][] getPixelData() {

        return this.bitmapPixelData;

    }

    /**
     * Simple getter for bitmap size
     *
     * @return      - bitmap pixel size as Point(X,Y)
     */
    public Point getBitmapSize() {

        return this.bitmapSize;

    }

    /**
     * Simple getter for bitmap Id
     *
     * @return      - bitmap ID
     */
    public int getBitmapId() {

        return this.bitmapId;

    }
}
