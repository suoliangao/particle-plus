package top.suoliangao.particleplus.command;

public enum ParticleGroupTypes {
	BASE("base"),
	;
	
	private String name;
	private ParticleGroupTypes (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
}
