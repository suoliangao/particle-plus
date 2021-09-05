package top.suoliangao.particleplus.particle.effect;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

public abstract class ParticlePlusEffect {

	protected EffectTypes type;
//	protected ByteBuf buf;

	protected ParticlePlusEffect (EffectTypes type) {
		this.type = type;
	}

	protected ParticlePlusEffect (ByteBuf buf) {}

	public abstract double[] getParticles ();
	public abstract ByteBuf buildByteBuffer ();
//	{
//		byte[] bs = this.buf.array();
//		this.buf.setIndex(0, 0);
//		return bs;
//	}
}
