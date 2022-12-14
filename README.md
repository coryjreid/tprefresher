# Touch Portal Refresher

This is a simple (and my first) plugin for [Touch Portal](https://www.touch-portal.com/). It simply generates a 
random number on a set interval and stores that state. This allows you to specify a "refresh interval" which you can 
use to trigger certain updates on that interval.

## Example

I have buttons for all my OBS scenes. Whichever one is active causes that scene's button to get an animated border 
telling me which scene is active. This border is a simple event "When OBS active scene 'is' changed to 'scene name'" 
(and the inverse to remove the border).

This works well when you're pressing buttons causing a scene switch which causes the event to fire. What I'd like is 
Touch Portal to figure out which scene is selected on _launch_ and highlight that button immediately.

This plugin will allow me to set an interval to "update" the button and the button can use a simple if/else to 
verify and set state accordingly, allowing me to independently refresh button state without receiving special events 
from other plugins.

## Concerns

I'm unsure how badly this will perform over time if there are a large number of buttons refreshing in this way. I 
only plan to use it on a handful of buttons which shouldn't be too bad.

If you encounter problems please open an Issue and let me know.