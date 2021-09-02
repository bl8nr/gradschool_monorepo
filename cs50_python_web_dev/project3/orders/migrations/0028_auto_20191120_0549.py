# Generated by Django 2.2.7 on 2019-11-20 05:49

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('orders', '0027_shoppingcart'),
    ]

    operations = [
        migrations.AlterField(
            model_name='orderitem',
            name='modifiers',
            field=models.ManyToManyField(blank=True, related_name='modifiers', to='orders.MenuItemModifier'),
        ),
    ]