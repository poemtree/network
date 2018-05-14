package Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class EventHandler {

	private static final int MAX_THREAD_POOL = 5;



	/**
	 * Note : ArrayList may occur ConcurrentModificationException so using
	 * CopyOnWriteArrayList for prevent Exception based on multi thread. Do not
	 * use below source code. private static List<EventListener> listeners = new
	 * ArrayList<EventListener>();
	 */
	private static List<EventListener> listeners = new CopyOnWriteArrayList<EventListener>();

	private static synchronized List<EventListener> getListeners() {
		return listeners;
	}

	public static synchronized void addListener(EventListener eventListener) {
		if (getListeners().indexOf(eventListener) == -1) {
			listeners.add(eventListener);
		}
	}

	public static synchronized void removeListener(EventListener eventListener) {
		if (getListeners().indexOf(eventListener) != -1) {
			listeners.remove(eventListener);
		}
	}

	public static synchronized void callEvent(final Class<?> caller, String event) {
		callEvent(caller, event, true);
	}

	public static synchronized void callEvent(final Class<?> caller, String event, boolean doAsynch) {
		if (doAsynch) {
			callEventByAsynch(caller, event);
		} else {
			callEventBySynch(caller, event);
		}
	}

	private static synchronized void callEventByAsynch(final Class<?> caller, final String event) {
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_POOL);

		

		for (final EventListener listener : listeners) {
			executorService.execute(new Runnable() {
				public void run() {
					if (listener.getClass().getName().equals(caller.getName())) {
						
					} else {


						listener.onEvent(event);
					}
				}
			});
		}

		executorService.shutdown();
	}

	private static synchronized void callEventBySynch(final Class<?> caller, final String event) {
		

		for (final EventListener listener : listeners) {
			if (listener.getClass().getName().equals(caller.getName())) {
		
			} else {
		

				listener.onEvent(event);
			}
		}
	}
}