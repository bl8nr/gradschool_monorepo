from django.urls import path
from . import views

urlpatterns = [

    # base route for the home page
    path("", views.index, name="index"),

    # route for the menu
    path("menu", views.menu, name="menu"),

    # route to show all of the current users Tickets (aka orders)
    path("tickets", views.tickets, name="tickets"),

    # route to view or perform actions on a single Ticket
    path("tickets/<uuid:ticket_id>", views.ticket, name="ticket"),

    # route to show all of a Ticket's OrderItems or add an OrderItem to a Ticket
    path("tickets/<uuid:ticket_id>/orderItems", views.orderItems, name="orderItems"),

    # route to view or perform actions on a single OrderItem
    path("tickets/<uuid:ticket_id>/orderItems/<uuid:orderItem_id>", views.orderItem, name="orderItem"),
]
