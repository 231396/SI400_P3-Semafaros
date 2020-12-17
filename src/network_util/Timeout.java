package network_util;

import java.util.Timer;
import java.util.TimerTask;

public class Timeout extends Timer{
	
	public interface OnTaskTimeout{
		public void onTimeout();
	}
	
	private TimerTask tt;
	private int timeout;
	private OnTaskTimeout onTaskTimeout;
	
	public Timeout(OnTaskTimeout onTaskTimeout, int timeout) {
		
		this.onTaskTimeout = onTaskTimeout;
		this.timeout = timeout;
	}
	

	public void startTimeout() {
		if(tt != null)
			tt.cancel();
		this.tt = new TimerTask() {
		    @Override
		    public void run() {
		    	onTaskTimeout.onTimeout();
		    };
		};
		this.schedule(tt, timeout);
	}
	
	public void stopTimeout() {
		tt.cancel();
	}
	
	

}
