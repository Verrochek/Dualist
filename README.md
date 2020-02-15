# Dualist

### What is it
Dualist is a plugin that helps provides duel between players

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

### Configuration
In `config.yml` you can find different settings for plugin
- **verbose** - will debug information prints in console _(default: true)_
- **startDelay** - time before duel starts _(default: 10)_
- **deathKeepInventory** - should player's inventory be kept when he dies in a duel _(default: true)_
- **deathKeepLevel** - should player's level be kept when he dies in a duel _(default: true)_
- **lang** - plugin language _(default: "en")_

### In addition
- If player leave during the countdown - he will lose
- Player can't hit each other during the countdown
- Another players can't hit the player in a duel
- Maybe another players can hit with arrow/potions **i didn't test**

### Todo list
- [x] ~~Language support~~
- [ ] Permissions (don't know exactly why)
- [ ] Creating arenas (optional WorldGuard integration)
- [ ] Item bets
- [ ] Make plugin more flexible via config
- [ ] Builds for higher Minecraft versions
- [ ] Test arrows/potions damage