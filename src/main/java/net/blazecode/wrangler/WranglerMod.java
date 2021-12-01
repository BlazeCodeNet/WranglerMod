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
		@Comment("Toggles the entire mod on or off")
		boolean enabled = true;

		@Comment("Chance of a creeper to be a 'fake' between 0-100")
		int fake_chance = 66;

		@Comment("Maximum Y value that creepers can explode. Setting to 0 disables this feature")
		int max_y_explosion = 50;

		public boolean getEnabled()
		{
			return enabled;
		}
		public int getFakeChance()
		{
			return fake_chance;
		}
		public int getMaxYExplosion() { return max_y_explosion; }
	}
}
