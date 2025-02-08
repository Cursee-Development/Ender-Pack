package com.cursee.ender_pack.core;

import com.cursee.ender_pack.platform.Services;
import com.cursee.monolib.util.toml.Toml;

import java.io.File;
import java.io.PrintWriter;

public class ServerConfigFabric {

    public static void onLoad() {
        File configDir = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
        configDir.mkdirs();

        File configFile = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config" + File.separator + "enderpack-common.toml");

        if (configFile.exists()) read(configFile);
        else write(configFile);
    }

    static void read(File file) {
        Toml toml = new Toml().read(file);
        ServerConfiguredValues.EXTRA_SLOT_ONLY = toml.getBoolean("extra_slot_only");
    }

    static void write(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("extra_slot_only = false");
            writer.println("# setting to true will only allow the curios/trinkets slot to work");
        }
        catch (Exception ignored) {}
    }
}
