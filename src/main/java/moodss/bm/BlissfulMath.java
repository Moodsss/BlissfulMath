package moodss.bm;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

@Mod("bm")
public class BlissfulMath
{
    private static BlissfulMathConfig CONFIG;
    private static Logger LOGGER;

    public BlissfulMath() {
        LOGGER = LoggerFactory.getLogger("BlissfulMath");

        var configPath = FMLPaths.CONFIGDIR.get()
                .resolve("blissful-math-config.json");

        loadConfig(configPath);
    }

    private static void loadConfig(Path configPath)
    {
        try
        {
            CONFIG = BlissfulMathConfig.load(configPath);
        }
        catch (Throwable t)
        {
            LOGGER.error("Failed to load configuration file for BlissfulMath", t);

            var result = TinyFileDialogs.tinyfd_messageBox("Minecraft",
                    """
                    The configuration file for BlissfulMath could not be loaded.
                    
                    Path: %s
                    Error: %s
                    
                    The file may be corrupted or in a newer format that cannot be used by this version.
                    
                    If you continue with launching the game, the configuration file will be restored to
                    known-good defaults, and any changes you may have made will be lost.
                    
                    Additional debugging information has been saved to your game's log file.
                    """.formatted(configPath, StringUtils.truncate(t.getMessage(), 90)),
                    "okcancel",
                    "warning",
                    false);

            if (!result) {
                LOGGER.info("User asked us to not reset their config file, re-throwing error");
                throw t;
            }

            CONFIG = BlissfulMathConfig.defaults(configPath);

            try
            {
                CONFIG.writeChanges();
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Failed to replace configuration file with known-good defaults", ex);
            }
        }
    }

    public static BlissfulMathConfig config()
    {
        if (CONFIG == null)
        {
            throw new IllegalStateException("Config not yet available");
        }

        return CONFIG;
    }
}
