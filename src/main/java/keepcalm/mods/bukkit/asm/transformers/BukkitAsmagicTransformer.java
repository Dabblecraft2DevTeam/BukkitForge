package keepcalm.mods.bukkit.asm.transformers;

import com.eoware.asm.asmagic.AsmagicClassTransformer;

import keepcalm.mods.bukkit.asm.BukkitASMLoader;
import keepcalm.mods.bukkit.asm.replacements.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.tileentity.*;

public class BukkitAsmagicTransformer implements IClassTransformer {

    protected HashMap<String,String> classes = null;

    protected HashMap<String,String> createClassesToTransform() {
        HashMap<String,String> classes = new HashMap<String,String>();

        repl = new HashMap<String, String>();

        addClassNameAndAlias(classes, "net.minecraftforge.common.DimensionManager", null, DimensionManager_BukkitForge.class);
        addClassNameAndAlias(classes, "cpw.mods.fml.common.network.NetworkRegistry", null, NetworkRegistry_BukkitForge.class);
        addClassNameAndAlias(classes, "net.minecraft.network.NetServerHandler", "jh", NetServerHandler_BukkitForge.class);
        addClassNameAndAlias(classes, "net.minecraft.entity.EntityTracker", "it", EntityTracker_BukkitForge.class);
        addClassNameAndAlias(classes, "net.minecraft.world.WorldServer", "iz", WorldServer_BukkitForge.class);
        addClassNameAndAlias(classes, "net.minecraft.world.Explosion", "zw", Explosion_BukkitForge.class);
        addClassNameAndAlias(classes, "net.minecraft.network.packet.Packet18Animation", "ct", Packet18Animation_BukkitForge.class);
        
        this.addClasses(classes);
        
        return classes;
    }

    protected HashMap<String,String> repl = null;
    
    public void addClasses(HashMap classes) {
    	
    	addClassNameAndAlias(classes, "net.minecraft.block.Block", "apw", Block.class);
    	
    	addClassNameAndAlias(classes, "net.minecraft.entity.EntityLivingBase", "oe", EntityLivingBase.class);
    	addClassNameAndAlias(classes, "net.minecraft.entity.player.InventoryPlayer", "uc", InventoryPlayer.class);
    	addClassNameAndAlias(classes, "net.minecraft.entity.item.EntityMinecartContainer", "su", EntityMinecartContainer.class);
    	
    	addClassNameAndAlias(classes, "net.minecraft.inventory.IInventory", "mn", IInventory.class);
    	addClassNameAndAlias(classes, "net.minecraft.inventory.InventoryBasic", "mt", InventoryBasic.class);
    	addClassNameAndAlias(classes, "net.minecraft.inventory.InventoryCrafting", "vj", InventoryCrafting.class);
    	addClassNameAndAlias(classes, "net.minecraft.inventory.InventoryCraftResult", "wb", InventoryCraftResult.class);
    	addClassNameAndAlias(classes, "net.minecraft.inventory.InventoryLargeChest", "mm", InventoryLargeChest.class);
    	addClassNameAndAlias(classes, "net.minecraft.inventory.InventoryMerchant", "vx", InventoryMerchant.class);
    	
    	addClassNameAndAlias(classes, "net.minecraft.tileentity.TileEntityBeacon", "art", TileEntityBeacon.class);
    	addClassNameAndAlias(classes, "net.minecraft.tileentity.TileEntityBrewingStand", "aru", TileEntityBrewingStand.class);
    	addClassNameAndAlias(classes, "net.minecraft.tileentity.TileEntityChest", "arv", TileEntityChest.class);
    	addClassNameAndAlias(classes, "net.minecraft.tileentity.TileEntityDispenser", "arz", TileEntityDispenser.class);
    	addClassNameAndAlias(classes, "net.minecraft.tileentity.TileEntityFurnace", "asd", TileEntityFurnace.class);
    	addClassNameAndAlias(classes, "net.minecraft.tileentity.TileEntityHopper", "asf", TileEntityHopper.class);
    	
    }

    protected void addClassNameAndAlias( HashMap<String,String> map, String className, String obfName, Class clss )
    {
        if( obfName != null )
        {
            map.put(obfName, clss.getName());
            //repl.put(clss.getCanonicalName().replace(".", "/"), obfName.replace(".", "/"));
        }
        else
        {
            map.put(className, clss.getName());
            //repl.put(clss.getCanonicalName().replace(".", "/"), className.replace( ".", "/"));
        }
    }

    AsmagicClassTransformer act = null;

    public BukkitAsmagicTransformer()
    {
        classes = createClassesToTransform();
        System.out.println("Got this Far!");
        //repl.put( "iv", "keepcalm/mods/bukkit/CraftNetServerHandler" );
        act = new AsmagicClassTransformer(classes, new HashMap<String, String>(), new HashMap<String, String>());
    }

    @Override
    public byte[] transform(String s, String arg1, byte[] bytes) {

        if( s.contains("Asmagic") ) return bytes;

        if( !classes.containsKey(s) ) return bytes;

        System.out.println( "Transforming " + s + " using " + classes.get(s) + " !test!");
        byte[] newClass = act.transform(s, bytes);

        if( bytes.length == newClass.length )
        {
            System.out.println( "Transforming " + s + " using " + classes.get(s) + " -- Size unchanged, likely not changed" );
        }
        return newClass;
    }

    public static void writeByteArrayToFile( String strFilePath, byte[] bytes ) {

        try
        {
            FileOutputStream fos = new FileOutputStream(strFilePath);
            fos.write(bytes);
            fos.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("FileNotFoundException : " + ex);
        }
        catch(IOException ioe)
        {
            System.out.println("IOException : " + ioe);
        }
    }
}
