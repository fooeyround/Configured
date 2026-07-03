# Configured


A mod with several small tweaks that aim at giving the server admin more finite control of the server.
You can disable travel to the end, nether, have fake hardcore hearts, toggle the "Join-ability" of your server for everyone but ops so that you can stay in a sort of maintenance mode without needing to change permissions.

Syntax: 
the `/config configured` command is used to control the settings in game, the configuration is stored in `./config/configured/config.json` relative to the server's directory.

### Examples:
  - `/config configured fakeHardcore set true`
  - `/config configured itemDespawnAge reset`
  - `/config configured playerConnections set ALLOW_ALL`
  - `/config configured disablePVP get`


## Settings:

*All default values should show vanilla behavior. If that is not the case, please submit a bug report.*

**motd** - Set the motd of the server on the fly.

**fakeHardcore** - Make all player's hearts show up as hardcore (does not affect gameplay).

**playerConnections** - Control who can join the server, `ALLOW_ALL` is vanilla / default, `ALLOW_OPS` allows ops, no one else, `ALLOW_ONLY_NON_BLOCKED` uses the `playerConnectionBlockList` to determine who to block.

**disablePlayerConnectionsJoinMessage** - This is the message shown to the players who are barred from joining.

**disableEnd** - Toggle the functionality of End Portals.

**disableEndPortalFrameFilling** - Disables the interaction of Eyes of Ender with End Portal Frames. 

**disableEyeOfEnderCasting** - Stops players from being able to cast Eyes of Ender.

**disableEndGateways** - Disables teleporting of any entity through End Gateways, disabling their full functionality.

**itemDespawnAge** - By default Minecraft sets this to 5 minutes, or 6000 ticks. Set the item despawn age *in ticks*

**simulationDistance** - Set the server's simulation distance on the fly.

**viewDistance** - Set the server's view distance on the fly.

**maxPlayers** - Set the max player count on the fly.

**maxPlayersFakeListing** - Set the max player count in the server listing and /list command; does not affect real max player count.

**spawnProtection** - Set the server's overworld spawn protection on the fly.

**playerDamageMultiplier** - Set a multiplier for some groups of damage types to players.
> This is useful for nerfing or buffing some game mechanics. \
>  The damage types that can currently be editable are as follows:
> - `end_crystal` Modify end crystal damage for players
> - `bad_respawn_point` Modify respawn anchor/bed damage for players
> - `tnt_minecart` Modify TNT Minecart damage for players


**playerCombatCooldown** - Set the duration of the combat timer applied when players engage in PvP. Set *in ticks*, `0` disables the combat cooldown entirely.

**playerCombatCooldownForAttacker** - Apply the combat cooldown to the attacking player when they damage another player.

**playerCombatCooldownForVictim** - Apply the combat cooldown to the victim player when they are damaged by another player.

**playerCombatCooldownShowInActionBar** - Display the remaining combat cooldown time in the player's action bar while they are in combat.

**playerCombatDisableElytra** - Prevent players in combat from using Elytra flight while the combat cooldown is active.

**playerCombatDisableElytraFireworkRockets** - Prevent players in combat from using Firework Rockets to boost Elytra flight while the combat cooldown is active.
