package net.blazecode.wrangler.mixins.ents;

import net.blazecode.vanillify.api.VanillaUtils;
import net.blazecode.wrangler.WranglerMod;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( CreeperEntity.class )
public abstract class CreeperEntityMixin
{
    @Redirect( method = "tick", at = @At( value = "INVOKE", target = "Lnet/minecraft/entity/mob/CreeperEntity;explode()V" ))
    private void explode( CreeperEntity instance )
    {
        if( !instance.world.isClient )
        {
            int fakeChance = WranglerMod.getConfig().getFakeChance();
            int maxY = WranglerMod.getConfig( ).getMaxYExplosion( );

            boolean isFake = (instance.world.random.nextInt( 100 ) <= fakeChance);
            boolean isBelowMaxY = (instance.getBlockY() <= maxY);

            if(!isFake && isBelowMaxY)
            {
                instance.playSound( SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, 1.33f, 0.85f );
                Explosion.DestructionType destructionType = instance.world.getGameRules().getBoolean( GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
                float f = instance.shouldRenderOverlay() ? 2.0F : 1.0F;
                instance.world.createExplosion(instance, instance.getX(), instance.getY(), instance.getZ(), 4.5f * f, destructionType);
            }
            else
            {
                instance.playSound( SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 0.5f );
            }
            instance.discard();
        }
    }
}
