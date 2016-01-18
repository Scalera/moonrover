# moonrover

[![Build Status](https://travis-ci.org/Scalera/moonrover.svg?branch=master)](https://travis-ci.org/Scalera/moonrover)
[![Coverage Status](https://coveralls.io/repos/Scalera/moonrover/badge.svg?branch=master&service=github)](https://coveralls.io/github/Scalera/moonrover?branch=master)

This is the source code for Scalera's first challenge.

## From Earth to the moon

The ESA (European Space Agency) wants to send two robots (rovers) to the Moon.
They are fitted with a sophisticated landing support: a parachute.
The problem here is that it seems both robots won’t land at the same place…
Once they’ve landed at the Moon, which has a discrete surface of infinite size, both rovers must find each other to start the mission together.

The challenge consists on programming the rovers using a limited command set, so they finally manage to find each other.
Keep in mind that the same program will be loaded on both robots and they don’t have any programatic way to know which their identifiers are.

## DSL for manouver

The command set that will be used for programming the rovers is composed of:

* MOVE <direction> : It moves the rover a discrete space unit to the left or to the right.

* IF FOUND PARACHUTE <command> : If the rover finds a parachute on the floor, at its feet wheels, then execute the
given command.

* NOP: Rover does nothing…

* GOTO <lineId> : It jumps to the command placed at the given line.

## How to define the Rover program

You can define your rover program at ```moonrover/display/src/main/scala/scalera/moonrover/Launch.scala``` file.

It should look similar to this:

```scala
package scalera.moonrover

import scalera.moonrover.RoverProgram._
import scalera.moonrover.interpreter.Program

/**
 * Define here the program that
 * your rovers must execute
 */
object Launch {

  val program = Program(
    1 -> RIGHT,
    2 -> GOTO(1))
}

```

Remember that all possibilities when defining the program are:

* RIGHT : Move right.
* LEFT : Move left.
* GOTO(lineId) : Jump to program's lineId.
* NOP : Do nothing
* \`IF FOUND PARACHUTE\`(command) : If the rover finds a parachute, execute another command.

## How does the simulator looks like?

![example](/doc/moonrover_example.png)

The most remarkable things are:
* At top-right you'll find the number of ticks have passed so far.
* At middle-center you'll find the distance between both rovers.
* At the bottom of the page you'll see both rovers' program execution (current execution line is highlighted with
blue color).

## Run it

Just execute ```sbt ~fastOptJS``` and you'll be able to simulate your rovers' landing at
```
http://localhost:12345/display/target/scala-2.11/classes/index.html
```

If you want to make changes at your rover program definition,
just change ```scalera.moonrover.Launch.scala``` contents, save the file and refresh the web browser page.
NB: Sometimes it's necessary to refresh with ```Ctrl``` + ```F5```.

If you want to change the simulation speed, you can do it at the ```scalera.moonrover.display.Config``` at line:

```scala
lazy val millisInterval = 1000
```

## Extra documentation

* [Hands-on scala](http://lihaoyi.github.io/hands-on-scala-js/)
* [Scalajs-dom](http://scala-js.github.io/scala-js-dom/)
* [Workbench-example](https://github.com/lihaoyi/workbench-example-app)
