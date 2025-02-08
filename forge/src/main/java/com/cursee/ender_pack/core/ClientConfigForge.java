package com.cursee.ender_pack.core;

import com.cursee.ender_pack.platform.Services;
import com.cursee.monolib.util.toml.Toml;

import java.io.File;
import java.io.PrintWriter;

public class ClientConfigForge {

    public static void onLoad() {
        File configDir = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
        configDir.mkdirs();

        File configFile = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config" + File.separator + "enderpack-client.toml");

        if (configFile.exists()) read(configFile);
        else write(configFile);
    }

    static void read(File file) {
        Toml toml = new Toml().read(file);
        ClientConfiguredValues.RENDERS_IN_ARMOR_SLOT = toml.getBoolean("renders_in_armor_slot");
        ClientConfiguredValues.RENDERS_IN_EXTRA_SLOT = toml.getBoolean("renders_in_extra_slot");
    }

    static void write(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("renders_in_armor_slot = true");
            writer.println("# setting to false disables rendering from chestplate slot");
            writer.println("renders_in_extra_slot = true");
            writer.println("# setting to false disables rendering from curios/trinkets slot");
        }
        catch (Exception ignored) {}
    }
}
