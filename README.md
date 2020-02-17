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
- **lang** - plugin language _(default: "en")_

### Languages
If you want to edit existing texts you need to edit `en.yml` or `ru.yml` on the `langs` folder

If you want to add new translation you need to create `.yml` file, copypaste the content of existing language and translate it (e.g. English to French) and change in `config.yml` **lang** property
### In addition
- If player leaves during the countdown - he loses
- Players can't hit each other during the countdown
- Other players can't hit duelists
- Maybe other players can hit with arrow/potions i didn't test

### Todo list
- [x] ~~Language support~~
- [x] ~~Permissions (don't know exactly why)~~
- [ ] Creating arenas (optional WorldGuard integration)
- [ ] Item bets
- [ ] Make plugin more flexible via config
- [ ] Builds for higher Minecraft versions
- [ ] Test arrows/potions damage
