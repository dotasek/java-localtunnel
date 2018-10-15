package dotasek.localtunnel;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TunnelCluster {

		String remote_host;
		Integer remote_port;
		
		private static TunnelCluster tunnelCluster = null;
		
		private TunnelCluster(TunnelConnectionModel tunnelConnection) {
			
		}
		
		public static TunnelCluster getTunnelCluster(TunnelConnectionModel tunnelConnection) {
			if (tunnelCluster == null) {
				tunnelCluster = new TunnelCluster(tunnelConnection);
			}
			return tunnelCluster;
		}
		
		public void open() throws UnknownHostException, IOException {
			/* 
			var remote_host = opt.remote_host;
			    var remote_port = opt.remote_port;

			    var local_host = opt.local_host || 'localhost';
			    var local_port = opt.local_port;

			    debug('establishing tunnel %s:%s <> %s:%s', local_host, local_port, remote_host, remote_port);
*/
			    // connection to localtunnel server
			   Socket remote = new Socket( remote_host, remote_port);
			   remote.setKeepAlive(true);
			   
		}
}
