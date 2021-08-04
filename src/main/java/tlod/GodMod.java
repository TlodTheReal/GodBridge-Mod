package tlod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = "godbridgemod", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class GodMod {
    public static final String MODID = "godbridgemod";

    public static final String VERSION = "1.0";

    public static GodBot godBot;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        godBot = new GodBot();
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand((ICommand)new Bind());
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        if ((Minecraft.getMinecraft()).theWorld == null || (Minecraft.getMinecraft()).thePlayer == null)
            return;
        try {
            if (Keyboard.isCreated() &&
                    Keyboard.getEventKeyState()) {
                int keyCode = Keyboard.getEventKey();
                if (keyCode <= 0)
                    return;
                if (Settings.getKey() == keyCode && keyCode > 0)
                    Settings.toggle();
            }
        } catch (Exception q) {
            q.printStackTrace();
        }
    }
}