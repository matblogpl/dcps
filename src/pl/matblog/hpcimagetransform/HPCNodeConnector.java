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
public interface HPCNodeConnector extends Remote {

    /**
     * Getter for node free status
     *
     * @return
     */
    public boolean isThisNodeFree() throws RemoteException;

    /**
     * Node shaders data link for further image processing
     *
     * @param bitmapObject
     * @param shaders
     * @return
     */
    public HPCBitmapModel applyShaders(HPCBitmapModel bitmapObject, ArrayList<HPCPixelShader> shaders) throws RemoteException;
}
