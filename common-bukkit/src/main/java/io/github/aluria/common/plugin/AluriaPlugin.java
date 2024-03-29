package io.github.aluria.common.plugin;

import co.aikar.commands.*;
import co.aikar.commands.CommandCompletions.AsyncCommandCompletionHandler;
import co.aikar.commands.CommandCompletions.CommandCompletionHandler;
import co.aikar.commands.CommandConditions.Condition;
import co.aikar.commands.CommandConditions.ParameterCondition;
import co.aikar.commands.contexts.ContextResolver;
import io.github.aluria.common.utils.CommonPlugin;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

import java.util.List;
import java.util.Locale;

public abstract class AluriaPlugin extends CommonPlugin {

    private final static Locale PORTUGUESE_BRAZILIAN = new Locale("pt", "BR");

    private PaperCommandManager commandManager;

    public PaperCommandManager getPaperCommand() {
        if (commandManager == null) {
            commandManager = new PaperCommandManager(this);
            commandManager.enableUnstableAPI("help");
            commandManager.getLocales().setDefaultLocale(PORTUGUESE_BRAZILIAN);
            commandManager.setFormat(MessageType.INFO, ChatColor.GREEN);
            commandManager.setFormat(MessageType.ERROR, ChatColor.RED);
            commandManager.setDefaultExceptionHandler(this::exceptionHandler);
        }
        return commandManager;
    }

    private boolean exceptionHandler(BaseCommand command, RegisteredCommand<?> registeredCommand, CommandIssuer sender, List<String> args, Throwable throwable) {
        sender.sendError(MessageKeys.ERROR_PERFORMING_COMMAND);
        throwable.printStackTrace();
        return false;
    }

    public void registerCommands(BaseCommand... commands) {
        final PaperCommandManager commandManager = getPaperCommand();
        for (BaseCommand command : commands) {
            commandManager.registerCommand(command);
        }
    }

    public <T> void registerDependency(@NonNull Class<T> clazz, T dependency) {
        getPaperCommand().registerDependency(clazz, dependency);
    }

    public void registerCondition(@NonNull String key, Condition<BukkitCommandIssuer> condition) {
        getPaperCommand().getCommandConditions().addCondition(key, condition);
    }

    public <T> void registerCondition(@NonNull Class<T> clazz, @NonNull String key, @NonNull ParameterCondition<T, BukkitCommandExecutionContext, BukkitCommandIssuer> handler) {
        getPaperCommand().getCommandConditions().addCondition(clazz, key, handler);
    }

    public void registerDependencies(Object... dependencies) {
        for (Object dependency : dependencies) {
            registerDependency((Class<Object>) dependency.getClass(), dependency);
        }
    }

    public void registerAsyncCompletion(@NonNull String id, @NonNull AsyncCommandCompletionHandler<BukkitCommandCompletionContext> handler) {
        getPaperCommand().getCommandCompletions().registerAsyncCompletion(id, handler);
    }

    public void registerCompletion(@NonNull String id, CommandCompletionHandler<BukkitCommandCompletionContext> handler) {
        getPaperCommand().getCommandCompletions().registerCompletion(id, handler);
    }

    public <T> void registerContextResolver(Class<T> clazz, ContextResolver<T, BukkitCommandExecutionContext> context) {
        final PaperCommandManager commandManager = getPaperCommand();
        commandManager.getCommandContexts().registerContext(
          clazz, context
        );
    }

    public void registerServices(@NonNull Object... objects) {
        for (Object object : objects) {
            registerService((Class<Object>) object.getClass(), object, ServicePriority.Normal);
        }
    }

    public <T> void registerService(Class<T> clazz, @NonNull T service, @NonNull ServicePriority priority) {
        Bukkit.getServicesManager().register(
          clazz,
          service,
          this,
          priority
        );
    }
}
