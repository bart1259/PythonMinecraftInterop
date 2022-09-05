package net.bart1259.pymcinteropmod;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.server.command.ServerCommandSource;
import pymcinterop.Minecraft;

public class PyScriptExecutor {

    Logger logger;
    PythonInterpreter pyInterp;

    public PyScriptExecutor(Logger logger) {
        this.logger = logger;
        pyInterp = new PythonInterpreter();
        pyInterp.exec("\n");
    }

    public String execute(CommandContext<ServerCommandSource> ctx, String sourceCode) {

        try {
            Minecraft.context = ctx;

            long start = System.nanoTime();
            pyInterp.exec(sourceCode);
            long end = System.nanoTime();
            Object x = pyInterp.get("x");
            logger.info("x: " + x);

            return ((end - start) / 1e+9) + "s elapsed";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return e.getMessage();
        }
    }
}