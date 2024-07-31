Once permissions granted, we start foreground service, blocking notifications also when app in background, of THIS app (for testing purposes) plus the fetched list of packages. 

setupPeriodicNotification() triggers notif so we can see logs of blocking, even when app in background.

Encrypt and Decrypt of logs for extra security, with secret keys in config.json, which is a sensitive and secure external file that in prod would not be part of git.
