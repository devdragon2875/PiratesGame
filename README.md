# Pirates README
Anantajit Subrahmanya, Devansh Mishra, and Blake Randle

## Introduction
Do you enjoy games that make you filthy rich, but have no effect on the real world? Do you get sadistic pleasure when you destroy something someone has been upgrading for hours? Well, Pirates will provide just that. Starting off with some basic supplies, you must venture out into a new world and cleverly trade items with the local merchants in order to gain gold. But be careful! There are others, those who are as bloodthirsty and ruthless as you are, who want to trade with the local merchants. You must fight your way through enemy ships and utilize your gold to upgrade your ship in order to survive the ruthless seas. Welcome to the world of Pirates…

## Instructions

### Start

Click the START button on the menu screen and enter the ip of the server you want to connect to.

### Setup 

Type in the Server code in the textbox, and press the enter button to start the game. An overview of the game play is provided in the panel below the game.

### Gameplay

The boat with a triangle above it is your boat. You can control the rowing speed of the boat with the W/UP and S/DOWN keys. The rudder can be 
controled by the A/LEFT and D/RIGHT keys. Pressing the 'M' or SHIFT key triggers Map subview, explained later in the README.
Clicking on the green sections of the boat will fire the cannons. You cannot fire sections that are red (reloading).

When the player interacts with docks, they go into the Trade/Upgrade subview, explained later in the README.

User health is displayed at the top left of the screen.

#### Map (subview)
Displays an overlay with the map of the nearby area you are able to see.

#### Trade/Upgrade (subview)
Allows for trading to get materials and allows the player to upgrade their ship.

## Feature Summary

### Must have
- Processing 2D graphics with simple textures
- Randomly generated maps with different tiles
- Player is able to buy/sell different goods at ports (will adjust prices)
- Player is able to battle with other ships (computers)
- Players can win through win conditions/time limit?
- UI for hp, cargo, gold, etc.
- Menu screen with instructions

### Want to have
- Networked Multiplayer
- Upgrades for ship (more hp/damage/storage)
- Complicated combat with sections of ship (similar to ftl)
- Misc extras (treasure maps, events to affect goods prices)

### Stretch Goals
- Variety of different starting ships
- Upgrades including adding new sections to ship
- Some sort of crew system, crew members can boost certain sections 

### Responsibilities 
- Anantajit Subrahmanya
    - Networking
- Devansh Mishra
    - Boat Sections and Weapons
- Blake Randle
    - Trading and Cargo
- Feedback given by Jose Amador and Ben Leistiko

### Class List
- Animation
- Block
- Boat
- Bullet
- BulletNet
- Button
- Cargo
- CentralServer
- Client
- ClientLoop
- Dock
- DrawingSurface
- Gun
- LookoutSection
- Main
- MapDrawer
- MapGenerator
- Menu
- NetworkedDock
- Noise
- Particle
- Player
- Section
- ServerUI
- SteerSection
- SubServer
- TextReader
- TradePart
- TradeScreen
- UpgradePart
- UpgradeScreen
- WeaponSection
