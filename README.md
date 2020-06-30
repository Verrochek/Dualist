# Dualist

### What is it
A duel plugin for minecraft

### Usage

- `/duel <nickname> <bet>`
- `/duel accept`
- `/duel decline`
- `/duel help`

### Permissions

- `dualist.use`
- `dualist.help`

### Requirements
Dualist requires [Vault](https://www.spigotmc.org/resources/vault.34315/) to support economy **(you basically can't launch plugin without it)**

Now Dualist supports Minecraft versions **1.12**

Available languages: **English, Русский**

### Configuration
In `config.yml` you can find different settings for plugin
- **verbose** - will debug information prints in console _(default: true)_
- **startDelay** - time before duel starts _(default: 10)_
- **deathKeepInventory** - determines whether player's inventory will be kept if he loses in a duel (default: true)
 _(default: true)_
- **deathKeepLevel** - determines whether player's level will be kept if he loses in a duel _(default: true)_
- **damageFromOtherPlayer** - can dueslists damage another players _(default: false)_
- **maxCallDistance** - determines whether max distance between player to user command _(default: 10)_
- **maxDistance** - determines whether max distance between player to end duel in a draw _(default: 35)_
- **lang** - plugin language _(default: "en")_
- **preventUsingCommands** - prevents using **any** command by player _(default: true)_
- **preventTeleport** - prevents teleporting from plugin/server and some other reasons _(default: true)_

### Languages
If you want to edit existing texts you need to edit `en.yml` or `ru.yml` on the `langs` folder

If you want to add new translation you need to create `.yml` file, copypaste the content of existing language and translate it (e.g. English to French) and change in `config.yml` **lang** property

### In addition
- If player leaves during the countdown - he loses
- If players will teleport to another world - duel ends in a draw
- Players can't hit each other during the countdown
- Other players can't hit duelists (if **damageFromOtherPlayer** is false)

### Todo list
- [x] ~~Option that disallows using commands in duel~~
- [x] ~~Option that disallows teleporting from plugins~~
- [ ] Timer (and displaying the remaining time in boss bar)
- [ ] Creating arenas (optional WorldGuard integration)
- [ ] Item bets
- [ ] Support higher Minecraft versions
- [x] ~~Add switching to survival gamemode if player in creative~~
- [ ] Exporting duel history in .db file (SQLite)
- [x] ~~Test arrows/potions damage~~
- [x] ~~Make plugin more flexible via config~~
- [x] ~~Language support~~
- [x] ~~Permissions (don't know exactly why)~~


### Support
![Jetbrains](https://cdn.jsdelivr.net/npm/@jetbrains/logos@1.1.8/jetbrains/jetbrains.svg) 

Supported with license by [Jetbrains opensource](jetbrains.com/opensource) 

