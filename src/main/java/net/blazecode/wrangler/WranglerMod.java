package net.blazecode.wrangler;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment( EnvType.SERVER )
public class WranglerMod implements DedicatedServerModInitializer
{

	public static final String MODID = "wrangler";

	@Override
	public void onInitializeServer( )
	{
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
	}

	public static ModConfig getConfig()
	{
		if (config == null)
		{
			config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		}
		return config;
	}

	private static ModConfig config;

	@Config(name = MODID)
	public static class ModConfig implements ConfigData
	{
		@Comment("Creepers damage blocks above max_y?")
		boolean damage_blocks_above = false;
		
		@Comment("Creepers damage blocks below max_y?")
		boolean damage_blocks_below = true;
		
		@Comment("Chance of a creeper to be a 'fake' between 0-100% ABOVE the max_y level")
		int fake_chance_above = 66;
		
		@Comment("Chance of a creeper to be a 'fake' between 0-100% BELOW the max_y level")
		int fake_chance_below = 66;
		
		@Comment("Explosion multiplier between 0-1(float) ABOVE max_y level")
		float explosion_multi_above = 1.5f;
		
		@Comment("Explosion multiplier between 0-1(float) BELOW max_y level")
		float explosion_multi_below = 1.5f;
		
		@Comment("Maximum Y value that creepers can explode. Setting to 0 disables this feature")
		int y_threshold = 40;

		public boolean getDamageBlocksAbove() { return damage_blocks_above; }
		public boolean getDamageBlocksBelow() { return damage_blocks_below; }
		
		public int getFakeChanceAbove() { return fake_chance_above; }
		public int getFakeChanceBelow() { return fake_chance_below; }
		
		public float getMultiplierAbove() { return explosion_multi_above; }
		public float getMultiplierBelow() { return explosion_multi_below; }
		
		public int getYThreshold() { return y_threshold; }
	}
}
