# Cheerville Zombie Simulator

![Project Image](https://braydonwang.github.io/cheerville.png)

> This is my Cheerville Zombie Simulator created for my ICS3U6 course

---

## Description

A tile-map, zombie simulator that consists of humans, zombies, plants and poison fungi. The user can click on a human to turn them into a zombie and zombies infect other humans within their radius. Plants and poison fungi are consumed by humans and zombies for health. All entities randomly traverse the map. Everything is designed to replicate a real ecosystem.

### In-depth Overview

 1. There are four main organisms on the map: Humans, zombies, plants and poison fungus
 2. Humans and zombies move one tile per turn and lose one health per turn as well
 3. Plants and poison fungus can gain or lose health depending on their age (plants in the growing stage gain health, while in the decaying stage, they lose health)
 4. Humans gain health by eating plants, while zombies gain health by eating poison fungus
 5. Humans are created when they collide with an opposite gender human and are both above the age of 20 (every turn = 5 years)
 6. The human that is created spawns in an empty space around their creator, but if all spaces are occupied, no human is created
 7. After creating a human, the female cannot produce anymore for another 9 turns
 8. Zombies can either infect humans and kill them when they collide
 9. Humans that eat poison fungus turn into zombies
 10. Humans and zombies can see one tile around them, which gives them better movement
 11. Humans will avoid zombies and poison fungus, and will go towards plants
 12. Plants spawn at a constant rate depending on the input and have a greater chance of spawning near other plants
 13. Initially, no zombies are on the map, but clicking a human will replace the human with a zombie
 14. The strongest human and zombie are displayed in the program, as well as a bar graph that shows the number of plants, humans and zombies at any given time
 15. All organisms are represented with an image and humans/zombies have a direction that they face based on their movement
 16. Hovering your cursor over any organism will display the basic statistics of it
 17. All organisms die once they reach a health of zero, but when humans become extinct, the program stops

#### Language

- Java
