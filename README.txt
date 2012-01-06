This is the documentation for the We Are Here Now project.

Purpose
-------
This program is currently designed to query the Foursquare service for venues with people currently
at those locations. These venues and values are then populated in a local database to be queried by
a Processing applet.

Sponsor
-------
This project is part of the Spatial Design Lab at Columbia University (c) 2012.

NB. At this point, this code is under heavy development and not for general use. Do not use without
permission from SIDL.


Version Information
-------------------
Version 0.1 - January 6, 2012

Initial development. The goals of this release was to get something up and running, and hopefully
resilient to failures. There are many known issues tracked in TODO.txt. The system is designed to
be configured through the .properties files under the resources.

The initial database has this structure:

`uniq_venues` (
  `vid` varchar(30) NOT NULL PRIMARY KEY,
  `name` text NOT NULL,
  `x` decimal(12,5) NOT NULL,
  `y` decimal(12,5) NOT NULL,
  `cat1` text,
  `cat2` text,
  `herenow` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `timestamp` (`timestamp`)
)


There are indexes on the 'vid' and 'timestamp' attributes, respectively.