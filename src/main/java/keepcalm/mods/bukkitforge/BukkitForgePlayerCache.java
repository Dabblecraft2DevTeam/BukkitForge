package keepcalm.mods.bukkitforge;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;

import org.bukkit.craftbukkit.v1_6_R2.CraftServer;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;

public class BukkitForgePlayerCache {

	private static final HashMap<String, CraftPlayer> playerCache = new HashMap<String, CraftPlayer>();
	
	public static CraftPlayer getCraftPlayer(EntityPlayerMP player) {
		if (playerCache.containsKey(player.username.toLowerCase()))
		{
			CraftPlayer ply = playerCache.get(player.username.toLowerCase());
			if (ply.getHandle().isDead) {
				ply.setHandle(player);
				return ply;
			}
			else {
				return ply;
			}
		}
		CraftPlayer ply = new CraftPlayer(player);
		playerCache.put(player.username.toLowerCase(), ply);
		return ply;
	}
	
	public static CraftPlayer getCraftPlayer( CraftServer server, EntityPlayerMP player) {
		return getCraftPlayer(player);
	}
	
	public static void removePlayer(String name) {
		if (playerCache.containsKey(name))
			playerCache.remove(name);
	}
	
}
