package me.rampo.testproject.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.rampo.testproject.util.Home;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.logging.Level;

public class HomeCommand extends CommandBase {

    @Nonnull
    private final RequiredArg<String> type;

    private final HytaleLogger logger;

    Map<UUID, Home> homes = new HashMap();

    public HomeCommand(HytaleLogger logger){
        super("home","test home");
        this.type = this.withRequiredArg("type", "Test", ArgTypes.STRING);
        this.logger = logger;

    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        if(commandContext.isPlayer()){
            Player p = (Player) commandContext.sender();
            String commandType = type.get(commandContext);
            Ref<EntityStore> ref = p.getReference();
            Store<EntityStore> store = ref.getStore();
            World world = store.getExternalData().getWorld();
            world.execute(()->{
                PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                if (playerRef == null) return;
                UUID uuid = playerRef.getUuid();
                logger.at(Level.SEVERE).log("player ref found");
                TransformComponent transformComponent = store.getComponent(ref, TransformComponent.getComponentType());
                HeadRotation headRotationComponent = store.getComponent(ref, HeadRotation.getComponentType());
                Vector3d position = transformComponent.getPosition().clone();
                Vector3f headRotation = headRotationComponent.getRotation().clone();

                if(commandType.equalsIgnoreCase("SET")){

                    Vector3f rotation = transformComponent.getRotation();

                    Home home = new Home(position, rotation, headRotation);
                    logger.at(Level.SEVERE).log(home.toString());

                    homes.put(uuid, home);
                    p.sendMessage(Message.raw("Bravo hai settato la casa"));
                }
                if (commandType.equalsIgnoreCase("GO")) {
                    Home home = homes.get(uuid);
                    if (home == null) return;
                    Teleport teleport = new Teleport(
                            home.getPosition(),
                            home.getRotation()
                    ).withHeadRotation(home.getHeadRotation());
                    store.addComponent(ref, Teleport.getComponentType(), teleport);
                }
            });

        }
    }
}
