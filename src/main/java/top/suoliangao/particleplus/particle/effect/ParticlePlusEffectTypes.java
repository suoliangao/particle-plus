package top.suoliangao.particleplus.particle.effect;

import java.util.function.Function;

import com.google.gson.JsonObject;

import net.minecraft.nbt.NbtCompound;

public enum ParticlePlusEffectTypes {
	/** Single particle */
	SINGLE ("single"),
	
	LATTICE ("lattice"),
	
	CLOUD ("cloud"),
	
	
	;
	
	private String type;
	public String getType () {return this.type;}
	private ParticlePlusEffectTypes (String type) {
		this.type = type;
	}
}
