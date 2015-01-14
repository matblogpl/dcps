package pl.matblog.hpcimagetransform;


import java.util.ArrayList;

/**
 * Distributed Computing pixel shader for graphic transform - complete server / proxy / node
 *
 *
 * The source code distributed under the MIT License
 *
 * Details: LICENSE file
 *
 *
 * Created by Mateusz_Mierzwinski on 16.11.14.
 */
public class Main {

    /**
     * Copyright invocation
     */
     private static void invocation() {

         System.out.println("HPC/RMI Distributed Computing pixel shader server\n" +
                 "2014(c) Mateusz Mierzwinski\n" +
                 "http://matblog.pl\n");

     }


    /**
     * Help invocation
     */
     private static void helpInvocation() {

         System.out.println("\nUsage: <jar execution string> <mode> <options>\n\n\t" +
                 "mode: server|node|local   - set distribution server or processing node mode\n\t" +
                 "options: rmiList          - in server mode only comma separated string of node IP's\n");

     }

    /**
     * Constructor for main app functionality
     *
     * @param args  - regular system arguments
     */
    public static void main(String[] args) {

        invocation();

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {

                case "server":
                    if (args.length > 1) {
                        initDistributionServer(args[1]);
                    } else {
                        helpInvocation();
                    }
                    break;

                case "node":
                    initNodeServer();
                    break;

                case "local":
                    initNodeServer();
                    initDistributionServer("127.0.0.1");
                    break;

                default:
                    helpInvocation();
                    break;

            }
        } else {
            helpInvocation();
        }

    }

    /**
     * Return ArrayList of node servers from string
     *
     * @param arg   - String, providing comma separated ip's
     * @return      - ArrayList of RMI links
     */
    private static ArrayList<String> getRmiNodeAddrs(String arg) {

        ArrayList<String> nodeList = new ArrayList<String>();

        String[] ipArray = arg.split(",");
        int ipArrayLength = ipArray.length;

        for (int i=0; i<ipArrayLength; i++) {
            if (ipArray[i].length() > 1) {
                nodeList.add("rmi://" + ipArray[i] + ":9091/hpcRmiNodeServer");
            }
        }

        return nodeList;
    }

    /**
     * Init node server (processing unit)
     */
    private static void initNodeServer() {

        try {

            System.out.print("-- Starting node server: ");
            HPCRmiNodeServer node = new HPCRmiNodeServer();
            System.out.println("DONE");

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Init distribution server
     *
     * @param nodesIps
     */
    private static void initDistributionServer(String nodesIps) {

        ArrayList<String> nodes = getRmiNodeAddrs(nodesIps);

        try {

            System.out.print("-- Starting distribution server: ");
            HPCRmiDistributionServer server = new HPCRmiDistributionServer(nodes);
            System.out.println("DONE");

            /**
             * Some detailed info about nodes
             */
            System.out.println("\nServer nodes: ");
            for (String node : nodes) {
                System.out.println(" ->"+node);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
