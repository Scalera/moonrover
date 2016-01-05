# moonrover

[![Build Status](https://travis-ci.org/Scalera/moonrover.svg?branch=master)](https://travis-ci.org/Scalera/moonrover)
[![Coverage Status](https://coveralls.io/repos/Scalera/moonrover/badge.svg?branch=master&service=github)](https://coveralls.io/github/Scalera/moonrover?branch=master)

Upcoming Scalera's next challenge!

## How to run

Just execute ```sbt ~fastOptJS``` and you'll be able to simulate your rovers' landing at
```
http://localhost:12345/display/target/scala-2.11/classes/index.html
```

If you want to make changes at your rover program definition,
just change ```scalera.moonrover.display.Launch.scala``` contents and refresh the web browser page.

## Extra documentation

* [Hands-on scala](http://lihaoyi.github.io/hands-on-scala-js/)
* [Scalajs-dom](http://scala-js.github.io/scala-js-dom/)
* [Workbench-example](https://github.com/lihaoyi/workbench-example-app)