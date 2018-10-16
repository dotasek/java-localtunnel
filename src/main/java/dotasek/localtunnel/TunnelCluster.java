package dotasek.localtunnel;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Observable;
import java.util.Observer;

public class TunnelCluster extends Observable {

		String remote_host;
		Integer remote_port;
		
		String local_host;
		Integer local_port;
		
		int tunnelCount;
		
		private static TunnelCluster tunnelCluster = null;
		
		private TunnelConnectionModel tunnelConnection;
		
		private TunnelCluster(TunnelConnectionModel tunnelConnection) {
			tunnelCount = 1;
			this.tunnelConnection = tunnelConnection;
		}
		
		public static TunnelCluster getTunnelCluster(TunnelConnectionModel tunnelConnection) {
			if (tunnelCluster == null) {
				tunnelCluster = new TunnelCluster(tunnelConnection);
			}
			return tunnelCluster;
		}
		
		public void open() throws UnknownHostException, IOException {
			 
			String remote_host = tunnelConnection.remote_host;
			Integer remote_port = tunnelConnection.remote_port;
			
			String local_host = "localhost"; //opt.local_host || "localhost";
			Integer local_port = 1234; //opt.local_port;

			System.out.println("establishing tunnel " +  local_host + " " +  local_port + " <> " + remote_host + " " + remote_port);

			    // connection to localtunnel server
			Socket remote = new Socket( remote_host, remote_port);
			
			Socket local = new Socket(local_host, local_port);
			   
			System.out.println("Sockets established.");
			 
		}

}
