import uuid
from django.db import models
from django.contrib.auth.models import User

# Create your models here.

# create a model for watchlist items which belong to a user
class Watchedcoin(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    user = models.ForeignKey(User, on_delete=models.ProtectedError, related_name="watchlist")
    coinId = models.CharField(max_length=64)
    coinSymbol = models.CharField(max_length=64)
    coinImage = models.CharField(max_length=128)
    coinName = models.CharField(max_length=64)


