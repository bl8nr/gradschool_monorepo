# Generated by Django 2.2.7 on 2019-11-21 06:48

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('orders', '0030_auto_20191121_0019'),
    ]

    operations = [
        migrations.AlterField(
            model_name='menuitem',
            name='size',
            field=models.CharField(choices=[('Small', 'Small'), ('Medium', 'Medium'), ('Large', 'Large'), ('Extra Large', 'Extra Large'), ('One Size', 'One Size')], default='None', max_length=16),
        ),
    ]
