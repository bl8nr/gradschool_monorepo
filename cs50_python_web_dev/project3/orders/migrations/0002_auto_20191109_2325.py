# Generated by Django 2.2.7 on 2019-11-09 23:25

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('orders', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='menucategory',
            name='isChild',
            field=models.BooleanField(default=1),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name='menucategory',
            name='childOf',
            field=models.ForeignKey(blank=True, on_delete=django.db.models.deletion.CASCADE, related_name='parent', to='orders.MenuCategory'),
        ),
    ]