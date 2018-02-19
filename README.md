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
You can add additional view displaying between carousel and dailies (i.e. rating popup).
To do that instantiate view and pass it on in method
`addAdditionalView(view : View)`