package top.suoliangao.particleplus.network;

public enum ParticlePlusPacketType {
	INVALID(-1), CREATE(0), DELET (1),
	EFFECT(10), MOVE(11), MOVETO(12),
	ROTATE(13), LOOKAT(14), SCALE(15),
	SET(20), RESET_ROTATION(21), ANIM(30);
	
	private byte type;
	public byte getType () {return this.type;}
	
	private ParticlePlusPacketType (int type) {
		this.type = (byte)type;
	}
	
	public static ParticlePlusPacketType fromByte (byte b) {
		switch (b) {
		case 0: return CREATE;
		case 1: return DELET;
		case 10: return EFFECT;
		case 11: return MOVE;
		case 12: return MOVETO;
		case 13: return ROTATE;
		case 14: return LOOKAT;
		case 15: return SCALE;
		case 20: return SET;
		case 21: return RESET_ROTATION;
		case 30: return ANIM;
		default: return INVALID;
		}
	}
	
}
