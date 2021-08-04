package tlod;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;

public class Bind extends CommandBase {
    public String getCommandName() {
        return "godBind";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/godBind <key>";
    }

    public List<String> getCommandAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("godBind");
        aliases.add("bindGod");
        aliases.add("bind");
        return aliases;
    }
    
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.addChatMessage((IChatComponent)new ChatComponentText("specify a key"));
        } else {
            Settings.setKey(Keyboard.getKeyIndex(args[0].toUpperCase()));
            if (Settings.getKey() == 0) {
                sender.addChatMessage((IChatComponent)new ChatComponentText("use a valid key"));
            } else {
                sender.addChatMessage((IChatComponent)new ChatComponentText("bound to " + Keyboard.getKeyName(Settings.getKey())));
            }
        }
    }
}
