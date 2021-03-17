public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	private static final double graviConstant = 6.67E-11;

	public Planet(double xP, double yP, double xV
					,double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet other) {
		double dx = other.xxPos - this.xxPos;
		double dy = other.yyPos - this.yyPos;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public double calcForceExertedBy(Planet other) {
		double distance = this.calcDistance(other);
		return graviConstant * this.mass * other.mass / (distance * distance);
	}

	public double calcForceExertedByX(Planet other) {
		double dx = other.xxPos - this.xxPos;
		double distance = this.calcDistance(other);

		return dx * calcForceExertedBy(other) / distance;
	}

	public double calcForceExertedByY(Planet other) {
		double dy = other.yyPos - this.yyPos;
		double distance = this.calcDistance(other);

		return dy * calcForceExertedBy(other) / distance;
	}

	public double calcNetForceExertedByX(Planet[] planets) {
		double netForceExertedByX = 0.0;
		for (var planet: planets) {
			if (this.equals(planet))
				continue;
			netForceExertedByX += this.calcForceExertedByX(planet);

		}
		return netForceExertedByX;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		double netForceExertedByY = 0.0;
		for (var planet: planets) {
			if (this.equals(planet))
				continue;
			netForceExertedByY += this.calcForceExertedByY(planet);

		}
		return netForceExertedByY;
	}

	public void update(double dt, double netForceExertedByX, double netForceExertedByY) {
		double aX = netForceExertedByX / this.mass;
		double aY = netForceExertedByY / this.mass;
		this.xxVel = this.xxVel + dt * aX;
		this.yyVel = this.yyVel + dt * aY;
		this.xxPos = this.xxPos + dt * xxVel;
		this.yyPos = this.yyPos + dt * yyVel;
	}

	public void draw(){
		String address = "images/" + imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, address);
	
	}
}