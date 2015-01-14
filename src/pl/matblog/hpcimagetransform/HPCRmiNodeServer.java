package pl.matblog.hpcimagetransform;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Distributed Computing pixel shader for graphic transform - node server
 *
 *
 * The source code distributed under the MIT License
 *
 * Details: LICENSE file
 *
 *
 * Created by Mateusz_Mierzwinski on 16.11.14.
 */
public class HPCRmiNodeServer extends UnicastRemoteObject implements HPCNodeConnector {

    private boolean freeServer;

    /**
     * Simple constructor for RMI Node
     *
     * @throws Exception
     */
    public HPCRmiNodeServer() throws Exception {

        Registry registry;

        this.freeServer = true;


        /**
         * Let's distribute methods to external/remote interfaces
         */
        try {

            registry = LocateRegistry.createRegistry(9091);
            registry.rebind("hpcRmiNodeServer", this);

        } catch (RemoteException remoteException) {

            throw remoteException;

        }
    }

    /**
     * Getter of node free status
     * @return
     */
    @Override
    public boolean isThisNodeFree() throws RemoteException {

        return this.freeServer;

    }

    /**
     * Node processing capability - running at node
     *
     * @param bitmapObject   - the bitmap object
     * @param shaders        - the shaders array
     * @return               - the bitmap object
     */
    @Override
    public HPCBitmapModel applyShaders(HPCBitmapModel bitmapObject, ArrayList<HPCPixelShader> shaders) throws RemoteException {

        this.freeServer = false;

        try {
            for (HPCPixelShader pixelShader : shaders) {

                bitmapObject.applyPixelShader(pixelShader);

            }
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBounds) {

            this.freeServer = true;
            throw new RemoteException("Problem with shader", arrayIndexOutOfBounds);

        } catch (NullPointerException nullPointerException) {

            this.freeServer = true;
            throw new RemoteException("Problem with shader", nullPointerException);

        }

        this.freeServer = true;

        return bitmapObject;
    }
}
