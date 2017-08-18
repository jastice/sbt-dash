 [ ![Download](https://api.bintray.com/packages/jastice/sbt-plugins/sbt-dash/images/download.svg) ](https://bintray.com/jastice/sbt-plugins/sbt-dash/_latestVersion)
[![Build Status](https://travis-ci.org/jastice/sbt-dash.svg?branch=master)](https://travis-ci.org/jastice/sbt-dash)

# sbt-dash

Create [Dash](https://kapeli.com/dash) docsets with sbt.


## Usage

This plugin only works under macOS with sbt 1.0.0+

Install the plugin in `project/plugins.sbt`:

    addSbtPlugin("works.mesh" % "sbt-dash" % "1.0")
    
Generate Dash docset on the sbt shell:

    > dashDocset
    
The result of the task is the `File` of the generated docset, which
is placed as `projectName.docset` in the `target/scala-<version>/` directory.
