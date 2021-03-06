package micdoodle8.mods.galacticraft.mars.wgen;

import java.util.Random;

import micdoodle8.mods.galacticraft.mars.blocks.GCMarsBlocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Copyright 2012-2013, micdoodle8
 *
 *  All rights reserved.
 *
 */
public class GCMarsCavern
{
    protected int range = 8;

    protected Random rand = new Random();

    protected World worldObj;

    public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, int[] par5ArrayOfByte)
    {
        final int var6 = this.range;
        this.worldObj = par2World;
        this.rand.setSeed(par2World.getSeed());
        final long var7 = this.rand.nextLong();
        final long var9 = this.rand.nextLong();

        for (int var11 = par3 - var6; var11 <= par3 + var6; ++var11)
        {
            for (int var12 = par4 - var6; var12 <= par4 + var6; ++var12)
            {
                final long var13 = var11 * var7;
                final long var15 = var12 * var9;
                this.rand.setSeed(var13 ^ var15 ^ par2World.getSeed());
                this.recursiveGenerate(par2World, var11, var12, par3, par4, par5ArrayOfByte);
            }
        }
    }

    protected void recursiveGenerate(World par1World, int xChunkCoord, int zChunkCoord, int origXChunkCoord, int origZChunkCoord, int[] par6ArrayOfByte)
    {
        this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);

        if (this.rand.nextInt(15) != 0)
        {
        }

        for (int var8 = 0; var8 < 1; ++var8)
        {
            final double xPos = xChunkCoord * 16 + this.rand.nextInt(16);
            final double yPos = (double)this.rand.nextInt(10) + 15;
            final double zPos = zChunkCoord * 16 + this.rand.nextInt(16);

            if (this.rand.nextInt(22) == 0)
            {
                this.generateLargeCaveNode(this.rand.nextLong(), origXChunkCoord, origZChunkCoord, par6ArrayOfByte, xPos, yPos, zPos);
                this.rand.nextInt(4);
            }
        }
    }

    protected void generateLargeCaveNode(long par1, int origXChunkCoord, int origZChunkCoord, int[] par5ArrayOfByte, double xPos, double yPos, double zPos)
    {
        this.generateCaveNode(par1, origXChunkCoord, origZChunkCoord, par5ArrayOfByte, xPos, yPos, zPos, 1.0F + this.rand.nextFloat() * 6.0F, 10.0F, 10.0F, -1, -1, 0.2D);
    }

    protected void generateCaveNode(long par1, int origXChunkCoord, int origZChunkCoord, int[] par5ArrayOfByte, double xPos, double yPos, double zPos, float par12, float par13, float par14, int par15, int par16, double heightMultiplier)
    {
        final double var19 = origXChunkCoord * 16 + 8;
        final double var21 = origZChunkCoord * 16 + 8;
        float var23 = 0.0F;
        float var24 = 0.0F;
        final Random var25 = new Random(par1);

        if (par16 <= 0)
        {
            final int var26 = this.range * 16 - 16;
            par16 = var26 - var25.nextInt(var26 / 4);
        }

        boolean var54 = false;

        if (par15 == -1)
        {
            par15 = par16 / 2;
            var54 = true;
        }

        final int var27 = var25.nextInt(par16 / 2) + par16 / 4;

        for (final boolean var28 = var25.nextInt(6) == 0; par15 < par16; ++par15)
        {
            final double caveWidth = 40;
            final double caveHeight = caveWidth * heightMultiplier;

            if (var28)
            {
                par14 *= 0.92F;
            }
            else
            {
                par14 *= 0.7F;
            }

            par14 += var24 * 0.1F;
            par13 += var23 * 0.1F;
            var24 *= 0.9F;
            var23 *= 0.75F;
            var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
            var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;

            if (!var54 && par15 == var27 && par12 > 1.0F && par16 > 0)
            {
                return;
            }

            if (var54 || var25.nextInt(4) != 0)
            {
                final double var35 = xPos - var19;
                final double var37 = zPos - var21;
                final double var39 = par16 - par15;
                final double var41 = par12 + 2.0F + 16.0F;

                if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41)
                {
                    return;
                }

                if (xPos >= var19 - 16.0D - caveWidth * 2.0D && zPos >= var21 - 16.0D - caveWidth * 2.0D && xPos <= var19 + 16.0D + caveWidth * 2.0D && zPos <= var21 + 16.0D + caveWidth * 2.0D) //CHECKED
                {
                    int caveMinX = MathHelper.floor_double(xPos - caveWidth) - origXChunkCoord * 16 - 1;
                    int caveMaxX = MathHelper.floor_double(xPos + caveWidth) - origXChunkCoord * 16 + 1;
                    int caveMinY = MathHelper.floor_double(yPos - caveHeight) - 1;
                    int caveMaxY = MathHelper.floor_double(yPos + caveHeight) + 1;
                    int caveMinZ = MathHelper.floor_double(zPos - caveWidth) - origZChunkCoord * 16 - 1;
                    int caveMaxZ = MathHelper.floor_double(zPos + caveWidth) - origZChunkCoord * 16 + 1;

                    if (caveMinX < 0)
                    {
                    	caveMinX = 0;
                    }

                    if (caveMaxX > 16)
                    {
                    	caveMaxX = 16;
                    }

                    if (caveMinY < 1)
                    {
                    	caveMinY = 1;
                    }

                    if (caveMaxY > 65)
                    {
                    	caveMaxY = 65;
                    }

                    if (caveMinZ < 0)
                    {
                    	caveMinZ = 0;
                    }

                    if (caveMaxZ > 16)
                    {
                    	caveMaxZ = 16;
                    }

                    boolean isBlockWater = false;
                    int var42;
                    int var45;

                    for (var42 = caveMinX; !isBlockWater && var42 < caveMaxX; ++var42)
                    {
                        for (int var43 = caveMinZ; !isBlockWater && var43 < caveMaxZ; ++var43)
                        {
                            for (int var44 = caveMaxY + 1; !isBlockWater && var44 >= caveMinY - 1; --var44)
                            {
                                var45 = (var42 * 16 + var43) * 128 + var44;

                                if (var44 >= 0 && var44 < 128)
                                {
                                    if (par5ArrayOfByte[var45] == (byte)GCMarsBlocks.bacterialSludgeMoving.blockID || par5ArrayOfByte[var45] == (byte)GCMarsBlocks.bacterialSludgeStill.blockID)
                                    {
                                        isBlockWater = true;
                                    }

                                    if (var44 != caveMinY - 1 && var42 != caveMinX && var42 != caveMaxX - 1 && var43 != caveMinZ && var43 != caveMaxZ - 1)
                                    {
                                        var44 = caveMinY;
                                    }
                                }
                            }
                        }
                    }

                    if (!isBlockWater)
                    {

                    }
                    for (var42 = caveMinX; var42 < caveMaxX; ++var42)
                    {
                        final double var59 = (var42 + origXChunkCoord * 16 + 0.5D - xPos) / caveWidth;

                        for (var45 = caveMinZ; var45 < caveMaxZ; ++var45)
                        {
                            final double var46 = (var45 + origZChunkCoord * 16 + 0.5D - zPos) / caveWidth;
                            int var48 = (var42 * 16 + var45) * 128 + caveMaxY;

                            boolean var49 = false;

                            if (var59 * var59 + var46 * var46 < 1.0D)
                            {
                                for (int var50 = caveMaxY - 1; var50 >= caveMinY; --var50)
                                {
                                    final double var51 = (var50 + 0.5D - yPos) / caveHeight;

                                    if (var51 > -0.7D && var59 * var59 + var51 * var51 + var46 * var46 < 1.0D)
                                    {
                                        final int var53 = par5ArrayOfByte[var48];

                                        if (var53 == GCMarsBlocks.marsGrass.blockID)
                                        {
                                            var49 = true;
                                        }

                                        if (var53 == GCMarsBlocks.marsStone.blockID || var53 == GCMarsBlocks.marsDirt.blockID || var53 == GCMarsBlocks.marsGrass.blockID)
                                        {
                                            if (isBlockWater)
                                            {
                                                par5ArrayOfByte[var48] = GCMarsBlocks.bacterialSludgeStill.blockID;
                                            }
                                            else
                                            {
                                                par5ArrayOfByte[var48] = 0;

                                                if (var49 && par5ArrayOfByte[var48 - 1] == (byte) GCMarsBlocks.marsDirt.blockID)
                                                {
                                                    par5ArrayOfByte[var48 - 1] = (byte) GCMarsBlocks.marsGrass.blockID;
                                                }
                                            }
                                        }
                                    }

                                    --var48;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
