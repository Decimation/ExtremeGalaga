package com.deci.galaga;

class Hypervisor {
	private static long   pollingMs;
	private static Thread internalVisor;

	private Hypervisor() {
	}

	static void init() {
		internalVisor = new Thread(new InternalHypervisor());
		pollingMs = 10;
		internalVisor.setDaemon(true);
		internalVisor.start();
		Common.printf(Debug.HYPERVISOR, "Initializing Hypervisor");
	}

	static void close() {
		internalVisor.interrupt();
		Common.printf(Debug.HYPERVISOR, "Hypervisor closed");
	}

	private static class InternalHypervisor implements Runnable {

		@Override
		public void run() {
			while (true) {
				Common.sleep(pollingMs);
				//Common.printf(Debug.HYPERVISOR, "Performing sweep");


				int flagged = 0;
				int nonflagged = 0;

				// Sweep aliens
				if (!GalagaEngine.aliens.isEmpty()) {
					for (GObject obj : GalagaEngine.aliens) {
						if (obj.flaggedForDeletion()) {
							flagged++;
							obj.destroy();
							GalagaEngine.aliens.remove(obj);
						}
						if (!obj.flaggedForDeletion()) {
							nonflagged++;
						}
					}
				}

				flagged = 0;
				nonflagged = 0;

				// Sweep bullets
				if (!((Ship) GalagaEngine.ship).bulletCache.isEmpty()) {
					for (GBullet bullet : ((Ship) GalagaEngine.ship).bulletCache) {
						if (bullet.flaggedForDeletion()) {
							flagged++;
							bullet.destroy();
							((Ship) GalagaEngine.ship).bulletCache.remove(bullet);
						}
						if (!bullet.flaggedForDeletion()) {
							nonflagged++;
						}
					}
				}
				if (flagged > 0) {
					//Common.printf(Debug.HYPERVISOR, "Indexed %d flagged objects and %d non-flagged objects", flagged, nonflagged);
					Common.printf(Debug.HYPERVISOR, "Unloaded %d GObjects", flagged);
				}

			}
		}
	}


}
