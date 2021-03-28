# RegionResourcePacks
Minecraft Spigot Plugin that hooks into the WorldGuard plugin allowing regions to have their own specific resource pack.

## Requirements
This plugin requires [WorldGuard](https://dev.bukkit.org/projects/worldguard) and [WorldEdit](https://dev.bukkit.org/projects/worldedit) as dependencies. It will not work if either of these two are not on your server.

To change the *resource-pack* flag, players need to have the following permission set to true: `regionresourcepacks.change`

## How to use
You need the download link for the resource pack/s you want to use. The following websites have instructions on how to do this for any custom resource packs you have created:
1. https://minecraft.fandom.com/wiki/Tutorials/Creating_a_resource_pack#Server_Resource_Packs
2. https://apexminecrafthosting.com/how-to-upload-a-resource-pack
3. https://shockbyte.com/billing/knowledgebase/81/How-to-Set-a-Resource-Pack-to-Your-Minecraft-Server.html

You can now set the *resource-pack* flag to the download link and then it will work.

**WARNING!!** If the download link is a valid format but yet points to nowhere, this might not work and the Minecraft Client will freeze. Make sure this link is correct.

## Overlapping Regions and Inheritance
The region with the highest priority is the region that gets checked for a resource-pack.

## Global Region
You can set the *resource-pack* flag for the `__global__` region and it will get used if there are no regions where the player is standing.

### Empty Resource Pack
This plugin comes with an empty resource pack called "EmptyResourcePack.zip". If your server doesn't have a server-wide resource pack in the *server.properties* file, then you'll want to use this resource pack. Follow one of the directions in the links [above](https://github.com/seanlego23/RegionResourcePacks/blob/master/README.md#how-to-use) with the empty resource pack. Then set the *resource-pack* flag for the `__global__` region in each of your worlds to that link. This allows a player who is not located in any protected regions to switch back to either classic minecraft or client-side texture packs. This prevents the need for players to relog every time they leave a region and go into the `__global__` region. **This is recommended if your server does not have a server-wide resource-pack**.
