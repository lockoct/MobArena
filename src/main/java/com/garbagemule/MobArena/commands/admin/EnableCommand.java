package com.garbagemule.MobArena.commands.admin;

import com.garbagemule.MobArena.Msg;
import com.garbagemule.MobArena.commands.Command;
import com.garbagemule.MobArena.commands.CommandInfo;
import com.garbagemule.MobArena.framework.Arena;
import com.garbagemule.MobArena.framework.ArenaMaster;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandInfo(
    name    = "enable",
    pattern = "enable|on",
    usage   = "/ma enable",
    desc    = "enable MobArena or individual arenas",
    permission = "mobarena.admin.enable"
)
public class EnableCommand implements Command
{
    @Override
    public boolean execute(ArenaMaster am, CommandSender sender, String... args) {
        // Grab the argument, if any.
        String arg1 = (args.length > 0 ? args[0] : "");
        
        if (arg1.equals("all")) {
            for (Arena arena : am.getArenas()) {
                enable(arena, sender);
            }
            return true;
        }
        
        if (!arg1.equals("")) {
            Arena arena = am.getArenaWithName(arg1);
            if (arena == null) {
                am.getGlobalMessenger().tell(sender, Msg.ARENA_DOES_NOT_EXIST);
                return true;
            }
            enable(arena, sender);
            return true;
        }
        
        am.setEnabled(true);
        am.saveConfig();
        am.getGlobalMessenger().tell(sender, "MobArena " + ChatColor.GREEN + "enabled");
        return true;
    }
    
    private void enable(Arena arena, CommandSender sender) {
        arena.setEnabled(true);
        arena.getPlugin().saveConfig();
        arena.getGlobalMessenger().tell(sender, "Arena '" + arena.configName() + "' " + ChatColor.GREEN + "enabled");
    }
}
