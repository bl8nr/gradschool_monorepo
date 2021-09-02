import uuid
from django.db import models
from django.contrib.auth.models import User

# create choices available for sizing of menuItems
SIZE_CHOICES = [
    ('Small', 'Small'),
    ('Medium', 'Medium'),
    ('Large', 'Large'),
    ('Extra Large', 'Extra Large'),
    ('One Size', 'One Size')
]


# create a MenuCategory model
# this determines what menu/pages are available in the navbar that MenuItems live under
class MenuCategory(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=28)
    description = models.CharField(max_length=128)

    def __str__(self):
        return f"{self.name}"


# create a MenuItemModifier model
# this represents toppings and extras that could be added to an OrderItem
class MenuItemModifier(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=64)
    priceAdjustment = models.DecimalField(default=0.00, decimal_places=2, max_digits=5)

    def __str__(self):
        return f"{self.name} [{self.priceAdjustment}]"


# create a MenuItem model
# this represents and item on the menu
class MenuItem(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=64)
    description = models.CharField(max_length=128)
    category = models.ForeignKey(MenuCategory, on_delete=models.ProtectedError, related_name="menuItems")
    price = models.DecimalField(default=0.00, decimal_places=2, max_digits=5)
    size = models.CharField(max_length=16, choices=SIZE_CHOICES, default='None')
    maximumModifiersAllowed = models.IntegerField()
    supportedModifiers = models.ManyToManyField(MenuItemModifier, blank=True, related_name="supportedMenuItems")

    def __str__(self):
        return f"{self.name} [{self.category.name}] [{self.size}]"


# create a Ticket model
# this represents an order placed by the user (if isSubmitted is True) or their shopping cart (if isSubmitted is False)
class Ticket(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    customerId = models.ForeignKey(User, on_delete=models.CASCADE, related_name="customer")
    subTotal = models.DecimalField(default=0.00, decimal_places=2, max_digits=5)
    isSubmitted = models.BooleanField(default=False)
    submittedOn = models.DateTimeField(null=True)
    isCompleted = models.BooleanField(default=False)

    def __str__(self):
        if self.isCompleted is True:
            return f"Order ticket for {self.customerId.username} [Completed]"
        if self.isSubmitted is True:
            return f"Order ticket for {self.customerId.username} [In Progress]"
        else:
            return f"Shopping Cart for {self.customerId.username}"


# create a OrderItem model
# this represents a part of an order that belongs to a Ticket and determines a MenuItem with MenuItemModifiers
class OrderItem(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    menuItemId = models.ForeignKey(MenuItem, on_delete=models.ProtectedError)
    modifiers = models.ManyToManyField(MenuItemModifier, blank=True, related_name="modifiers")
    ticketId = models.ForeignKey(Ticket, on_delete=models.CASCADE, related_name="orderItems")
    totalItemPrice = models.DecimalField(default=0.00, decimal_places=2, max_digits=5)

    def __str__(self):
        return f"{self.menuItemId.name} order item for ticket {self.ticketId.id}"
