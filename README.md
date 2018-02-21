# README #

This is a repository for custom view displaying HiFamily dailies.

## Usage ##
The only thing you have to do is include HiFamilyDailiesView in your xml and initialize it with the help of method
`init(currentIndex: Int, allDailiesCount: Int, availableDailies: Map<String, String>)`

Parameters for the method

* currentIndex - this is the index of currently selected daily message
* allDailiesCount - thie is the amount of all dailies available in Database
* availableDailies - this is a map of dailies available for the user (user gets more dailies every day). This map maps headers (like date) to daily messages.

## Properties ##
You can change couple of this view's attributes.

* app:header - this is a boolean value stating whether to show headers or not
* app:header_color - this is the color resource for header text
* app:header_text_size - this is the size of header text
* app:dailies_color - this is the color resource for daily message text
* app:dailies_text_size - this is the size of daily message text
* app:carousel_drawable - this is the resource for carousel item's drawable

## Additional view ##
You can add additional views displaying between carousel and dailies (i.e. rating popup).
To do that instantiate view and pass it on in method
`addAdditionalView(view : View)`

New view will simply be added beneath the carousel in vertical order.
To remove all additional views call method
`removeAdditionalViews()`

## Listeners ##
HiFamilyDailiesView provides additional listeners that you can attach.

* OnDailyElementTouchedListener = (element: DailyElement) -> Unit
* DailySelectedListener = (index: Int) -> Unit

First listener can be attached via method
`addOnTouchListener(listener: OnDailyElementTouchedListener)`
This listener returns to you the information about the method of 'scrolling' that user has chosen. If he touches carousel to use fast scroll, this listener will return DailyElement.CAROUSEL. 
If he touches dailies view to use normal scroll, this listener will return DailyElement.DAILIES.
To remove those listeners just use method
`clearOnTouchListeners()`

Second listener can be attached via method
`addOnDailySelectedListener(listener: DailySelectedListener)`
This listener returns the index of currently selected daily.
To remove it simply call
`clearDailySelectedListeners()`

## Other ##
You can define which daily is currently selected by using method
`getCurrentDailyIndex() : Int`