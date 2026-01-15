package me.rampo.testproject;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import me.rampo.testproject.commands.HomeCommand;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class TestProject extends JavaPlugin {
    public TestProject(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        getLogger().at(Level.INFO).log("Plugin started");
        this.getCommandRegistry().registerCommand(new HomeCommand(getLogger()));
    }

    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("Plugin stopped");
    }

}
