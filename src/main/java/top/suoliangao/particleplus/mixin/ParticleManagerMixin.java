package top.suoliangao.particleplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.MinecraftClient;
import top.suoliangao.particleplus.ParticlePlusMod;

@Mixin(MinecraftClient.class)
public class ParticleManagerMixin {
	 @Inject(method = "<init>", at = @At("TAIL"))
	 public void constructorTail (CallbackInfo ci) {
		 System.out.println("Particle Plus Mod MIXIN!!!");
		 ParticlePlusMod.getInstance().initParticleManager();
	 }

}
