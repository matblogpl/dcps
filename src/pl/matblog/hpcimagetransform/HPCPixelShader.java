package pl.matblog.hpcimagetransform;

import java.awt.*;

/**
 *  Pixel shader interface for HPC RMI Server / Distributed Computing pixel shader for graphic transform
 *
 *
 * The source code distributed under the MIT License
 *
 * Details: LICENSE file
 *
 *
 * Created by Mateusz_Mierzwinski on 16.11.14.
 */
public interface HPCPixelShader {

    /**
     * Returns radius of implemented pixel shader
     *
     * @return  - radius of the pixel shader in pixels
     */
    public int getPixelShaderRadius();

    /**
     * Apply pixel shader at specified position
     *
     * @param bitmapPixelArray      - current pixel array for applying of the shader
     * @param currentPixelPosition  - current pixel position of shader
     */
    public void applyPixelShader(Color[][] bitmapPixelArray, Point currentPixelPosition);

}
