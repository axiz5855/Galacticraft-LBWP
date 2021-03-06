package micdoodle8.mods.galacticraft.mimas;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

/**
 * Copyright 2012-2013, micdoodle8
 *
 *  All rights reserved.
 *
 */
public class GCMimasConfigManager
{
	public static boolean loaded;

	static Configuration configuration;

	// DIMENSIONS
	public static int dimensionIDMimas;

	// BLOCKS
	public static int idBlock;

	public GCMimasConfigManager(File file)
	{
		if (!GCMimasConfigManager.loaded)
		{
			GCMimasConfigManager.configuration = new Configuration(file);
			this.setDefaultValues();
		}
	}

	private void setDefaultValues()
    {
		try
		{
	        GCMimasConfigManager.configuration.load();

	        GCMimasConfigManager.dimensionIDMimas = 				GCMimasConfigManager.configuration.get("Dimensions", 										"Mimas Dimension ID",				-23)		.getInt(-23);

	        GCMimasConfigManager.idBlock = 						GCMimasConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, 						"idBlockMimas", 					198)	.getInt(198);
		}
		catch (final Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Galacticraft Mimas has a problem loading it's configuration");
		}
		finally
		{
			GCMimasConfigManager.configuration.save();
			GCMimasConfigManager.loaded = true;
		}
    }
}
