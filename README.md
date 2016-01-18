# Openfire File Transfer Disabler plugin

This project is a plugin for the [Openfire Realtime Collaboration Server](http://www.igniterealtime.org/projects/openfire/).

This plugin attempts to disable file transfers between XMPP clients, by rejecting
[Stream Initiation](http://xmpp.org/extensions/xep-0096.html) stanzas for the File Transfer Profile.

## Building the source
To create an Openfire plugin from the source code in this project:

- Download a copy of the sources of this project into `PLUGINDIR`
- Download a copy of the [Openfire sources](https://github.com/igniterealtime/Openfire) into `OPENFIRE_SOURCE_DIR`
- From within `OPENFIRE_SOURCE_DIR` execute: `ant -f build/build.xml -Dplugin.src.dir=PLUGINDIR/.. -Dplugin=file-trf-dsblr plugin`

## Installing the plugin

After a plugin JAR file has been created, place that file in the plugin directory of a running Openfire instance.
Openfire will automatically detect and activate the plugin.

More detailed usage instructions are available in the [readme](readme.html) file that is available as part of the source
code of this project.

## Project history
This project is a fork of the (apparently defunct) project at http://sourceforge.net/projects/file-trf-dsblr/ The source
code from it's version 1.0.1 is the base of the fork.

A more detailed [change log](changelog.html) is available as part of the source code of this project.

