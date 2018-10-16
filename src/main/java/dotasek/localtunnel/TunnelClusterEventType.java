package dotasek.localtunnel;

enum TunnelClusterEventType {
	OPEN, //once + open
	ERROR, 
	DEAD,
	REQUEST
}