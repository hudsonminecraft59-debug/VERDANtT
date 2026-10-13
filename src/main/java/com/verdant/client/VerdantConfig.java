package com.verdant.client;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public final class VerdantConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("verdant.properties");
    public static boolean storageEsp = true, spawnerFinder = true;
    public static boolean storageFill = false, spawnerFill = false;
    public static int storageColor = 0x54E6B0, spawnerColor = 0xB78CFF;
    public static int storageFillAlpha = 35, spawnerFillAlpha = 35, outlineWidth = 2, range = 48;
    public static void load() {
        Properties p = new Properties();
        try (InputStream in = Files.newInputStream(FILE)) { p.load(in); } catch (IOException ignored) { return; }
        storageEsp = Boolean.parseBoolean(p.getProperty("storageEsp", "true")); spawnerFinder = Boolean.parseBoolean(p.getProperty("spawnerFinder", "true"));
        storageFill = Boolean.parseBoolean(p.getProperty("storageFill", "false")); spawnerFill = Boolean.parseBoolean(p.getProperty("spawnerFill", "false"));
        storageColor = parseColor(p.getProperty("storageColor"), 0x54E6B0); spawnerColor = parseColor(p.getProperty("spawnerColor"), 0xB78CFF);
        storageFillAlpha = number(p, "storageFillAlpha", 35); spawnerFillAlpha = number(p, "spawnerFillAlpha", 35); outlineWidth = number(p, "outlineWidth", 2); range = number(p, "range", 48);
    }
    public static void save() {
        Properties p = new Properties(); p.setProperty("storageEsp", ""+storageEsp); p.setProperty("spawnerFinder", ""+spawnerFinder); p.setProperty("storageFill", ""+storageFill); p.setProperty("spawnerFill", ""+spawnerFill); p.setProperty("storageColor", Integer.toHexString(storageColor)); p.setProperty("spawnerColor", Integer.toHexString(spawnerColor)); p.setProperty("storageFillAlpha", ""+storageFillAlpha); p.setProperty("spawnerFillAlpha", ""+spawnerFillAlpha); p.setProperty("outlineWidth", ""+outlineWidth); p.setProperty("range", ""+range);
        try { Files.createDirectories(FILE.getParent()); try (OutputStream out = Files.newOutputStream(FILE)) { p.store(out, "Verdant client settings"); } } catch (IOException ignored) { }
    }
    private static int parseColor(String s, int fallback) { try { return (int)Long.parseLong(s, 16) & 0xFFFFFF; } catch (Exception e) { return fallback; } }
    private static int number(Properties p, String key, int fallback) { try { return Integer.parseInt(p.getProperty(key, ""+fallback)); } catch (Exception e) { return fallback; } }
}
