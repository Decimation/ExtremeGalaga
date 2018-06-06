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

	private static final class InternalHypervisor implements Runnable {

		@Override
		public void run() {

			while (true) {
				Common.sleep(pollingMs);
				//Common.printf(Debug.HYPERVISOR, "Performing sweep");

				IntTuple localAliens = new IntTuple();
				IntTuple localBullets = new IntTuple();


				// Sweep aliens
				if (!GalagaEngine.aliens.isEmpty()) {
					for (GAlien obj : GalagaEngine.aliens) {

						// Recursively search the bullet cache in the aliens
						/*for (GBullet bullet : obj.getBulletCache()) {
							if (bullet.flaggedForDeletion()) {
								localBullets.incrementKey();
								bullet.destroy();
								obj.getBulletCache().remove(bullet);
							}
							if (!bullet.flaggedForDeletion()) {
								localBullets.incrementKey();
							}
							//Common.printf(Debug.HYPERVISOR, "[%s, %s]", obj, bullet);
						}*/

						if (obj.flaggedForDeletion()) {
							localAliens.incrementKey();
							obj.destroy();
							localBullets.incrementKey(obj.getBulletCache().size());
							obj.getBulletCache().clear();

							assert GalagaEngine.aliens.indexOf(obj) != -1;
							GalagaEngine.aliens.remove(obj);
						}
						if (!obj.flaggedForDeletion()) {
							localAliens.incrementValue();
						}


					}
					//Common.printf(Debug.HYPERVISOR, "Deleted %d aliens, %d ignored", flagged, nonFlagged);
				}


				// Sweep bullets
				if (!GalagaEngine.getShip().bulletCache.isEmpty()) {
					for (ShipBullet bullet : GalagaEngine.getShip().bulletCache) {
						if (bullet.flaggedForDeletion()) {
							localBullets.incrementKey();
							bullet.destroy();
							GalagaEngine.getShip().bulletCache.remove(bullet);
						}
						if (!bullet.flaggedForDeletion()) {
							localBullets.incrementValue();
						}
					}
				}
				System.out.printf("[D/A] Aliens: %s | Bullets: %s\n[Size] Aliens: %d | Bullets: %d\n\n", localAliens, localBullets, GalagaEngine.aliens.size(), GalagaEngine.getShip().bulletCache.size());

			}
		}
	}


}
