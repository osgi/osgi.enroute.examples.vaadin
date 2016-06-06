# OSGI ENROUTE EXAMPLES VAADIN PROVIDER

A proof-of-concept implementation to show how Vaadin can be used with a service model.

## How To

To use this provider, register an Application service with the `alias` property set to the web path. 
In this service provide the class of the UI and a method that can create instances.

All `VAADIN` directories in every bundle are directly mapped and merged to the web 
paths. To use the standard themes, just add the `com.vaadin.themes` to the `-runbundles`. 
As is standard in OSGi enRoute, all `static` directories are mapped as well.

## Example
See the `osgi.enroute.examples.vaadin.application` example application


