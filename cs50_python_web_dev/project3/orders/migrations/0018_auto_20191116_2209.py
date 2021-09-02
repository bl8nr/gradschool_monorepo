# Generated by Django 2.2.7 on 2019-11-16 22:09

from django.db import migrations, models
import uuid


class Migration(migrations.Migration):

    dependencies = [
        ('orders', '0017_auto_20191113_0420'),
    ]

    operations = [
        migrations.CreateModel(
            name='MenuItemModifier',
            fields=[
                ('id', models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=64)),
                ('description', models.CharField(max_length=128)),
                ('priceAdjustment', models.DecimalField(decimal_places=2, default=0.0, max_digits=5)),
            ],
        ),
        migrations.RemoveField(
            model_name='menuitemxvariations',
            name='variationOf',
        ),
        migrations.RenameField(
            model_name='menuitem',
            old_name='menuCategory',
            new_name='category',
        ),
        migrations.RemoveField(
            model_name='menuitem',
            name='maxCustomizations',
        ),
        migrations.RemoveField(
            model_name='menuitem',
            name='variationOf',
        ),
        migrations.AlterField(
            model_name='menuitem',
            name='description',
            field=models.CharField(max_length=128),
        ),
        migrations.AlterField(
            model_name='menuitem',
            name='id',
            field=models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False),
        ),
        migrations.AlterField(
            model_name='menuitem',
            name='price',
            field=models.DecimalField(decimal_places=2, default=0.0, max_digits=5),
        ),
        migrations.AlterField(
            model_name='menuitem',
            name='size',
            field=models.CharField(choices=[('Small', 'Small'), ('Medium', 'Medium'), ('Large', 'Large'), ('Extra Large', 'Extra Large'), ('None', 'None')], default='None', max_length=16),
        ),
        migrations.DeleteModel(
            name='MenuItemX',
        ),
        migrations.DeleteModel(
            name='MenuItemXVariations',
        ),
        migrations.AddField(
            model_name='menuitem',
            name='supportedModifiers',
            field=models.ManyToManyField(to='orders.MenuItemModifier'),
        ),
    ]