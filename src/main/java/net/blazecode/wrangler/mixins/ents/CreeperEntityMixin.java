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
            int maxY = WranglerMod.getConfig( ).getYThreshold( );
            boolean isBelowMaxY = (instance.getBlockY() <= maxY);

            int fakeChance = ( ( isBelowMaxY ? WranglerMod.getConfig( ).getFakeChanceBelow( ) : WranglerMod.getConfig( ).getFakeChanceAbove( ) ) );
            
            float multiplier = ( isBelowMaxY ? WranglerMod.getConfig( ).getMultiplierBelow( ) : WranglerMod.getConfig( ).getMultiplierAbove() );
            
            boolean triggered = (instance.world.random.nextInt( 101 ) < fakeChance);
            boolean blockDamage = (isBelowMaxY ? WranglerMod.getConfig().getDamageBlocksBelow() : WranglerMod.getConfig( ).getDamageBlocksAbove( ) );
            
            if(triggered)
            {
                instance.playSound( SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, 1.33f, 0.85f );
                Explosion.DestructionType destructionType = (instance.world.getGameRules().getBoolean( GameRules.DO_MOB_GRIEFING) && blockDamage) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
                float f = instance.shouldRenderOverlay() ? 2.0F : 1.0F;
                instance.world.createExplosion(instance, instance.getX(), instance.getY(), instance.getZ(), 3.0f * f * multiplier, destructionType);
            }
            else
            {
                instance.playSound( SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 0.5f );
            }
            instance.discard();
        }
    }
}
