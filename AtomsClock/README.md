# AtmosClock




## Version Initial Commit:
Pushed initial code for clock 

## Version 2.0:
Added functionality which updates weather data on clock after completing that hour

Added functionality which imports 2 days work of weather data so clock can show data of next day after midnight

Added functionality which update data at 12am everyday so clock can run forever

Clock now uses feels like temp in f rather that actual temp

Fixed bugs with clock showing incorrect temp and precipitation


## Version 3.0 
Used BufferStratagy to stop clockface from flashing after every second

Implemented Runnable to take program off of main thread and run more smoothly

Used color class to greatly optimize code and increase readability

Stopped unnecessary API calls

Fixed incorrect data from parsing only through first day

## Version 4.0 
Program asks for location inside of window instead of terminal

Completely eliminated flashing 

Added date to the clock display

Added executable jar file support to clock and made first release

Fixed bug not able to change to different faces caused by update v3.0


## Version 5.0
Added Option to change location in interface

Clock now shows local time of the city or region instead of computer

Added Error message when inputing invalid city or ZIP code

Added button to show further forecast from 12 to 24 hours in the future

Added enter key shortcut to enter button

Fixed bug where cities with two or more words did not work properly

Fixed bug where midnight refresh did not work

## Future

Automatically also gets location 

