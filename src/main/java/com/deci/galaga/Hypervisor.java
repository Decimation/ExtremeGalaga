package com.deci.galaga;

class Hypervisor {

	/**
	 * The delay, in ms, between Hypervisor sweeps
	 */
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
				int nonFlagged = 0;

				// Sweep aliens
				if (!GalagaEngine.aliens.isEmpty()) {
					for (GObject obj : GalagaEngine.aliens) {
						if (obj.flaggedForDeletion()) {
							flagged++;
							obj.destroy();
							GalagaEngine.aliens.remove(obj);
						}
						if (!obj.flaggedForDeletion()) {
							nonFlagged++;
						}
					}
				}

				flagged = 0;
				nonFlagged = 0;

				// Sweep bullets
				if (!GalagaEngine.getShip().bulletCache.isEmpty()) {
					for (GBullet bullet : GalagaEngine.getShip().bulletCache) {
						if (bullet.flaggedForDeletion()) {
							flagged++;
							bullet.destroy();
							GalagaEngine.getShip().bulletCache.remove(bullet);
						}
						if (!bullet.flaggedForDeletion()) {
							nonFlagged++;
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
