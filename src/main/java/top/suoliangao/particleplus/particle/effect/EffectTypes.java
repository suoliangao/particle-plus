package top.suoliangao.particleplus.particle.effect;

public enum EffectTypes {
	/** Single particle */
	SINGLE ("single", 1),
	
	LATTICE ("lattice", 2),
	
	CLOUD ("cloud", 3),
	
	IMAGE ("image", 11),

	TEXT ("text", 12),
	;
	
	private String name;
	private byte type;
	public String getName() {return this.name;}
	public byte getType() {return this.type;}
	private EffectTypes(String name, int type) {
		this.name = name;
		this.type = (byte)type;
	}

	public static EffectTypes fromByte (byte b) {
		switch (b) {
			case 1: return SINGLE;
			case 2: return LATTICE;
			case 3: return CLOUD;
			case 11: return IMAGE;
			case 12: return TEXT;

		}
		return null;
	}
}
