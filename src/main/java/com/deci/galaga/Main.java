package com.deci.galaga;


import processing.core.PApplet;

public class Main {



	private static GalagaEngine engine;

    public static void main(String[] args) {
		System.setSecurityManager(null);

		// Start the engine
		engine = new GalagaEngine();

		// Start the Processing applet
		PApplet.main("com.deci.galaga.GalagaEngine");


    }

}
