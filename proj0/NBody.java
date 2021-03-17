public class NBody {
	public static String background = "images/starfield.jpg";

	public static double readRadius(String address) {
		In in = new In(address);

		int numberOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();

		return radiusOfUniverse;
	}


	public static Planet[] readPlanets(String address) {
		In in = new In(address);

		int numberOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();
		Planet[] planets = new Planet[numberOfPlanets];
		for (int i = 0; i< numberOfPlanets; i++) {
			double xxP = in.readDouble();
			double yyP = in.readDouble();
			double xxV = in.readDouble();
			double yyV = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();
			planets[i] = new Planet(xxP, yyP, xxV, yyV, mass, img);
		}

		return planets;
	}

	public static void drawBackground(double radius) {
		
		StdDraw.picture(0, 0, background);
	}

	public static void drawOnePlanet(Planet planet) {
		planet.draw();
	}

	public static void drawPlanets(Planet[] planets) {
		for (var planet : planets){		
			drawOnePlanet(planet);
		}
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		double radiusOfUniverse = readRadius(filename);
		var planets = readPlanets(filename);
		int numberOfPlanets = planets.length;
		StdDraw.setScale(-radiusOfUniverse, radiusOfUniverse);
	

		StdDraw.enableDoubleBuffering();
		for (double time = 0; time < T; time += dt) {
			double[] xForces = new double[numberOfPlanets];
			double[] yForces = new double[numberOfPlanets];
			for (int i = 0; i<numberOfPlanets; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			drawBackground(radiusOfUniverse);	
			drawPlanets(planets);
			StdDraw.show();
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radiusOfUniverse);
		for (int i = 0; i < planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   	
		}	
	}
}