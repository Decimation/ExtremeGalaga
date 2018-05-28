package com.deci.galaga;

class DrawThread extends Thread {

	float frameRate;
	int   frameCount;
	float frameRateLastNanos;
	private volatile EBullet obj;
	DrawThread(final EBullet obj) {
		this.obj = obj;
		System.out.printf("\nDrawThread @ %s\n", obj);

	}

	@Override
	public void run() {

		System.out.printf("Running thread %s\n", getName());


		long now = System.nanoTime();

		double rate = 1000000.0D / ((double) (now - this.frameRateLastNanos) / 1000000.0D);
		float instantaneousRate = (float) (rate / 1000.0D);
		this.frameRate = this.frameRate * 0.9F + instantaneousRate * 0.1F;

		this.frameRateLastNanos = now;
		++this.frameCount;

		float newY = obj.getY();
		while (newY >= 0.0f) {
			newY -= 0.1f;
			obj.setY(newY);
			obj.manifest();
		}


		System.out.printf("\nThread %s done\n", getName());
	}
}
