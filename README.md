## Introduction

In RPG(Role-playing game), turn-based fighting is a main component for both the game and players. In the game, our player always controller 2-4 persons, which has differenct magics and skills. The number of enemy is also uncertain. However in a boss fighting, the enemy always is only one: the boss himself, with strong HP&MP, abundant attack skills and multiple perks. A classical RPG fighting is always like: 

![legend of hero](https://i.imgur.com/M4QEqcL.jpg)

 Our goal is to train a AI model which can help us to play a RPG fighting automatically. As we know, turn-based fighting is a asymmetric game, which means the task is somehow like GO. Therefore we can try to use MCTS-based algorithm on this task.


## MCTS RPG simulator

In detail, we design a minimize game simulator, which has two friend members and a enemy-boss.


## Install & Run

To run the game:
```
cd rein

gradle run
```

You will see the game state information in each turn on the console.

![game log](https://i.imgur.com/cKkGQWn.jpg)

Happy hacking!

## Roadmap

* [x] A minimize game simulator implementation
* [x] A vanilla MCTS algorithm
* [ ] More complex game simulator: more skills, 
* [ ] MCTS variants: parallel optimization 
