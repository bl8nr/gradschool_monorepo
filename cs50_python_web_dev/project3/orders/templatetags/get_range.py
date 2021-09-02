from django.template import Library

register = Library()

# simple get range template filter allowing an iteration of a for loop X number of times
# this is used to re render the 'MenuItemModifiers' HTML option selector for the amount (int)
# of which a MenuItem supports modifiers (ie 2 topping pizza supports 2 modifiers, requires 2 HTML selects)
@register.filter
def get_range(value):
    return range(value)
