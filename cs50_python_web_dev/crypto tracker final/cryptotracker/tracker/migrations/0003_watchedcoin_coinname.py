# Generated by Django 2.2.7 on 2019-11-30 03:36

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('tracker', '0002_auto_20191129_2355'),
    ]

    operations = [
        migrations.AddField(
            model_name='watchedcoin',
            name='coinName',
            field=models.CharField(default='wefwe', max_length=64),
            preserve_default=False,
        ),
    ]
