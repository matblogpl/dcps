package pl.matblog.hpcimagetransform;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Distributed Computing pixel shader for graphic transform - Distribution server
 *
 *
 * The source code distributed under the MIT License
 *
 * Details: LICENSE file
 *
 *
 * Created by Mateusz_Mierzwinski on 16.11.14.
 */
public class HPCRmiDistributionServer extends UnicastRemoteObject implements HPCBridge {

    private ArrayList<String> rmiNodesLinks;


    /**
     * Simple constructor for RMI Distrubution Server with RMI binding capability
     *
     * @param rmiNodesLinks
     * @throws Exception
     */
    public HPCRmiDistributionServer(ArrayList<String> rmiNodesLinks) throws Exception {

        Registry registry;

        /**
         * Do we have any nodes specified?
         */
        if (rmiNodesLinks == null || rmiNodesLinks.size() < 1) {
            throw new Exception("No nodes links for RMI Distributed computing.");
        }

        this.rmiNodesLinks = rmiNodesLinks;

        /**
         * Let's distribute methods to external/remote interfaces
         */
        try {

            registry = LocateRegistry.createRegistry(9090);
            registry.rebind("hpcRmiDistributionServer", this);

        } catch (RemoteException remoteException) {
            throw remoteException;
        }
    }

    /**
     * Distributed shaders processing (applyShaders on multiple nodes)
     *
     * @param bitmapModel                   - bitmap to apply list of shaders
     * @param shaders                       - list of shaders to apply on bitmap
     * @return                              - HPCBitmapModel as bitmap data
     * @throws RemoteException              - Throwed when there is no free nodes
     */
    @Override
    public HPCBitmapModel applyShaders(HPCBitmapModel bitmapModel, ArrayList<HPCPixelShader> shaders) throws RemoteException {

        boolean foundFreeNode = false;
        HPCNodeConnector hpcShadersProcessingNode = null;

        for (String rmiNodeLink : this.rmiNodesLinks) {
            try {
                hpcShadersProcessingNode = (HPCNodeConnector) Naming.lookup(rmiNodeLink);
                if (hpcShadersProcessingNode.isThisNodeFree()) {
                    foundFreeNode = true;
                    break;
                }
            } catch (NotBoundException notBoundException) {
            } catch (MalformedURLException malformedUrlException) {
            }
        }

        if (!foundFreeNode) {
            throw new RemoteException("There is no free node");
        } else {
            if (hpcShadersProcessingNode == null) {
                throw new RemoteException("Cannot start processing - node is null");
            }
            return hpcShadersProcessingNode.applyShaders(bitmapModel, shaders);
        }

    }
}
