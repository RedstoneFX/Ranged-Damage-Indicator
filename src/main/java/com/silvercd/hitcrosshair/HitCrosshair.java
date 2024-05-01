package com.silvercd.hitcrosshair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = HitCrosshair.MODID, name = HitCrosshair.NAME, version = HitCrosshair.VERSION)
public class HitCrosshair
{
    public static final String MODID = "hitcrosshair";
    public static final String NAME = "HitCrosshair";
    public static final String VERSION = "1.0";

    public static ResourceLocation hitCrosshair;

    private static Logger logger;
    private static Minecraft mc;
    private static int counter;
    private static boolean isCritical;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        counter = 0;
        isCritical = false;
        mc = Minecraft.getMinecraft();
        logger = event.getModLog();
        hitCrosshair = new ResourceLocation(MODID, "textures/gui/crosshair.png");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onScreenUpdate(RenderGameOverlayEvent.Post event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
            if(counter>0) {
                int x = event.getResolution().getScaledWidth() / 2 - 15; // Получаем ширину экрана, делим её пополам и вычитаем 15
                int y = event.getResolution().getScaledHeight() / 2 - 15; // Получаем высоту экрана, делим её пополам и вычитаем 15
                mc.getTextureManager().bindTexture(hitCrosshair);
                mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 32, 32);
                counter--;
            }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onEvent(LivingHurtEvent event)
    {
        Entity entitysource = event.getSource().getTrueSource();
        if (entitysource instanceof EntityPlayerMP)
        {
            if (entitysource.getUniqueID().equals(mc.player.getUniqueID()) && event.getSource().isProjectile())
            {
                // TODO: custom play sound
                // TODO: Entity killed detection
                //logger.info("Player hit entity by projectile!");
                //mc.world.playSound(mc.player, mc.player.getPosition(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.AMBIENT, 1, 1);
                counter = 8;
            }
        }
    }


    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

}
