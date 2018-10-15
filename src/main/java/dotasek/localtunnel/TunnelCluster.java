package dotasek.localtunnel;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class TunnelCluster implements Observer{

		String remote_host;
		Integer remote_port;
		
		enum TunnelClusterEventType {
			OPEN, //once + open
			ERROR, 
			DEAD,
			REQUEST
		}
		
		class TunnelClusterEvent {
			final TunnelClusterEventType type;
			public TunnelClusterEvent(TunnelClusterEventType type) {
				this.type = type;
			}
		}
		
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

		public void update(Observable arg0, Object arg1) {
			if (arg0 instanceof Tunnel && arg1 instanceof TunnelClusterEvent)
			{
				Tunnel tunnel = (Tunnel) arg0;
				TunnelClusterEvent tunnelClusterEvent = (TunnelClusterEvent) arg1;
				switch (tunnelClusterEvent.type) {
					case OPEN: 
						
						break;
					case ERROR: 
						break;
					case DEAD: 
						break;
					case REQUEST: 
						break;
				}
			}
			
		}
}
