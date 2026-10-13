# Verdant — Fabric client mod (Minecraft 1.21.11)

Verdant is a client-side Fabric mod prototype inspired by the supplied Figma reference: deep forest-green panels, mint accents, compact controls, and a three-column ClickGUI.

## Features included
- Right Shift opens/closes the ClickGUI.
- Storage ESP toggle: chests, trapped chests, ender chests, hoppers, and shulker boxes.
- Spawner Finder toggle: mob spawner blocks.
- Independent colors for Storage ESP and Spawner Finder, cycled from a small palette in the GUI.
- Configurable scan range (8–128 blocks).
- Settings saved to `.minecraft/config/verdant.properties`.
- Client-only; it does not need to be installed on a server.

## Build
Requirements: Java 21 and Gradle compatible with Fabric Loom 1.14.

From this folder run:

```bash
gradle build
```

The output JAR should be in `build/libs/`. Put the JAR in `.minecraft/mods` alongside Fabric API for Minecraft 1.21.11.

## Notes
This source project has not been compiled in the current environment because Gradle is not installed here. The 1.21.11 Fabric API/rendering signatures may require small adjustments after the first local build. The GUI contains outline-width and fill controls as a UI/config foundation; the current world renderer draws block outlines, while filled overlays and true variable line thickness are not yet wired into rendering. The palette cycles colors rather than providing a full RGB picker.
