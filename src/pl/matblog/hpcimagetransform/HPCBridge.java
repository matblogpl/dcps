package pl.matblog.hpcimagetransform;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Distributed Computing pixel shader for graphic transform - bridge to nodes interface
 *
 *
 * The source code distributed under the MIT License
 *
 * Details: LICENSE file
 *
 *
 * Created by Mateusz_Mierzwinski on 16.11.14.
 */
public interface HPCBridge extends Remote {

    /**
     * External link for shader application from external apps
     *
     * @param bitmapModel                   - bitmap to apply list of shaders
     * @param shaders                       - list of shaders to apply on bitmap
     * @throws java.rmi.RemoteException     - exception, like there is no free nodes
     */
    public HPCBitmapModel applyShaders(HPCBitmapModel bitmapModel, ArrayList<HPCPixelShader> shaders) throws RemoteException;

}
