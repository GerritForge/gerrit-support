# Gerrit-Support Plugin

Collect information on Gerrit Code Review in order to have enough
elements to request support.

## How to build

Gerrit-Support plugins is written in Scala language and built with Sbt.
To build the plugin you need to have SBT 0.13.13 installed and then
run the ``assembly`` target.

Example:

```
   $ sbt assembly
   [...]
   [info] Packaging /Users/lucamilanesio/gerrithub/gerrit-support/target/scala-2.11/gerrit-support.jar ...
```

