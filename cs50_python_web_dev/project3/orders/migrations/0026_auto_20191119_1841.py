# Generated by Django 2.2.7 on 2019-11-19 18:41

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('orders', '0025_ticket_submittedon'),
    ]

    operations = [
        migrations.AlterField(
            model_name='ticket',
            name='submittedOn',
            field=models.DateTimeField(null=True),
        ),
    ]
