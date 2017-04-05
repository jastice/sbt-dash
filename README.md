# sbt-dash

Create [Dash](https://kapeli.com/dash) docsets with sbt


## Usage

This only works on macOS.

Install the plugin in `project/plugins.sbt`:

    addSbtPlugin("works.mesh" % "sbt-dash" % "0.1")
    
Generate Dash docset on the sbt shell:

    > dashDocset
    
The result of the task is the `File` of the generated docset, which
is placed as `projectName.docset` in the `target/scala-<version>/` directory.
