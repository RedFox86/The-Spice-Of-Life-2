**The Spice of Life 2**

This is a port of the classic mod The Spice of Life which was originally made for 1.12.2.

This mod encourages a diverse diet of foods by punishing the player for eating the same food 
repeatedly. The hunger and saturation of a given food will diminish as you eat that food multiple 
times. By default, the player will remember the last 30 foods that you have eaten, so after eating 
other foods the nutrition value of a food will eventually come back.

**Customization**

This mod comes with a common config file located in your instances config folder (config/spiceoflife-
common.toml)

In the config file, you are able to modify the way the mod works. At the present, there are two possible config options that you are able to change:
- Nutrition Decay - A List of floats that control the rate at which hunger and saturation decay at.
For example, [1.0, 1.0, 0.5, 0.0] would mean that the first three times you eat a food, it will
retain 100% of its nutrition value, the fourth time would have 50%, and the fifth would have 0%.
- Max History - The maximum amount of food that the player will remember before foods reset. For
example, if max history is 10, it will take 10 different foods to reset the nutrition value
completely for any given food.

**Compatability**

This mod is currently compatible with AppleSkin

AppleSkin provides a large amount of information about nutrition to your hud, particularly the amount of hunger and saturation that a given food will restore. This mod will adjust that accordingly and the HUD will display the correct information.

**FAQ**

Q: "I have a suggestion/bug report/request"
A: Please create an issue tracker on the [Github page](https://github.com/RedFox86/The-Spice-Of-Life-2)

Q: "Fabric please!!!"
A: Not right now

Q: Can I use your mod in my modpack?
A: Of course! :P

**Credit**

- [The Spice of Life](https://www.curseforge.com/minecraft/mc-mods/the-spice-of-life) - Original idea and logo
- [The Spice of Life: Classic Edition](https://www.curseforge.com/minecraft/mc-mods/foodvariations) - Tutorial for using mixins

Report bugs on the issue tracker at [github.com/RedFox86/The-Spice-Of-Life-2/issues](https://github.com/RedFox86/The-Spice-Of-Life-2/issues)
