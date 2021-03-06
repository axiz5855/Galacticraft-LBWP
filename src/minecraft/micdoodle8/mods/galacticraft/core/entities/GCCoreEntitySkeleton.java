package micdoodle8.mods.galacticraft.core.entities;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Copyright 2012-2013, micdoodle8
 *
 *  All rights reserved.
 *
 */
public class GCCoreEntitySkeleton extends EntitySkeleton
{
    /** The ItemStack that any Skeleton holds (a bow). */
    private static final ItemStack defaultHeldItem = new ItemStack(Item.bow, 1);

    public GCCoreEntitySkeleton(World par1World)
    {
        super(par1World);
        this.texture = "/micdoodle8/mods/galacticraft/core/client/entities/skeleton.png";
        this.moveSpeed = 0.25F;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, this.moveSpeed));
        this.tasks.addTask(4, new GCCoreEntityAIArrowAttack(this, this.moveSpeed, 1, 20));
        this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
    }

	@Override
    public void onUpdate()
    {
        this.motionY += 0.06F;

    	super.onUpdate();

    	this.fallDistance = 0;
    }

	@Override
    public void fall(float var1)
    {
		;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	public boolean isAIEnabled()
    {
        return true;
    }

    @Override
	public int getMaxHealth()
    {
        return 20;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
	protected String getLivingSound()
    {
        return "mob.skeleton";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
	protected String getHurtSound()
    {
        return "mob.skeletonhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
	protected String getDeathSound()
    {
        return "mob.skeletonhurt";
    }

    @Override
	@SideOnly(Side.CLIENT)

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return GCCoreEntitySkeleton.defaultHeldItem;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
	public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
        {
            final float var1 = this.getBrightness(1.0F);

            if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F)
            {
                this.setFire(8);
            }
        }

        super.onLivingUpdate();
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
	public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getSourceOfDamage() instanceof EntityArrow && par1DamageSource.getEntity() instanceof EntityPlayer)
        {
            final EntityPlayer var2 = (EntityPlayer)par1DamageSource.getEntity();
            final double var3 = var2.posX - this.posX;
            final double var5 = var2.posZ - this.posZ;

            if (var3 * var3 + var5 * var5 >= 2500.0D)
            {
                var2.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
	protected int getDropItemId()
    {
        return Item.arrow.itemID;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(3 + par2);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.arrow.itemID, 1);
        }

        var3 = this.rand.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.bone.itemID, 1);
        }
    }

    @Override
	protected void dropRareDrop(int par1)
    {
        if (par1 > 0)
        {
            final ItemStack var2 = new ItemStack(Item.bow);
            EnchantmentHelper.addRandomEnchantment(this.rand, var2, 5);
            this.entityDropItem(var2, 0.0F);
        }
        else
        {
            this.dropItem(Item.bow.itemID, 1);
        }
    }
}
