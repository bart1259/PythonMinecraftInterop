package net.bart1259.pymcinteropmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.*;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import org.python.util.PythonInterpreter;

public class PyMCInteropMod implements ModInitializer {

	public static final String MOD_ID = "pymcinteropmod";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private PyFileManager fileManager;
	private PyScriptExecutor scriptExecutor;

    public static LiteralArgumentBuilder<ServerCommandSource> literal(String name) {
        return LiteralArgumentBuilder.<ServerCommandSource>literal(name);
    }

    public static <T> RequiredArgumentBuilder<ServerCommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.<ServerCommandSource, T>argument(name, type);
    }

	private void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			// This code is run to intialize commandss
			dispatcher.register(literal("py")
				.then(argument("path", string())
				.executes(ctx -> {
						String fileName = getString(ctx, "path");
						System.out.println("Path is " + fileName);
						if(fileManager.fileExists(fileName)) {
							try {
								String srcCode = fileManager.getFileContents(fileName);
								String result = scriptExecutor.execute(ctx, srcCode);
								ctx.getSource().sendMessage(Text.literal(result));
							} catch (IOException e) {
								ctx.getSource().sendMessage(Text.literal("An error occured while reading " + fileName));
								return 1;
							}

						} else {
							ctx.getSource().sendMessage(Text.literal("Could not find script " + fileName));
						}
						return 1;
					})
				)
			);
        });
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		String scriptDir = FabricLoader.getInstance().getGameDir().toString() + "\\pyscripts\\";
		fileManager = new PyFileManager(LOGGER, scriptDir);
		scriptExecutor = new PyScriptExecutor(LOGGER);
		registerCommands();
	}
}
