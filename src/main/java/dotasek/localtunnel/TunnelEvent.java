package dotasek.localtunnel;

import java.net.Socket;

class TunnelEvent {
	final TunnelClusterEventType type;
	
	final Socket socket;
	
	public TunnelEvent(TunnelClusterEventType type) {
		this(type, null);
	}
	
	public TunnelEvent(TunnelClusterEventType type, Socket socket) {
		this.type = type;
		this.socket = socket;
	}
}