from django.contrib import admin
from .models import MenuCategory, MenuItem, MenuItemModifier, Ticket, OrderItem


# create inline OrderItems so that they can be viewed from Tickets and Shopping carts
class OrderItemsInline(admin.TabularInline):
    model = OrderItem


# filter the Ticket admin model that way Tickets admin section wont show non-submitted tickets
class TicketAdmin(admin.ModelAdmin):
    list_display = ('__str__', 'isCompleted', 'submittedOn', 'subTotal')
    inlines = [
        OrderItemsInline
    ]

    def get_queryset(self, request):
        qs = super(TicketAdmin, self).get_queryset(request)
        return qs.filter(isSubmitted=True)


# create a proxy for Tickets as ShoppingCart that way the Ticket model can be registered twice
class ShoppingCart(Ticket):
    class Meta:
        proxy = True


# filter the Ticket admin model that way ShoppingCarts admin section only show non-submitted tickets
class ShoppingCartAdmin(TicketAdmin):
    list_display = ('__str__', 'subTotal')

    def get_queryset(self, request):
        qs = super(TicketAdmin, self).get_queryset(request)
        return qs.filter(isSubmitted=False)


# register models for the django admin ui
admin.site.register(MenuCategory)
admin.site.register(MenuItem)
admin.site.register(MenuItemModifier)
admin.site.register(OrderItem)
admin.site.register(Ticket, TicketAdmin)
admin.site.register(ShoppingCart, ShoppingCartAdmin)