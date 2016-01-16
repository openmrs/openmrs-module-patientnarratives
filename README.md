OpenMRS-Module-Patient Narratives
================================

[![Build Status](https://travis-ci.org/openmrs/openmrs-module-patientnarratives.png?branch=master)](https://travis-ci.org/openmrs/openmrs-module-patientnarratives)

Overview
========

Just  think of a situation where a patient isn’t capable of attending clinic  to get proper health-care treatment. But he/she might have the Internet  facility at their homes or may be at their neighbourhoods. So in that  case If the person could get medical advice through internet that would  be really cool, isnt it ?
 
So  that’s where the Patient Narratives Module’s idea comes from. The  care-seeking patient can simply login to the publicly hosted OpenMRS  system and Navigate to Patient Narratives interface and fill, upload the  required information (simply they could just say their story) submit  it! And wait for the confirmation from Care-providers who are generously  waiting to provide health-care support for them.User-driven open source healthcare system: OpenMRS-core with the Patient Narratives Module installed on top.


Browser Compatibility
=====================

With Video Recording functionality
Google Chrome 29 + (only)

Without Video Recording functionality
Google Chrome 18 + (Recommended)
Firefox 3.5 +
Internet Explorer 9 +
Opera
SafariSafari


Requirements
============

OpenMRS 1.9.3 (Strongly recommend upgrading to OpenMRS 1.9 as there may be some instability if you are running OpenMRS 1.7.x, OpenMRS 1.8.x)
HTML form entry module: version 2.1.3 (If you are using OpenMRS 1.9+ you should also install the htmlformentry19ext module.)
Important: use HTMLform entry version: 2.1.3, the latest versions doesnt work properly. [HTML-498]
Xforms module: version 4.2.3.0

Roles and Privileges
====================

Administrators need to apply following privileges for particular user roles to function the module properly.

Add Patient Narratives: Able to submit Patient Narratives <-- This privilege will be needed to submit Patient Narratives, So the Admin need to add this to even Anonymous role.

Manage Patient Narratives: Able to manage the Patient Narratives <-- The users who are privileged with this one will be able to see the care provider console and manage it. So basically Provider role should bear this privilege.

Configure Patient Narratives: Able to admin the Patient Narratives Module <-- The users who are privileged with this will able to configure Patient Narratives Module settings. (Changing Forms, Change default patient Id, default encounter Id, etc)

Downloads
=========
Installation
============

To install the Patient Narratives Module download the distribution .omod package from here and upload it into your OpenMRS system


Check out our wiki for more information: https://wiki.openmrs.org/x/IAIzAw

Issues/Tickets: https://tickets.openmrs.org/browse/PNM
