import decimal
from django.shortcuts import render, redirect
from django.views.decorators.http import require_http_methods
from django.contrib.auth.decorators import login_required
from django.utils import timezone
from .models import MenuCategory, MenuItem, Ticket, MenuItemModifier

# GET request to view the home page
@require_http_methods(["GET"])
def index(request):

    # create a context for the page that includes the menu and maybe shopping cart
    context = {
        "menu": getMenu(),
        "shoppingCart": None
    }

    # if theres an authenticated user, then include a shopping cart in the context
    if request.user.is_authenticated:
        context["shoppingCart"] = getShoppingCartByUser(request.user)

    # render the index template
    return render(request, "index.html", context)

# GET request to view a menu category
@login_required()
@require_http_methods(["GET"])
def menu(request):

    # get the category id param and fetch a menu category of that ID param
    categoryId = request.GET.get('category', '')
    menuCategory = MenuCategory.objects.get(id=categoryId)

    # create a context for the page that includes the menu, menuCategory and users shopping cart
    context = {
        "menu": getMenu(),
        "shoppingCart": getShoppingCartByUser(request.user),
        "menuCategory": menuCategory
    }

    # render the menu page to show that menu category and its menu items
    return render(request, "menu.html", context)

# POST request to add an OrderItem to a Ticket
@login_required()
@require_http_methods(["POST"])
def orderItems(request, ticket_id):

    # get the menuItem from the menuItemId ID in the POSTs form, add that to the targeted Ticket as an OrderItem
    menuItem = MenuItem.objects.get(id=request.POST.get('menuItemId', ''))
    ticket = Ticket.objects.get(id=ticket_id)
    orderItem = ticket.orderItems.create(menuItemId=menuItem, totalItemPrice=menuItem.price)

    # iterate through the POSTs form, looking for legit customizations/modifiers, add those to the OrderItem and save it
    for i in range(menuItem.maximumModifiersAllowed):
        modifierIdFromTemplateForm = request.POST.get(f'customization{i}', '')
        orderItem.modifiers.add(MenuItemModifier.objects.get(id=modifierIdFromTemplateForm))
        orderItem.save()

    # recalculate the OrderItems total price to include its modifiers price adjustments and save it
    for modifier in orderItem.modifiers.all():
        orderItem.totalItemPrice = orderItem.totalItemPrice + modifier.priceAdjustment
        orderItem.save()

    # recalculate the entire ticket subtotal and save it
    updateTicketSubtotal(ticket)

    # redirect the user to the Ticket page of the Ticket they just added an OrderItem to (shopping cart, basically)
    return redirect("ticket", ticket_id=ticket_id)

# POST request to delete an OrderItem from a Ticket
@login_required()
@require_http_methods(["POST"])
def orderItem(request, ticket_id, orderItem_id):

    # get the method param from the POSTs form, get the Ticket and OrderItem associated with the endpoint
    method = request.POST.get('method', '')
    ticket = Ticket.objects.get(id=ticket_id)
    orderItem = ticket.orderItems.get(id=orderItem_id)

    # as long at the POST form method param is DELETE, then delete the OrderItem
    # this is how DELETE is done while not being able to send DELETE natively from an HTML form
    if method == 'DELETE':
        orderItem.delete()

    # recalculate the entire ticket subtotal and save it
    updateTicketSubtotal(ticket)

    # redirect the user to the Ticket page of the Ticket they just deleted an OrderItem from (shopping cart, basically)
    return redirect("ticket", ticket_id=ticket_id)

# GET request to view all of the current users Tickets
@login_required()
@require_http_methods(["GET"])
def tickets(request):

    # fetch all of the Tickets belonging to the currently authorized user
    tickets = Ticket.objects.filter(customerId=request.user)

    # create a context for the page that includes the menu and the users shopping cart and tickets
    context = {
        "menu": getMenu(),
        "shoppingCart": getShoppingCartByUser(request.user),
        "tickets": tickets
    }

    # render the tickets template page
    return render(request, "tickets.html", context)

# GET to view a tickets details
# POST to checkout a shopping cart ticket
@login_required()
@require_http_methods(["GET", "POST"])
def ticket(request, ticket_id):

    # get the Ticket associated with the endpoint and update the subtotal one last time
    ticket = Ticket.objects.get(id=ticket_id)
    updateTicketSubtotal(ticket)

    # if the req is using a POST
    if request.method == "POST":
        action = request.POST.get('action', '')

        # and if the action is checkout, mark the Ticket as submitted at now and create a new shopping cart for the user
        if action == "checkout":
            ticket.isSubmitted = True
            ticket.submittedOn = timezone.localtime(timezone.now())
            ticket.save()
            getShoppingCartByUser(request.user)

            # refresh the page to the Ticket they just checked out
            return redirect("ticket", ticket_id=ticket.id)

    # if the req is using a GET, create a context for the page that includes the menu, users shopping cart and ticket
    context = {
        "menu": getMenu(),
        "shoppingCart": getShoppingCartByUser(request.user),
        "ticket": Ticket.objects.get(id=ticket_id)
    }

    # render the Ticket details template
    return render(request, "ticket.html", context)


# get the menu categories from the database
def getMenu():
    menu = {
        "menuCategories": MenuCategory.objects.all()
    }
    return menu


# get the users shopping cart ticket from the database, create a shopping cart if one does not already exist
def getShoppingCartByUser(user):
    shoppingCartTicket = Ticket.objects.filter(customerId=user, isSubmitted=False)

    if not shoppingCartTicket:
        Ticket.objects.create(customerId=user)
        shoppingCartTicket = Ticket.objects.filter(customerId=user, isSubmitted=False)

    # update the Ticket subtotal whenever the cart is fetched, to be sure the subtotal is accurate
    updateTicketSubtotal(shoppingCartTicket[0])

    return shoppingCartTicket[0]


# calculate and save a Tickets subtotal by iterating through all of a Ticket's OrderItems and adding up their costs
def updateTicketSubtotal(ticket):
    newSubtotal = decimal.Decimal(0.00)

    for orderItem in ticket.orderItems.all():
        newSubtotal = newSubtotal + orderItem.totalItemPrice

    ticket.subTotal = newSubtotal
    return ticket.save()
