# PBControl
PBControl is a Java application used for controlling a wireless
Paintball Scoring system.

It's mainly used in the Dutch Scenario Competition (www.scenariocompetitie.nl)
and the Belgian Scenario Competition.

This software is open source, but the hardware design for the system is not.

# Building
Building is done by using Ant, simply run 'ant' in the src directory and it will
build the system:

```$ ant```

# Running
PBControl depends on the Java library RXTX to run, so on your system these libraries
have to be installed.

You can also use Ant to run the application:

```$ ant run```

This will compile the project and run it
